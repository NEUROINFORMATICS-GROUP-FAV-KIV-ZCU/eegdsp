package pilsner.signalwindows;

import java.util.Arrays;

/**
 * Troj�hlen�kov� okno, ale s t�m, �e krajn� body nezahazuje.
 * Zdroj: http://en.wikipedia.org/wiki/Window_function
 * 
 * 		w(i) = (2/N) * ((N/2) - abs(n - (N - 1)/2)),
 * 
 * kde N je po�et vzork� a i = 0, 1 ... N - 1
 * 	 
 *  Matlab nav�c zohledn�uje to jestli je okno sud� nebo lich�.
 *  tak�e pro lich� to bude
 *  
 *  	w(i) = 2/(N + 1) * ((N + 1)/2 - abs(i - (N + 1)/2))
 *  
 *  testov�no proti matlabu a maxim�n� odchylka okna je 4e-7
 *  a odchylka m� povahu �umu. Chyba je pravd�podobn� zp�soben�
 *  jin�m po�ad�m operac�. 
 * 
 *  @author Martin �imek
 * */
public class TriangularWindow implements Window {

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
		multiplyByTriangularWindow(retval);
		
		//n�vrat
		return retval;
	}

	/**
	 * Troj�hlen�kov� okno, ale s t�m, �e krajn� body nezahazuje.
	 * Zdroj: http://en.wikipedia.org/wiki/Window_function
	 * 
	 * 		w(i) = (2/N) * ((N/2) - abs(i - (N - 1)/2)),
	 * 
	 * kde N je po�et vzork� a i = 0, 1 ... N - 1
	 * 
	 *  Matlab nav�c zohledn�uje to jestli je okno sud� nebo lich�.
	 *  tak�e pro lich� to bude
	 *  
	 *  	w(i) = 2/(N + 1) * ((N + 1)/2 - abs(i - (N + 1)/2))
	 *  
	 *  testov�no proti matlabu a maxim�n� odchylka okna je 4e-7
	 *  a odchylka m� povahu �umu. Chyba je pravd�podobn� zp�soben�
	 *  jin�m po�ad�m operac�. 
	 *  
	 * */
	public static void multiplyByTriangularWindow(double[] signal){
		if(signal == null || signal.length < 2){
			return;
		}
		
		if(signal.length % 2 == 0){ //sud� po�et
			//konstatn� parametry
			double multiplier = (double)2 / (double)(signal.length);
			double konst1 = (double)(signal.length - 1) / (double)(2);
			double konst2 = (double)(signal.length) / (double)(2);
			
			for(int i = 0; i < signal.length; i+= 1){
				signal[i] *= multiplier * (konst2 - Math.abs(i - konst1));
			}
		}else{ //lich� po�et
			//konstatn� parametry
			double multiplier = (double)2 / (double)(signal.length + 1);
			double konst1 = (double)(signal.length + 1) / (double)(2);
			double konst2 = (double)(signal.length + 1) / (double)(2);
			
			for(int i = 0; i < signal.length; i+= 1){
				signal[i] *= multiplier * (konst2 - Math.abs((i + 1) - konst1));
			}
		}
	}
}
