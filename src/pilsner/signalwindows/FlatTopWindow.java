package pilsner.signalwindows;

import java.util.Arrays;

/**
 * Flat Top window,
 * což je harmonický polynom podle:
 * http://en.wikipedia.org/wiki/Window_function
 * tj.:
 * 		w(i) = a_0 - a_1 * cos(2*pi*i/(N-1)) 
 * 			+ a_2 * cos(4*pi*i/(N-1)) - a_3 * cos(6*pi*i/(N-1))
 * 			+ a_4 * cos(8*pi*i/(N-1)),
 * 
 * kde N je poèet vzorkù, i = 0, 1, 2 ... N - 1, a_0 = 1,
 * a_1 = 1.93, a_2 = 1.29, a_3 = 0.388, a_4 = 0.032
 * 
 * matlab definuje koeficienty jinak:
 * 	a_0 = 0.2156, a_1 = 0.4160, a_2 = 0.2781, a_3 = 0.0836, a_4 = 0.0069
 * 
 * a tato implementace používá koeficienty matlabu. Odychylka až 6e-7.
 * 
 * @author Martin Šimek
 * */
public class FlatTopWindow implements Window {

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
		multiplyByFlatTopWindow(retval);
		
		//návrat
		return retval;
	}

	/**
	 * Vynásobí pole se vstupním signálem Flat Top
	 * oknem, což je harmonický polynom podle:
	 * http://en.wikipedia.org/wiki/Window_function
	 * tj.:
	 * 		w(i) = a_0 - a_1 * cos(2*pi*i/(N-1)) 
	 * 			+ a_2 * cos(4*pi*i/(N-1)) - a_3 * cos(6*pi*i/(N-1))
	 * 			+ a_4 * cos(8*pi*i/(N-1)),
	 * 
	 * kde N je poèet vzorkù, i = 0, 1, 2 ... N - 1, a_0 = 1,
	 * a_1 = 1.93, a_2 = 1.29, a_3 = 0.388, a_4 = 0.032
	 * 
	 * matlab definuje koeficienty jinak:
	 * 	a_0 = 0.2156, a_1 = 0.4160, a_2 = 0.2781, a_3 = 0.0836, a_4 = 0.0069
	 * 
	 * a tato implementace používá koeficienty matlabu. Odychylka až 6e-7.
	 * 
	 * @param signal pole vzorkù signálu
	 * */
	public static void multiplyByFlatTopWindow(double[] signal){
		if(signal == null || signal.length < 1){
			return;
		}
		
		//konstatní èásti vzorce
		/*
		double a_0 = 1;
		double a_1 = 1.93;
		double a_2 = 1.29;
		double a_3 = 0.388;
		double a_4 = 0.032;
		*/
		
		//double matlabScaleFactor = a_0 + a_1 + a_2 + a_3 + a_4;
		
		double a_0 = 0.2156;
		double a_1 = 0.4160;
		double a_2 = 0.2781;
		double a_3 = 0.0836;
		double a_4 = 0.0069;
		
		double factor = Math.PI / (double)(signal.length - 1);
	
		for(int i = 0; i < signal.length; i += 1){
			signal[i] *= a_0 - a_1 * Math.cos(2 * factor * i) + 
			a_2 * Math.cos(4 * factor * i) - a_3 * Math.cos(6 * factor * i) +
			a_4 * Math.cos(8 * factor * i);
		}
	}
	
}
