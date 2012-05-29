package pilsner.signalwindows;

import java.util.Arrays;

/**
* Kaiserovo vokno, nejslo�it�j��, ale perj nejlep��.
 * zdroj z�pisu: http://en.wikipedia.org/wiki/Kaiser_window
 * tj.:
 * 
 * 		w(i) = I_0(alpha * sqrt(1 - (2i/(N - 1) - 1)^2)) / I_0(alpha),
 * 
 * kde N je po�et vzork� sign�lu, i = 0, 1, 2 ... N - 1,
 * I_0 je modifikovan� Besselova funkce nult�ho ��du prvn�ho druhu a
 * alpha vol�me re�ln� 0 <= alpha <= 30, proto�e d�l se besselova funkce
 * ned� jednodu�e po��tat s omezenou aritmetikou IEEE754
 * 
 *   Testov�no proti matlabu a chyba op�t do 4e-7.
 *   pozn.: V amtlabu je koeficient alpha ozna�ov�n jako beta, ale m� stejn� v�znam.
 *   
 *   @author Martin �imek
 * */
public class KaiserWindow implements Window {
	
	private double alpha;

	/**
	 * Vytvo�� t��du generuj�c� okna s defaultn� hodntou alpha = 0,5.
	 * */
	public KaiserWindow(){
		this.alpha = 0.5; //stejn� jako v matlabu
	}
	
	/**
	 * Konstruktor umo��uje nastavovat koeficient alpha.
	 * @param alpha parametr kaiserova okna.
	 * */
	public KaiserWindow(double alpha){
		this.alpha = alpha;
	}
	
	/**
	 * @return aktu�ln� parametr kaiserova okna
	 * */
	public double getAlpha(){
		return this.alpha;
	}
	
	/**
	 * nastav� hodnotu alpha pokud je argument v intervalu <-30, 30>
	 * @param newAlpha nov� parametr sign�lov�ho okna.
	 * */
	public void setAlpha(double newAlpha){
		if(Math.abs(newAlpha) > 30){
			this.alpha = newAlpha;
		}
	}
	
	public double getBandwidthConst() {
		// TODO Auto-generated method stub
		return 0;
	}

	public double getSignalRatio() {
		// TODO Auto-generated method stub
		return 0;
	}

	public double[] getWinSequence(int orderRate) {
		//vytvo�en� pole pro okno
		double[] retval = new double[orderRate];
		
		//vypln�n� jedni�kou
		Arrays.fill(retval, 1);
		
		//p�en�soben� oknem, co� je jako zaps�n� okna
		multiplyByKaiserWindow(retval, this.alpha);
		
		//n�vrat
		return retval;
	}
	
	/**
	 * Kaiserovo vokno, nejslo�it�j��, ale perj nejlep��.
	 * zdroj z�pisu: http://en.wikipedia.org/wiki/Kaiser_window
	 * tj.:
	 * 
	 * 		w(i) = I_0(alpha * sqrt(1 - (2i/(N - 1) - 1)^2)) / I_0(alpha),
	 * 
	 * kde N je po�et vzork� sign�lu, i = 0, 1, 2 ... N - 1,
	 * I_0 je modifikovan� Besselova funkce nult�ho ��du prvn�ho druhu a
	 * alpha vol�me re�ln� 0 <= alpha <= 30, proto�e d�l se besselova funkce
	 * ned� jednodu�e po��tat s omezenou aritmetikou IEEE754
	 * 
	 *   Testov�no proti matlabu a chyba op�t do 4e-7
	 * */
	private static void multiplyByKaiserWindow(double[] signal, double alpha){
		if(signal == null || signal.length < 1 || Math.abs(alpha) > 30 ){
			return;
		}
		
		//konstatn� ��sti vzorce
		double divider = 1 / besselFunction(alpha, true);
		double factor = (double)2 / (double)(signal.length - 1);
		double hlp;
		int half = signal.length / 2; //(int)Math.floor((double)signal.length/(double)2);
		
		if(signal.length % 2 == 1){ //pro lich� d�lky
			half += 1;
		}
		
		for(int i = 0; i < half; i += 1){
			hlp = factor*i - 1;
			hlp *= hlp;
			signal[i] *= besselFunction(alpha * Math.sqrt(1 - hlp), true) * divider;
		}
		
		/*hack no. 1 -- kop�rov�n� poloviny okna, proto�e besselova funkce
		  je hrozn� �patn� podm�n�n� */
		int pos;
		for(int i = half; i < signal.length; i += 1){
			pos = (signal.length - 1 - i);
			signal[i] = signal[pos]; 
		}
		
	}
	
	/**
	 * Besselova funkce nult�ho ��du prvn�ho druhu.
	 * definice podle: 
	 * http://en.wikipedia.org/wiki/Bessel_function_of_the_first_kind
	 * s modifikac� pro nult� ��d:
	 * 
	 * 			J_0(x) = sum from m = 0 to inft. (-1)^m / (m!)^2 * (x/2)^(2m)
	 * 
	 * kde povolen� rozsah x (re�ln�) je <0, 30>, proto�e d�le kv�li
	 * artimetice po��ta�e suma nekonverguje. Funkce tak vrac� NaN.
	 * 
	 * K v�po�tu je pou�ita dop�edn� metoda iterace se zastavovac� podm�nkou
	 * |nov� �len psp.| < 10e-6. Kter� odpov�d� nejv��e 100 iterac�m pro v��e
	 * uveden� interval konvergence. Interval byl zji�t�n experiment�ln�
	 * v programu Matlab.
	 * 
	 * Mo�no pou��t i modifikovanou besselovu funkci dle:
	 * http://mathworld.wolfram.com/ModifiedBesselFunctionoftheFirstKind.html ,
	 * kter� nam�sto alternuj�c�ho �lenu (-1)^m m� konstatn� jedni�ku.
	 * 
	 * @param x argument besselovy funkce, experiment�ln� se vyzkou�elo, �e 
	 * s aritmetikou IEEE754 double na 64 biutech se d� rozum� po��tat s hodnotami
	 * |x| <= 30
	 * @param modified p�ep�na� modifikovan� a nemodifikovan�. true = modifikovan�,
	 * false = p�vodn�.
	 * */
	public static double besselFunction(double x, boolean modified){
		if(x < -30 || x > 30 ){
			return Double.NaN;
		}
		
		//implementace funkce  (-1)^m
		double[] sign_change = new double[2];
		sign_change[0] = 1;
		sign_change[1] = -1;
		if(modified){
			sign_change[1] = 1;	
		}
		
		//p��prava konstatn�ch ��st� posloupnosti
		double x_half = (x / (double)2); // x/2
		x_half *= x_half;				// (x/2)^2
		double x_power = x_half; //pr�b�n� mocnina (x/2)^2
		double mf = 1; // hodnota faktori�lu m
		double J = 1; //hodnota cel� �ady
		double nu = J; // nov� spo�ten� �len �ady
		double m_double = 1; // po�adov� ��slo iterace v double form�tu, proto�e 
							 // konverze na double je �asov� n�ro�n�j�� ne� s��t�n� 
		
		//nejv��e 125 iterac� p�i p�esnosti maxim�ln� chyby pro 0 <= x <= 30 
		for(int m = 1; m <= 125 && Math.abs(nu) >= 10e-80; m += 1, m_double += 1){
			mf *= m_double; //v�po�et m!
			nu = sign_change[m & 0x01] / (mf * mf) * x_power; //�len �ady
			J += nu; // p�i�ten� do sumy
			x_power *= x_half; // postupn� umoc�ov�n� (  (x/2)^2  )^m 
		}
		
		return J;
	}

}
