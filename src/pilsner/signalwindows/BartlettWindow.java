package pilsner.signalwindows;

import java.util.Arrays;

/**
 * Bartlettovo okno 
 * (trojúhelník s nulovými kraji) podle popisu z:
 * http://en.wikipedia.org/wiki/Window_function
 * tj.:
 * 
 * 		w(i) = 2 / (N - 1) * ( (N - 1)/2 - abs(i - (N - 1)/2)),
 * 
 * kde N je poèet vzorkù a i = 0, 1 ... N - 1
 * 
 *  Tato definice odpovídá tomu jak bartlettovo okno implementuje matlab.
 *  Testováno proti matlabu 7.0.1 binárnì, ne textovì!! Odchylka až 3e-7.
 * 
 * @author Martin Šimek
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
		//vytvoøení pole pro okno
		double[] retval = new double[orderRate];
		
		//vyplnìní jednièkou
		Arrays.fill(retval, 1);
		
		//pøenásobení oknem, což je jako zapsání okna
		multiplyByBartlettWindow(retval);
		
		//návrat
		return retval;
	}

	/**
	 * Pøenásobí prvky pole Bartlettovým oknem 
	 * (trojúhelník s nulovými kraji) podle popisu z:
	 * http://en.wikipedia.org/wiki/Window_function
	 * tj.:
	 * 
	 * 		w(i) = 2 / (N - 1) * ( (N - 1)/2 - abs(i - (N - 1)/2)),
	 * 
	 * kde N je poèet vzorkù a i = 0, 1 ... N - 1
	 * 
	 * Tato definice odpovídá tomu jak bartlettovo okno implementuje matlab.
	 * Testováno proti matlabu 7.0.1 binárnì, ne textovì!! Odchylka až 3e-7.
	 * 
	 * @param signal pole se vzorky signálu
	 * */
	public static void multiplyByBartlettWindow(double[] signal){
		if(signal == null || signal.length < 2){
			return;
		}
		
		//konstatní parametry
		double multiplier = (double)2 / (double)(signal.length - 1);
		double konst = (double)(signal.length - 1) / (double)(2);
		
		for(int i = 0; i < signal.length; i+= 1){
			signal[i] *= multiplier * (konst - Math.abs(i - konst));
		}
	}
}
