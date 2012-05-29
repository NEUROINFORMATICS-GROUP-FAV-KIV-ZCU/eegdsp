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
 * a N je poèet vzorkù signálu a i = 0, 1, 2, ... N - 1.
 * 
 * @author Martin Šimek
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
		//vytvoøení pole pro okno
		double[] retval = new double[orderRate];
		
		//vyplnìní jednièkou
		Arrays.fill(retval, 1);
		
		//pøenásobení oknem, což je jako zapsání okna
		multiplyByLnaczosWindow(retval);
		
		//návrat
		return retval;
	}
	
	/**
	 * Pøenásobí prvky v poli Lanczosovým oknem podle definice z
	 * http://en.wikipedia.org/wiki/Window_function
	 * tj.
	 * 
	 * 		w(i) = sinc(2i / (N - 1) - 1),
	 * 
	 * kde
	 * 
	 * 		sinc(k) = sin(pi * k) / (pi * k)
	 * 
	 * a N je poèet vzorkù signálu a i = 0, 1, 2, ... N - 1.
	 * 
	 *  @param signal pole se vzorky signálu, 
	 *  musí být dlouhé alespoò dva vzorky
	 * */
	public static void multiplyByLnaczosWindow(double[] signal){
		if(signal == null || signal.length < 2){
			return;
		}
		
		//konstatní parametry
		double factor = (double)2 / (double)(signal.length - 1);
		double argument, val;
		
		for(int i = 0; i < signal.length; i+= 1){
			argument = (factor*i - 1) * Math.PI;
			
			if(argument == 0){
				val = 1; //protože nìkdo vyprojektoval, že v pùlce to bude "0"/"0" = 1 a to jaksi nejde.
				//takže je na to tenhle hack
			}else{
				val = Math.sin(argument) / argument;	
			}
			signal[i] *= val;
		}
	}

}
