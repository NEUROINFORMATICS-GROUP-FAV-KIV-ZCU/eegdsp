package pilsner.fir.present;

import pilsner.fir.FIRFilter;
import pilsner.fir.Filter.FilterTypes;
import pilsner.signalwindows.WindowFactory.WINDOWS;
import pilsner.utils.Arrays;
import pilsner.utils.math.Convolution;

/**
 * Hlavni trida pro ukazku FIR filtru
 * @author Michal Nykl, 2008
 */
public class Presentation {
	
	/**
	 * Hlavni metoda
	 * @param args Nepredpoklada vstupni parametry
	 */
	public static void main(String[] args) throws Exception {
		// zadni hodnot filtru
		FIRFilter filtr = new FIRFilter(4, FilterTypes.BANDTH_PASS, WINDOWS.RECTANGULAR_WINDOW, 1000.0, 2500.0, 8000.0);
		// vytvoreni filtru
		filtr.firFilterDesign();
		double[] filterCoeff = filtr.getAllFIRFilterCoeff();
		
		System.out.println("FIR filter coeff.:");
		System.out.println(filtr.allFIRFilterCoeffToString("\n"));
		
		System.out.println("Signal Ratio: "+ filtr.getSignalRatio() +" [dB]");
		System.out.println();
		
		System.out.println("Test signal:");
		double[] testSignal = {1.0, 2.0, 3.0, 4.0, 5.0};
		System.out.println(Arrays.toString(testSignal,"\n"));
		
		System.out.println("Convolution:");
		double[] convolution = Convolution.convolution(testSignal, filterCoeff);
		System.out.println(Arrays.toString(convolution,"\n"));
		
		System.out.println("Periodic convolution:");
		double[] periodicConvolution = Convolution.periodicConvolution(testSignal, filterCoeff);
		System.out.println(Arrays.toString(periodicConvolution,"\n"));
	}
}

