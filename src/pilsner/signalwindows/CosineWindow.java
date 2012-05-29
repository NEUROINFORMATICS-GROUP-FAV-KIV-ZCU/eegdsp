package pilsner.signalwindows;

import java.util.Arrays;

/**
 * Implementace kos�nov�ho okna podle definice na:
 * http://en.wikipedia.org/wiki/Window_function
 * tj.:
 * 
 * 		w(i) = sin(pi * i * 1/(N - 1)),
 * 
 * kde N je po�et vzork� a i = 0, 1, ... N - 1
 * 
 * V matlabu v toolboxu signal nen� toto okno dostupn�.
 * 
 * @author Martin �imek
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
		
		//vytvo�en� pole pro okno
		double[] retval = new double[orderRate];
		
		//vypln�n� jedni�kou
		Arrays.fill(retval, 1);
		
		//p�en�soben� oknem, co� je jako zaps�n� okna
		multiplyByCosineWindow(retval);
		
		//n�vrat
		return retval;
	}
	
	/**
	 * Implementace kos�nov�ho okna podle definice na:
	 * http://en.wikipedia.org/wiki/Window_function
	 * tj.:
	 * 
	 * 		w(i) = sin(pi * i * 1/(N - 1)),
	 * 
	 * kde N je po�et vzork� a i = 0, 1, ... N - 1
	 * 
	 * @param signal pole vzork� sign�lu
	 * */
	public static void multiplyByCosineWindow(double[] signal){
		//test pou�itelnosti
		if(signal == null || signal.length <= 1){
			return;
		}
		
		/* paramtery kos�nov�ho okna */
		double factor = ((double)1 / (double)(signal.length - 1)) * Math.PI;
		
		for(int i = 0; i < signal.length; i += 1){
			signal[i] *= Math.sin(factor * i);
		}
	}

}
