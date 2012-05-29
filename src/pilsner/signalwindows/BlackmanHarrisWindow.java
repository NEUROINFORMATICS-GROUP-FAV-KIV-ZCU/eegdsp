package pilsner.signalwindows;

import java.util.Arrays;

/**
 * Blackmano-Harrisovo okno,
 * co� je harmonick� polynom podle:
 * http://en.wikipedia.org/wiki/Window_function
 * kter� je toto�n� s Nuttal window a li�� se pouze v koeficientech
 * tj.:
 * 		w(i) = a_0 - a_1 * cos(2*pi*i/(N-1)) 
 * 			+ a_2 * cos(4*pi*i/(N-1)) - a_3 * cos(6*pi*i/(N-1)),
 * 
 * kde N je po�et vzork�, i = 0, 1, 2 ... N - 1, a_0 = 0.35875,
 * a_1 = 0.48829, a_2 = 0.14128, a_3 = 0.01168
 * 
 * Koeficienty z wiki odpov�daj� koeficient�m v matlabu 7.0.1.
 * 
 * Matlab se odkazuje na 
 * [1] fredric j. harris [sic], On the Use of Windows for Harmonic 
 *    Analysis with the Discrete Fourier Transform, Proceedings of 
 *    the IEEE, Vol. 66, No. 1, January 1978
 *    
 *    a odchylka od v�po�tu matlabu je a� 4e-7. Je to d�no vnit�n�
 *    implementac� goniometrick�ch funkc� apod.
 *    
 * @author Martin �imek
 * */
public class BlackmanHarrisWindow implements Window {

	public double getBandwidthConst() {
		// TODO Auto-generated method stub
		return 2.01;
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
		multiplyByBlackmanHarrisWindow(retval);
		
		//n�vrat
		return retval;
	}

	
	/**
	 * Vyn�sob� pole se vstupn�m sign�lem Blackmanov�m-Harrisov�m
	 * oknem, co� je harmonick� polynom podle:
	 * http://en.wikipedia.org/wiki/Window_function
	 * kter� je toto�n� s Nuttal window a li�� se pouze v koeficientech
	 * tj.:
	 * 		w(i) = a_0 - a_1 * cos(2*pi*i/(N-1)) 
	 * 			+ a_2 * cos(4*pi*i/(N-1)) - a_3 * cos(6*pi*i/(N-1)),
	 * 
	 * kde N je po�et vzork�, i = 0, 1, 2 ... N - 1, a_0 = 0.35875,
	 * a_1 = 0.48829, a_2 = 0.14128, a_3 = 0.01168
	 * 
	 * Koeficienty z wiki odpov�daj� koeficient�m v matlabu 7.0.1.
	 * 
	 * Matlab se odkazuje na 
	 * [1] fredric j. harris [sic], On the Use of Windows for Harmonic 
     *    Analysis with the Discrete Fourier Transform, Proceedings of 
     *    the IEEE, Vol. 66, No. 1, January 1978
     *    
     *    a odchylka od v�po�tu matlabu je a� 4e-7. Je to d�no vnit�n�
     *    implementac� goniometrick�ch funkc� apod. 
	 * 
	 * @param signal pole vzork� sign�lu
	 * */
	public static void multiplyByBlackmanHarrisWindow(double[] signal){
		if(signal == null || signal.length < 1){
			return;
		}
		
		//konstatn� ��sti vzorce
		double a_0 = 0.35875;
		double a_1 = 0.48829;
		double a_2 = 0.14128;
		double a_3 = 0.01168;
		double factor1 = 2 * Math.PI / (double)(signal.length - 1);
		double factor2 = 4 * Math.PI / (double)(signal.length - 1);
		double factor3 = 6 * Math.PI / (double)(signal.length - 1);
	
		for(int i = 0; i < signal.length; i += 1){
			signal[i] *= a_0 - a_1 * Math.cos(factor1 * i) + 
			a_2 * Math.cos(factor2 * i) - a_3 * Math.cos(factor3 * i);
		}
		
	}
}
