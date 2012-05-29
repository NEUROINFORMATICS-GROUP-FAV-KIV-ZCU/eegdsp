package pilsner.signalwindows;

import java.util.Arrays;

/**
 * Bartlettovo okno 
 * (troj�heln�k s nulov�mi kraji) podle popisu z:
 * http://en.wikipedia.org/wiki/Window_function
 * tj.:
 * 
 * 		w(i) = 2 / (N - 1) * ( (N - 1)/2 - abs(i - (N - 1)/2)),
 * 
 * kde N je po�et vzork� a i = 0, 1 ... N - 1
 * 
 *  Tato definice odpov�d� tomu jak bartlettovo okno implementuje matlab.
 *  Testov�no proti matlabu 7.0.1 bin�rn�, ne textov�!! Odchylka a� 3e-7.
 * 
 * @author Martin �imek
 * */
public class BartlettWindow implements Window {

	public double getBandwidthConst() {
		// TODO Auto-generated method stub
		return 1.33;
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
		multiplyByBartlettWindow(retval);
		
		//n�vrat
		return retval;
	}

	/**
	 * P�en�sob� prvky pole Bartlettov�m oknem 
	 * (troj�heln�k s nulov�mi kraji) podle popisu z:
	 * http://en.wikipedia.org/wiki/Window_function
	 * tj.:
	 * 
	 * 		w(i) = 2 / (N - 1) * ( (N - 1)/2 - abs(i - (N - 1)/2)),
	 * 
	 * kde N je po�et vzork� a i = 0, 1 ... N - 1
	 * 
	 * Tato definice odpov�d� tomu jak bartlettovo okno implementuje matlab.
	 * Testov�no proti matlabu 7.0.1 bin�rn�, ne textov�!! Odchylka a� 3e-7.
	 * 
	 * @param signal pole se vzorky sign�lu
	 * */
	public static void multiplyByBartlettWindow(double[] signal){
		if(signal == null || signal.length < 2){
			return;
		}
		
		//konstatn� parametry
		double multiplier = (double)2 / (double)(signal.length - 1);
		double konst = (double)(signal.length - 1) / (double)(2);
		
		for(int i = 0; i < signal.length; i+= 1){
			signal[i] *= multiplier * (konst - Math.abs(i - konst));
		}
	}
}
