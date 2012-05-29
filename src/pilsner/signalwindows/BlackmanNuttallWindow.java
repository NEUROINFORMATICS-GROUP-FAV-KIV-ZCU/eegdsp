package pilsner.signalwindows;

import java.util.Arrays;
/**
 * Blackman-Nuttalovo okno,
 * což je harmonický polynom podle:
 * http://en.wikipedia.org/wiki/Window_function
 * který je totožný s Nuttal window a liší se pouze v koeficientech
 * tj.:
 * 		w(i) = a_0 - a_1 * cos(2*pi*i/(N-1)) 
 * 			+ a_2 * cos(4*pi*i/(N-1)) - a_3 * cos(6*pi*i/(N-1)),
 * 
 * kde N je poèet vzorkù, i = 0, 1, 2 ... N - 1, a_0 = 0.3635819,
 * a_1 = 0.4891775, a_2 = 0.1365995, a_3 = 0.0106411
 * 
 * @author Martin Šimek
 * */
public class BlackmanNuttallWindow implements Window {

	public double getBandwidthConst() {
		// TODO Auto-generated method stub
		return 1.98;
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
		multiplyByBlackmanNuttallWindow(retval);
		
		//návrat
		return retval;
	}
	
	/**
	 * Vynásobí pole se vstupním signálem Blackmanovým-Nuttalovým
	 * oknem, což je harmonický polynom podle:
	 * http://en.wikipedia.org/wiki/Window_function
	 * který je totožný s Nuttal window a liší se pouze v koeficientech
	 * tj.:
	 * 		w(i) = a_0 - a_1 * cos(2*pi*i/(N-1)) 
	 * 			+ a_2 * cos(4*pi*i/(N-1)) - a_3 * cos(6*pi*i/(N-1)),
	 * 
	 * kde N je poèet vzorkù, i = 0, 1, 2 ... N - 1, a_0 = 0.3635819,
	 * a_1 = 0.4891775, a_2 = 0.1365995, a_3 = 0.0106411
	 * 
	 * @param signal pole vzorkù signálu
	 * */
	public static void multiplyByBlackmanNuttallWindow(double[] signal){
		if(signal == null || signal.length < 1){
			return;
		}
		
		//konstatní èásti vzorce
		double a_0 = 0.3635819;
		double a_1 = 0.4891775;
		double a_2 = 0.1365995;
		double a_3 = 0.0106411;
		double factor1 = 2 * Math.PI / (double)(signal.length - 1);
		double factor2 = 4 * Math.PI / (double)(signal.length - 1);
		double factor3 = 6 * Math.PI / (double)(signal.length - 1);
	
		for(int i = 0; i < signal.length; i += 1){
			signal[i] *= a_0 - a_1 * Math.cos(factor1 * i) + 
			a_2 * Math.cos(factor2 * i) - a_3 * Math.cos(factor3 * i);
		}
		
	}

}
