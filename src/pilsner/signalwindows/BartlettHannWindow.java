package pilsner.signalwindows;

import java.util.Arrays;

/**
 * Tøída s implementací modifikovaného Bartlett-Hannova okna.
 * 
 * podle pøedpisu na: http://en.wikipedia.org/wiki/Window_function
 * tj.:
 * 
 * 		w(i) = 0.62 - 0.48 * abs(i / (N - 1) - 0.5) -
 * 				0.38*cos(2 * pi * i / (N - 1)),
 * 
 * kde N je poèet vzorkù a i = 0, 1 ... N - 1
 * 
 * V matlabu je to implementováno trochu jinak, protože cos je
 * sudá funkce a oni si tam napoèítají parametr <-0.5, 0.5>.
 * Každopádnì odchylka od matlabího okna až 4e-7
 * 
 * @author Martin Šimek
 * */
public class BartlettHannWindow implements Window {

	public double getBandwidthConst() {
		// TODO Auto-generated method stub
		return 1.46;
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
		multiplyByBartlettHannWindow(retval);
		
		//návrat
		return retval;
	}
	
	/**
	 * Pøenásobí pole doublù bartlettovým-hannovým oknem
	 * podle pøedpisu na: http://en.wikipedia.org/wiki/Window_function
	 * tj.:
	 * 
	 * 		w(i) = 0.62 - 0.48 * abs(i / (N - 1) - 0.5) -
	 * 				0.38*cos(2 * pi * i / (N - 1)),
	 * 
	 * kde N je poèet vzorkù a i = 0, 1 ... N - 1
	 * 
	 * V matlabu je to implementováno trochu jinak, protože cos je
	 * sudá funkce a oni si tam napoèítají parametr <-0.5, 0.5>.
	 * Každopádnì odchylka od matlabího okna až 4e-7
	 * */
	public static void multiplyByBartlettHannWindow(double[] signal){
		if(signal == null || signal.length < 2){
			return;
		}
	
		//konstantní èásti vzorce
		double konst1 = (double)1 / (double)(signal.length - 1);
		double konst2 = konst1 * 2 * Math.PI;
		
		for(int i = 0; i < signal.length; i += 1){
			signal[i] *= 0.62 - 0.48 * Math.abs(i * konst1 - 0.5) -
						0.38* Math.cos(i * konst2);
		}
	}

}
