package pilsner.spectra;

/**
 * Roz���en� periodogramu, po��t� odhad spektra sign�lu
 * pomoc� welchovy metody co� znamen�, �e v r�mci sign�lu bere
 * segment ur�it� d�lky ten p�en�sob� oknem a ud�l� z n�j periodogram.
 * V�echny periodogramy nakonec zpr�m�ruje.
 * 
 *  Jeliko� je to podt��da periodogramu m� ve�ker� nastaven� shodn�.
 *  Nav�c p�id�v� mo�nost nastavit velikost segmentu a p�ekryv.
 * 
 * */
public class WelchMethod extends Periodogram {

	/* po�et vzork� sign�lu kter� maj� b�t
	 * spole�n� pro dva posob� jdouc� segmenty */
	private int overlap = 0;
	
	/* Velikost segmentu pou�it�ho pro v�po�et
	 * jednotliv�vh periodogram� */
	private int segmentSize = 32;

	/**
	 * @return po�et vzork� kter� je spole�n� pro dva po sob� jdouc�
	 * segmenty.
	 * */
	public int getOverlap()
	{
		return overlap;
	}

	/**
	 * Nastav� p�ekryt� jednotliv�ch segment�.
	 * P�ekryt� mus� b�t nez�porn�, jinak se zm�na
	 * neprojev� a z�stane p�edchoz� hodnota.
	 * Pokud je p�ekryt� v�t�� ne� velikost segmentu
	 * op�t se vynech� jeho zm�na a z�st�v� p�vodn�.
	 * Defaultn� hodnota je nula. 
	 * */
	public void setOverlap(int overlap)
	{
		if (overlap < 0 || overlap >= this.segmentSize) return;
		this.overlap = overlap;
	}

	/**
	 * @return po�et vzork� tvo��c�ho jeden segment pou�it� pro 
	 * v�po�ty d�l��ch periodogram�.
	 * */
	public int getSegmentSize()
	{
		return segmentSize;
	}

	/**
	 * Nastav� velikost segmentu pou��t�ho pro v�po�et d�l��ch
	 * periodogram�.
	 * Pokud je segment men�� ne� p�ekryv (overlap) je p�ekryv
	 * automaticky nastaven na nulu.
	 * Z�rove� je p�i zm�n� velikosti segmentu navzorkov�no okno
	 * pokud je nastaveno.
	 * 
	 *  pokud zad�te velikost segmentu men�� nebo rovnou nule
	 *  zm�na se ignoruje a hodnota zustane p�vodn�.
	 *  
	 *  defaultn� hodnota je 32.
	 *  
	 *  @param segmentSize po�et vzork�, kter� se maj� pou��t
	 *  pro jeden segment.
	 * */
	public void setSegmentSize(int segmentSize)
	{
		if (segmentSize <= 0) return;
		this.segmentSize = segmentSize;
		if (this.segmentSize <= this.overlap) this.overlap = 0;
	}
	
	/**
	 * Konstruktor, kter� vytvo�� instanci Welchovy metody na z�klad� t��dy
	 * peridogram.
	 * 
	 * Z�kladn� nastaven�  d�lka segmentui 32 vzork� p�ekryv nula vzork�
	 * */
	public WelchMethod()
	{
		super();
	}
	
	/**
	 * Spo��t� odhad frekven�n�ho spektra sign�lu pomoc�
	 * welchovy metody. Tedy:
	 * 
	 * 1) Z d�lky sign�lu, d�lky segmentu a velikosti p�ekryvu
	 *    vypo��t� po�et pot�ebn�ch krok�.
	 * 2) Vezme <i>segmentSize</i> vzork�
	 *    podle nastaven� jim spo��t� periodogram.
	 * 3) Periodogram p�en�sob� p�evr�cenou hodnotou po�tu
	 *    krok� a p�i�te k v�stupn�mu poli.
	 * 4) posune se o (segmentSize - overlap) vzork� a pokud
	 *    konec segmetu nele�� mimo pole vstupn�ho sign�lu
	 *    jde na bod (2).
	 *    
	 * pozn.: ��st sign�lu m��e z�stat nevyu�it� pokud jsou velikost segmentu
	 *        a p�ekryv takov�, �e posledn� segment by zasahoval mimo d�lku sign�lu.
	 *        V tom p��pad� by bu� n�soben� oknem nem�lo po�adovan� ��inek, proto�e
	 *        uprost�ed by sign�l skon�il, nebo pokud by se vhodn� zmen�ilo okno
	 *        tak by tento �sek nerovnom�rn� a nespr�vn� p�isp�val do v�sledn�ho
	 *        spektra.
	 * 
	 * @param signal vzorky sign�lu v �asov� oblasti nad kter�mi se m� spo��tat
	 * odhad spektra.
	 * @return pole doubl� reprezentuj�c�ch odhad spektra sign�lu pomoc�
	 * welchovy metody.
	 * */
	public double[] getWelchEstimation(double[] signal)
	{	
		//zb�n� kontrola spr�vnosti vstupu
		if (signal == null || signal.length < this.segmentSize) return null;
		
		double[] result;
		double[] current;

		//velikost posunu pro jednotilv� segmenty
		int step = this.segmentSize - this.overlap;
		
		//velikost nad, kterou po��tat FFT. kvuli kompatibilit� s matlabem
		this.setNfft(256);

		/*�e��me rovnici (n*step + segmetnSize) <= signal.length
		n <= (signal.length - segmentSize)/step  && n je cel�
		tak�e celo��seln� v�po�et by m�l bejt OK */
		int n = (signal.length - this.segmentSize) / step + 1;
		
		System.out.println("n = " + n);
		double nInverted = (double) 1 / (double) n;
		
		/*
		 * Prvn� krok: spo��t�me periodogram prvn�ho segmentu
		 * proto�e t�m z�sk�me spr�vnou d�lku pole a nebudeme
		 * muset vymej�let jak m� asi tak bejt dlouh� pdle
		 * nastaven� periodogramu.
		 * */
		result = this.getPeriodogram(signal, 0, this.segmentSize);
		SpectraEstimation.multiplyBy(result, nInverted);
		
		/* postupn� napo��t�me periodogramy pro jednotliv� segmenty
		 * a s��t�me jejich d�l�� ��st s pr�b�n�m v�sledkem */
		int lastSampleIndex = signal.length - this.segmentSize;  
		for (int i = step; i <= lastSampleIndex; i += step)
		{
			current = this.getPeriodogram(signal, i, this.segmentSize);
			SpectraEstimation.multiplyBy(current, nInverted);
			SpectraEstimation.addArray(result, result, current);
		}
		
		/* nakonec vr�t�me v�sledn� odhad spektra */
		return result;
	}
}
