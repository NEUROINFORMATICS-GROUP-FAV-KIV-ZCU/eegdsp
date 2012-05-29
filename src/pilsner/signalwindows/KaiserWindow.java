package pilsner.signalwindows;

import java.util.Arrays;

/**
* Kaiserovo vokno, nejsložitìjší, ale perj nejlepší.
 * zdroj zápisu: http://en.wikipedia.org/wiki/Kaiser_window
 * tj.:
 * 
 * 		w(i) = I_0(alpha * sqrt(1 - (2i/(N - 1) - 1)^2)) / I_0(alpha),
 * 
 * kde N je poèet vzorkù signálu, i = 0, 1, 2 ... N - 1,
 * I_0 je modifikovaná Besselova funkce nultého øádu prvního druhu a
 * alpha volíme reálné 0 <= alpha <= 30, protože dál se besselova funkce
 * nedá jednoduše poèítat s omezenou aritmetikou IEEE754
 * 
 *   Testováno proti matlabu a chyba opìt do 4e-7.
 *   pozn.: V amtlabu je koeficient alpha oznaèován jako beta, ale má stejný význam.
 *   
 *   @author Martin Šimek
 * */
public class KaiserWindow implements Window {
	
	private double alpha;

	/**
	 * Vytvoøí tøídu generující okna s defaultní hodntou alpha = 0,5.
	 * */
	public KaiserWindow(){
		this.alpha = 0.5; //stejnì jako v matlabu
	}
	
	/**
	 * Konstruktor umožòuje nastavovat koeficient alpha.
	 * @param alpha parametr kaiserova okna.
	 * */
	public KaiserWindow(double alpha){
		this.alpha = alpha;
	}
	
	/**
	 * @return aktuální parametr kaiserova okna
	 * */
	public double getAlpha(){
		return this.alpha;
	}
	
	/**
	 * nastaví hodnotu alpha pokud je argument v intervalu <-30, 30>
	 * @param newAlpha nový parametr signálového okna.
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
		//vytvoøení pole pro okno
		double[] retval = new double[orderRate];
		
		//vyplnìní jednièkou
		Arrays.fill(retval, 1);
		
		//pøenásobení oknem, což je jako zapsání okna
		multiplyByKaiserWindow(retval, this.alpha);
		
		//návrat
		return retval;
	}
	
	/**
	 * Kaiserovo vokno, nejsložitìjší, ale perj nejlepší.
	 * zdroj zápisu: http://en.wikipedia.org/wiki/Kaiser_window
	 * tj.:
	 * 
	 * 		w(i) = I_0(alpha * sqrt(1 - (2i/(N - 1) - 1)^2)) / I_0(alpha),
	 * 
	 * kde N je poèet vzorkù signálu, i = 0, 1, 2 ... N - 1,
	 * I_0 je modifikovaná Besselova funkce nultého øádu prvního druhu a
	 * alpha volíme reálné 0 <= alpha <= 30, protože dál se besselova funkce
	 * nedá jednoduše poèítat s omezenou aritmetikou IEEE754
	 * 
	 *   Testováno proti matlabu a chyba opìt do 4e-7
	 * */
	private static void multiplyByKaiserWindow(double[] signal, double alpha){
		if(signal == null || signal.length < 1 || Math.abs(alpha) > 30 ){
			return;
		}
		
		//konstatní èásti vzorce
		double divider = 1 / besselFunction(alpha, true);
		double factor = (double)2 / (double)(signal.length - 1);
		double hlp;
		int half = signal.length / 2; //(int)Math.floor((double)signal.length/(double)2);
		
		if(signal.length % 2 == 1){ //pro liché délky
			half += 1;
		}
		
		for(int i = 0; i < half; i += 1){
			hlp = factor*i - 1;
			hlp *= hlp;
			signal[i] *= besselFunction(alpha * Math.sqrt(1 - hlp), true) * divider;
		}
		
		/*hack no. 1 -- kopírování poloviny okna, protože besselova funkce
		  je hroznì špatnì podmínìná */
		int pos;
		for(int i = half; i < signal.length; i += 1){
			pos = (signal.length - 1 - i);
			signal[i] = signal[pos]; 
		}
		
	}
	
	/**
	 * Besselova funkce nultého øádu prvního druhu.
	 * definice podle: 
	 * http://en.wikipedia.org/wiki/Bessel_function_of_the_first_kind
	 * s modifikací pro nultý øád:
	 * 
	 * 			J_0(x) = sum from m = 0 to inft. (-1)^m / (m!)^2 * (x/2)^(2m)
	 * 
	 * kde povolený rozsah x (reálné) je <0, 30>, protože dále kvùli
	 * artimetice poèítaèe suma nekonverguje. Funkce tak vrací NaN.
	 * 
	 * K výpoètu je použita dopøedná metoda iterace se zastavovací podmínkou
	 * |nový èlen psp.| < 10e-6. Která odpovídá nejvýše 100 iteracím pro výše
	 * uvedený interval konvergence. Interval byl zjištìn experimentálnì
	 * v programu Matlab.
	 * 
	 * Možno použít i modifikovanou besselovu funkci dle:
	 * http://mathworld.wolfram.com/ModifiedBesselFunctionoftheFirstKind.html ,
	 * která namísto alternujícího èlenu (-1)^m má konstatní jednièku.
	 * 
	 * @param x argument besselovy funkce, experimentálnì se vyzkoušelo, že 
	 * s aritmetikou IEEE754 double na 64 biutech se dá rozumì poèítat s hodnotami
	 * |x| <= 30
	 * @param modified pøepínaè modifikované a nemodifikované. true = modifikovaná,
	 * false = pùvodní.
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
		
		//pøíprava konstatních èístí posloupnosti
		double x_half = (x / (double)2); // x/2
		x_half *= x_half;				// (x/2)^2
		double x_power = x_half; //prùbìžná mocnina (x/2)^2
		double mf = 1; // hodnota faktoriálu m
		double J = 1; //hodnota celé øady
		double nu = J; // novì spoètený èlen øady
		double m_double = 1; // poøadové èíslo iterace v double formátu, protože 
							 // konverze na double je èasovì nároènìjší než sèítání 
		
		//nejvýše 125 iterací pøi pøesnosti maximální chyby pro 0 <= x <= 30 
		for(int m = 1; m <= 125 && Math.abs(nu) >= 10e-80; m += 1, m_double += 1){
			mf *= m_double; //výpoèet m!
			nu = sign_change[m & 0x01] / (mf * mf) * x_power; //èlen øady
			J += nu; // pøiètení do sumy
			x_power *= x_half; // postupné umocòování (  (x/2)^2  )^m 
		}
		
		return J;
	}

}
