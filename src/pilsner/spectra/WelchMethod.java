package pilsner.spectra;

/**
 * Rozšíøení periodogramu, poèítá odhad spektra signálu
 * pomocí welchovy metody což znamená, že v rámci signálu bere
 * segment urèité délky ten pøenásobí oknem a udìlá z nìj periodogram.
 * Všechny periodogramy nakonec zprùmìruje.
 * 
 *  Jelikož je to podtøída periodogramu má veškerá nastavení shodná.
 *  Navíc pøidává možnost nastavit velikost segmentu a pøekryv.
 * 
 * */
public class WelchMethod extends Periodogram {

	/* poèet vzorkù signálu které mají být
	 * spoleèné pro dva posobì jdoucí segmenty */
	private int overlap = 0;
	
	/* Velikost segmentu použitého pro výpoèet
	 * jednotlivývh periodogramù */
	private int segmentSize = 32;

	/**
	 * @return poèet vzorkù který je spoleèný pro dva po sobì jdoucí
	 * segmenty.
	 * */
	public int getOverlap()
	{
		return overlap;
	}

	/**
	 * Nastaví pøekrytí jednotlivých segmentù.
	 * Pøekrytí musí být nezáporné, jinak se zmìna
	 * neprojeví a zùstane pøedchozí hodnota.
	 * Pokud je pøekrytí vìtší než velikost segmentu
	 * opìt se vynechá jeho zmìna a zùstává pùvodní.
	 * Defaultní hodnota je nula. 
	 * */
	public void setOverlap(int overlap)
	{
		if (overlap < 0 || overlap >= this.segmentSize) return;
		this.overlap = overlap;
	}

	/**
	 * @return poèet vzorkù tvoøícího jeden segment použitý pro 
	 * výpoèty dílèích periodogramù.
	 * */
	public int getSegmentSize()
	{
		return segmentSize;
	}

	/**
	 * Nastaví velikost segmentu použítého pro výpoèet dílèích
	 * periodogramù.
	 * Pokud je segment menší než pøekryv (overlap) je pøekryv
	 * automaticky nastaven na nulu.
	 * Zároveò je pøi zmìnì velikosti segmentu navzorkováno okno
	 * pokud je nastaveno.
	 * 
	 *  pokud zadáte velikost segmentu menší nebo rovnou nule
	 *  zmìna se ignoruje a hodnota zustane pùvodní.
	 *  
	 *  defaultní hodnota je 32.
	 *  
	 *  @param segmentSize poèet vzorkù, které se mají použít
	 *  pro jeden segment.
	 * */
	public void setSegmentSize(int segmentSize)
	{
		if (segmentSize <= 0) return;
		this.segmentSize = segmentSize;
		if (this.segmentSize <= this.overlap) this.overlap = 0;
	}
	
	/**
	 * Konstruktor, který vytvoøí instanci Welchovy metody na základì tøídy
	 * peridogram.
	 * 
	 * Základní nastavení  délka segmentui 32 vzorkù pøekryv nula vzorkù
	 * */
	public WelchMethod()
	{
		super();
	}
	
	/**
	 * Spoèítá odhad frekvenèního spektra signálu pomocí
	 * welchovy metody. Tedy:
	 * 
	 * 1) Z délky signálu, dìlky segmentu a velikosti pøekryvu
	 *    vypoèítá poèet potøebných krokù.
	 * 2) Vezme <i>segmentSize</i> vzorkù
	 *    podle nastavení jim spoèítá periodogram.
	 * 3) Periodogram pøenásobí pøevrácenou hodnotou poètu
	 *    krokù a pøiète k výstupnímu poli.
	 * 4) posune se o (segmentSize - overlap) vzorkù a pokud
	 *    konec segmetu neleží mimo pole vstupního signálu
	 *    jde na bod (2).
	 *    
	 * pozn.: Èást signálu mùže zùstat nevyužitá pokud jsou velikost segmentu
	 *        a pøekryv takové, že poslední segment by zasahoval mimo délku signálu.
	 *        V tom pøípadì by buï násobení oknem nemìlo požadovaný úèinek, protože
	 *        uprostøed by signál skonèil, nebo pokud by se vhodnì zmenšilo okno
	 *        tak by tento úsek nerovnomìrnì a nesprávnì pøispíval do výsledného
	 *        spektra.
	 * 
	 * @param signal vzorky signálu v èasové oblasti nad kterými se má spoèítat
	 * odhad spektra.
	 * @return pole doublù reprezentujících odhad spektra signálu pomocí
	 * welchovy metody.
	 * */
	public double[] getWelchEstimation(double[] signal)
	{	
		//zbìžná kontrola správnosti vstupu
		if (signal == null || signal.length < this.segmentSize) return null;
		
		double[] result;
		double[] current;

		//velikost posunu pro jednotilvé segmenty
		int step = this.segmentSize - this.overlap;
		
		//velikost nad, kterou poèítat FFT. kvuli kompatibilitì s matlabem
		this.setNfft(256);

		/*øešíme rovnici (n*step + segmetnSize) <= signal.length
		n <= (signal.length - segmentSize)/step  && n je celé
		takže celoèíselný výpoèet by mìl bejt OK */
		int n = (signal.length - this.segmentSize) / step + 1;
		
		System.out.println("n = " + n);
		double nInverted = (double) 1 / (double) n;
		
		/*
		 * První krok: spoèítáme periodogram prvního segmentu
		 * protože tím získáme správnou délku pole a nebudeme
		 * muset vymejšlet jak má asi tak bejt dlouhý pdle
		 * nastavení periodogramu.
		 * */
		result = this.getPeriodogram(signal, 0, this.segmentSize);
		SpectraEstimation.multiplyBy(result, nInverted);
		
		/* postupnì napoèítáme periodogramy pro jednotlivé segmenty
		 * a sèítáme jejich dílèí èást s prùbìžným výsledkem */
		int lastSampleIndex = signal.length - this.segmentSize;  
		for (int i = step; i <= lastSampleIndex; i += step)
		{
			current = this.getPeriodogram(signal, i, this.segmentSize);
			SpectraEstimation.multiplyBy(current, nInverted);
			SpectraEstimation.addArray(result, result, current);
		}
		
		/* nakonec vrátíme výsledný odhad spektra */
		return result;
	}
}
