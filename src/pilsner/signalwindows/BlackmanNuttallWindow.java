package pilsner.signalwindows;

import java.util.Arrays;
/**
 * Blackman-Nuttalovo okno,
 * co� je harmonick� polynom podle:
 * http://en.wikipedia.org/wiki/Window_function
 * kter� je toto�n� s Nuttal window a li�� se pouze v koeficientech
 * tj.:
 * 		w(i) = a_0 - a_1 * cos(2*pi*i/(N-1)) 
 * 			+ a_2 * cos(4*pi*i/(N-1)) - a_3 * cos(6*pi*i/(N-1)),
 * 
 * kde N je po�et vzork�, i = 0, 1, 2 ... N - 1, a_0 = 0.3635819,
 * a_1 = 0.4891775, a_2 = 0.1365995, a_3 = 0.0106411
 * 
 * @author Martin �imek
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
		//vytvo�en� pole pro okno
		double[] retval = new double[orderRate];
		
		//vypln�n� jedni�kou
		Arrays.fill(retval, 1);
		
		//p�en�soben� oknem, co� je jako zaps�n� okna
		multiplyByBlackmanNuttallWindow(retval);
		
		//n�vrat
		return retval;
	}
	
	/**
	 * Vyn�sob� pole se vstupn�m sign�lem Blackmanov�m-Nuttalov�m
	 * oknem, co� je harmonick� polynom podle:
	 * http://en.wikipedia.org/wiki/Window_function
	 * kter� je toto�n� s Nuttal window a li�� se pouze v koeficientech
	 * tj.:
	 * 		w(i) = a_0 - a_1 * cos(2*pi*i/(N-1)) 
	 * 			+ a_2 * cos(4*pi*i/(N-1)) - a_3 * cos(6*pi*i/(N-1)),
	 * 
	 * kde N je po�et vzork�, i = 0, 1, 2 ... N - 1, a_0 = 0.3635819,
	 * a_1 = 0.4891775, a_2 = 0.1365995, a_3 = 0.0106411
	 * 
	 * @param signal pole vzork� sign�lu
	 * */
	public static void multiplyByBlackmanNuttallWindow(double[] signal){
		if(signal == null || signal.length < 1){
			return;
		}
		
		//konstatn� ��sti vzorce
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
