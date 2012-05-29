package pilsner.signalwindows;

import java.util.Arrays;

/**
 * Blackmano-Harrisovo okno,
 * což je harmonický polynom podle:
 * http://en.wikipedia.org/wiki/Window_function
 * který je totožný s Nuttal window a liší se pouze v koeficientech
 * tj.:
 * 		w(i) = a_0 - a_1 * cos(2*pi*i/(N-1)) 
 * 			+ a_2 * cos(4*pi*i/(N-1)) - a_3 * cos(6*pi*i/(N-1)),
 * 
 * kde N je poèet vzorkù, i = 0, 1, 2 ... N - 1, a_0 = 0.35875,
 * a_1 = 0.48829, a_2 = 0.14128, a_3 = 0.01168
 * 
 * Koeficienty z wiki odpovídají koeficientùm v matlabu 7.0.1.
 * 
 * Matlab se odkazuje na 
 * [1] fredric j. harris [sic], On the Use of Windows for Harmonic 
 *    Analysis with the Discrete Fourier Transform, Proceedings of 
 *    the IEEE, Vol. 66, No. 1, January 1978
 *    
 *    a odchylka od výpoètu matlabu je až 4e-7. Je to dáno vnitøní
 *    implementací goniometrických funkcí apod.
 *    
 * @author Martin Šimek
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
		//vytvoøení pole pro okno
		double[] retval = new double[orderRate];
		
		//vyplnìní jednièkou
		Arrays.fill(retval, 1);
		
		//pøenásobení oknem, což je jako zapsání okna
		multiplyByBlackmanHarrisWindow(retval);
		
		//návrat
		return retval;
	}

	
	/**
	 * Vynásobí pole se vstupním signálem Blackmanovým-Harrisovým
	 * oknem, což je harmonický polynom podle:
	 * http://en.wikipedia.org/wiki/Window_function
	 * který je totožný s Nuttal window a liší se pouze v koeficientech
	 * tj.:
	 * 		w(i) = a_0 - a_1 * cos(2*pi*i/(N-1)) 
	 * 			+ a_2 * cos(4*pi*i/(N-1)) - a_3 * cos(6*pi*i/(N-1)),
	 * 
	 * kde N je poèet vzorkù, i = 0, 1, 2 ... N - 1, a_0 = 0.35875,
	 * a_1 = 0.48829, a_2 = 0.14128, a_3 = 0.01168
	 * 
	 * Koeficienty z wiki odpovídají koeficientùm v matlabu 7.0.1.
	 * 
	 * Matlab se odkazuje na 
	 * [1] fredric j. harris [sic], On the Use of Windows for Harmonic 
     *    Analysis with the Discrete Fourier Transform, Proceedings of 
     *    the IEEE, Vol. 66, No. 1, January 1978
     *    
     *    a odchylka od výpoètu matlabu je až 4e-7. Je to dáno vnitøní
     *    implementací goniometrických funkcí apod. 
	 * 
	 * @param signal pole vzorkù signálu
	 * */
	public static void multiplyByBlackmanHarrisWindow(double[] signal){
		if(signal == null || signal.length < 1){
			return;
		}
		
		//konstatní èásti vzorce
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
