package pilsner.signalwindows;

import java.util.Arrays;

/**
 * Trojúhleníkové okno, ale s tím, že krajní body nezahazuje.
 * Zdroj: http://en.wikipedia.org/wiki/Window_function
 * 
 * 		w(i) = (2/N) * ((N/2) - abs(n - (N - 1)/2)),
 * 
 * kde N je poèet vzorkù a i = 0, 1 ... N - 1
 * 	 
 *  Matlab navíc zohlednòuje to jestli je okno sudé nebo liché.
 *  takže pro liché to bude
 *  
 *  	w(i) = 2/(N + 1) * ((N + 1)/2 - abs(i - (N + 1)/2))
 *  
 *  testováno proti matlabu a maximání odchylka okna je 4e-7
 *  a odchylka má povahu šumu. Chyba je pravdìpodobnì zpùsobená
 *  jiným poøadím operací. 
 * 
 *  @author Martin Šimek
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
		//vytvoøení pole pro okno
		double[] retval = new double[orderRate];
		
		//vyplnìní jednièkou
		Arrays.fill(retval, 1);
		
		//pøenásobení oknem, což je jako zapsání okna
		multiplyByTriangularWindow(retval);
		
		//návrat
		return retval;
	}

	/**
	 * Trojúhleníkové okno, ale s tím, že krajní body nezahazuje.
	 * Zdroj: http://en.wikipedia.org/wiki/Window_function
	 * 
	 * 		w(i) = (2/N) * ((N/2) - abs(i - (N - 1)/2)),
	 * 
	 * kde N je poèet vzorkù a i = 0, 1 ... N - 1
	 * 
	 *  Matlab navíc zohlednòuje to jestli je okno sudé nebo liché.
	 *  takže pro liché to bude
	 *  
	 *  	w(i) = 2/(N + 1) * ((N + 1)/2 - abs(i - (N + 1)/2))
	 *  
	 *  testováno proti matlabu a maximání odchylka okna je 4e-7
	 *  a odchylka má povahu šumu. Chyba je pravdìpodobnì zpùsobená
	 *  jiným poøadím operací. 
	 *  
	 * */
	public static void multiplyByTriangularWindow(double[] signal){
		if(signal == null || signal.length < 2){
			return;
		}
		
		if(signal.length % 2 == 0){ //sudý poèet
			//konstatní parametry
			double multiplier = (double)2 / (double)(signal.length);
			double konst1 = (double)(signal.length - 1) / (double)(2);
			double konst2 = (double)(signal.length) / (double)(2);
			
			for(int i = 0; i < signal.length; i+= 1){
				signal[i] *= multiplier * (konst2 - Math.abs(i - konst1));
			}
		}else{ //lichý poèet
			//konstatní parametry
			double multiplier = (double)2 / (double)(signal.length + 1);
			double konst1 = (double)(signal.length + 1) / (double)(2);
			double konst2 = (double)(signal.length + 1) / (double)(2);
			
			for(int i = 0; i < signal.length; i+= 1){
				signal[i] *= multiplier * (konst2 - Math.abs((i + 1) - konst1));
			}
		}
	}
}
