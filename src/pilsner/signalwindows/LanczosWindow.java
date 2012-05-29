package pilsner.signalwindows;

import java.util.Arrays;


/**
 * Implemntace Lanczosova oknem podle definice z
 * http://en.wikipedia.org/wiki/Window_function
 * tj.
 * 
 * 		w(i) = sinc(2i / (N - 1) - 1),
 * 
 * kde
 * 
 * 		sinc(k) = sin(pi * k) / (pi * k)
 * 
 * a N je po�et vzork� sign�lu a i = 0, 1, 2, ... N - 1.
 * 
 * @author Martin �imek
 * */
public class LanczosWindow implements Window {

	public double getBandwidthConst() {
		// TODO Auto-generated method stub
		return 1.31;
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
		multiplyByLnaczosWindow(retval);
		
		//n�vrat
		return retval;
	}
	
	/**
	 * P�en�sob� prvky v poli Lanczosov�m oknem podle definice z
	 * http://en.wikipedia.org/wiki/Window_function
	 * tj.
	 * 
	 * 		w(i) = sinc(2i / (N - 1) - 1),
	 * 
	 * kde
	 * 
	 * 		sinc(k) = sin(pi * k) / (pi * k)
	 * 
	 * a N je po�et vzork� sign�lu a i = 0, 1, 2, ... N - 1.
	 * 
	 *  @param signal pole se vzorky sign�lu, 
	 *  mus� b�t dlouh� alespo� dva vzorky
	 * */
	public static void multiplyByLnaczosWindow(double[] signal){
		if(signal == null || signal.length < 2){
			return;
		}
		
		//konstatn� parametry
		double factor = (double)2 / (double)(signal.length - 1);
		double argument, val;
		
		for(int i = 0; i < signal.length; i+= 1){
			argument = (factor*i - 1) * Math.PI;
			
			if(argument == 0){
				val = 1; //proto�e n�kdo vyprojektoval, �e v p�lce to bude "0"/"0" = 1 a to jaksi nejde.
				//tak�e je na to tenhle hack
			}else{
				val = Math.sin(argument) / argument;	
			}
			signal[i] *= val;
		}
	}

}
