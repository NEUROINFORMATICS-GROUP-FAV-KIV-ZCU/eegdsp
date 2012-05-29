package pilsner.signalwindows;

import java.util.Arrays;

/**
 * Gaussovo okno podle Matlabu.
 * Matlab používá implementaci podle: 
 * 		fredric j. harris [sic], On the Use of Windows for Harmonic 
 * 		Analysis with the Discrete Fourier Transform, Proceedings of 
 * 		the IEEE, Vol. 66, No. 1, January 1978
 * 
 * 		w(i) = w(i) = exp(-1/2 * (alpha * (i - (N - 1)/2) / (N/2))^2),
 * 
 *  kde alpha je pøevrácená hodnota rozptylu. I pøes naprosto shodnou 
 *  implementaci s matalbem 7.0.1 dosahuje odchylka zde generovaného okna
 *  oproti matlabu až 10^-6. Pravdìpodobnì je to tím, že matlab stejnì jako
 *  java používá nativní funkci exp(), která se v obou implementacích liší.
 *
 * @param signal je pole které se má pøenásobit oknem.
 * @param alpha je pøevrácená hodnota rozptylu.
 * */
public class GaussWindow implements Window {

	//private double sigma; //pro implementaci dle wiki
	private double alpha;
	
	public GaussWindow(){
		//možná sem dodìlám defaultní alpha = 2.5 jako je v matlabu
		this.alpha = 2.5; 
	}
	
	public GaussWindow(double alpha){
		this.alpha = alpha;
	}
	
	/**
	 * Vrátí hodnotu rozptylu použitou v 
	 * */
	public double getAlpha(){
		return this.alpha;
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
		multiplyByGaussWindow(retval, this.alpha);
		
		//návrat
		return retval;
	}
	
	/**
	 * Vynásobí signál Gaussovým oknem podle definice z: Matlabu
	 * 	
	 * Matlab používá implementaci podle: 
	 * 		fredric j. harris [sic], On the Use of Windows for Harmonic 
	 * 		Analysis with the Discrete Fourier Transform, Proceedings of 
	 * 		the IEEE, Vol. 66, No. 1, January 1978
	 * 
	 * 		w(i) = w(i) = exp(-1/2 * (alpha * (i - (N - 1)/2) / (N/2))^2),
	 * 
	 *  kde alpha je pøevrácená hodnota rozptylu. I pøes naprosto shodnou 
	 *  implementaci s matalbem 7.0.1 dosahuje odchylka zde generovaného okna
	 *  oproti matlabu až 10^-6. Pravdìpodobnì je to tím, že matlab stejnì jako
	 *  java používá nativní funkci exp(), která se v obou implementacích liší.
	 *
	 * @param signal je pole které se má pøenásobit oknem.
	 * @param alpha je pøevrácená hodnota rozptylu.
	 * */
	public static void multiplyByGaussWindow(double[] signal, double alpha){
		if(signal == null || signal.length < 2){
			return;
		}
		
		//konstatní èásti vzorce
		double konst1 = (double)(signal.length - 1) / (double)2;
		double konst3 = (double)signal.length / (double)2;
		//double konst2 = 1 / (sigma * konst1);
		double konst2 = alpha / konst3; 
		double frac;
		
		for(int i = 0; i < signal.length; i += 1){
			// frac = (i - konst1) * konst2;   //implementace podle wiki
			// signal[i] *= Math.exp(-0.5 * frac * frac);
			
			frac = (i - konst1) * konst2;   //implementace podle matlabu
			signal[i] *= Math.exp(-0.5 * frac * frac);
		}
	}

        public static double applyGaussWindow(double t) {
            return Math.exp(-Math.PI * Math.pow(t, 2));
        }

}
