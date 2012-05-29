package pilsner.signalwindows;

import java.util.Arrays;

public class NuttallWindow implements Window {

	public double getBandwidthConst() {
		// TODO Auto-generated method stub
		return 2.02;
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
		multiplyByNuttallWindow(retval);
		
		//návrat
		return retval;
	}
	
	/**
	 * Vynásobí pole se vstupním signálem Nuttall oknem, což
	 * je harmonický polynom podle:
	 * http://en.wikipedia.org/wiki/Window_function
	 * tj.:
	 * 		w(i) = a_0 - a_1 * cos(2*pi*i/(N-1)) 
	 * 			+ a_2 * cos(4*pi*i/(N-1)) - a_3 * cos(6*pi*i/(N-1)),
	 * 
	 * kde N je poèet vzorkù, i = 0, 1, 2 ... N - 1, a_0 = 0.355768,
	 * a_1 = 0.487396, a_2 = 0.144232, a_3 = 0.012604  podle wiki
	 * 
	 * @param signal pole vzorkù signálu
	 * */
	public static void multiplyByNuttallWindow(double[] signal){
		if(signal == null || signal.length < 1){
			return;
		}
		
		//konstatní èásti vzorce
		/*
		double a_0 = 0.355768;
		double a_1 = 0.487396;
		double a_2 = 0.144232;
		double a_3 = 0.012604;
			// hodnoty podle wiki
		*/
		
		double a_0 = 0.3635819;
		double a_1 = 0.4891775;
		double a_2 = 0.1365995;
		double a_3 = 0.0106411;
			/* hodnoty podle matlabu který cituje:
			 * 	     [1] Albert H. Nuttall, Some Windows with Very Good Sidelobe
			 *	         Behavior, IEEE Transactions on Acoustics, Speech, and 
			 *	         Signal Processing, Vol. ASSP-29, No.1, February 1981
			 *			 page 89 
			 */
		
		double factor1 = 2 * Math.PI / (double)(signal.length - 1);
		double factor2 = 4 * Math.PI / (double)(signal.length - 1);
		double factor3 = 6 * Math.PI / (double)(signal.length - 1);
	
		for(int i = 0; i < signal.length; i += 1){
			signal[i] *= a_0 - a_1 * Math.cos(factor1 * i) + 
			a_2 * Math.cos(factor2 * i) - a_3 * Math.cos(factor3 * i);
		}
		
	}

}
