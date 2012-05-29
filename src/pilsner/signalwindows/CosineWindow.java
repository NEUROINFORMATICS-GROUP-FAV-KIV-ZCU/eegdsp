package pilsner.signalwindows;

import java.util.Arrays;

/**
 * Implementace kosínového okna podle definice na:
 * http://en.wikipedia.org/wiki/Window_function
 * tj.:
 * 
 * 		w(i) = sin(pi * i * 1/(N - 1)),
 * 
 * kde N je poèet vzorkù a i = 0, 1, ... N - 1
 * 
 * V matlabu v toolboxu signal není toto okno dostupné.
 * 
 * @author Martin Šimek
 * */
public class CosineWindow implements Window {

	public double getBandwidthConst() {
		// TODO Auto-generated method stub
		return 1.24;
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
		multiplyByCosineWindow(retval);
		
		//návrat
		return retval;
	}
	
	/**
	 * Implementace kosínového okna podle definice na:
	 * http://en.wikipedia.org/wiki/Window_function
	 * tj.:
	 * 
	 * 		w(i) = sin(pi * i * 1/(N - 1)),
	 * 
	 * kde N je poèet vzorkù a i = 0, 1, ... N - 1
	 * 
	 * @param signal pole vzorkù signálu
	 * */
	public static void multiplyByCosineWindow(double[] signal){
		//test použitelnosti
		if(signal == null || signal.length <= 1){
			return;
		}
		
		/* paramtery kosínového okna */
		double factor = ((double)1 / (double)(signal.length - 1)) * Math.PI;
		
		for(int i = 0; i < signal.length; i += 1){
			signal[i] *= Math.sin(factor * i);
		}
	}

}
