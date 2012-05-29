package pilsner.wt.dwt.wavelets;

/**
 * Tøída waveletu Daubechies12.
 * Obsahuje veškeré koeficienty pro dopøednou i inverzní
 * diskrétní waveletovou transformaci.
 */
public class Daubechies12 extends WaveletDWT
{
	//název waveletu
	private final static String NAME = "Daubechies_12";
	
	//škálové koeficienty
	private final static double[] SCALE = {	1.11540743350000000E-01,
									        4.94623890397999980E-01,
									        7.51133908021000000E-01,
									        3.15250351709000010E-01,
									        -2.26264693965000010E-01,
									        -1.29766867567000010E-01,
									        9.75016055869999950E-02,
									        2.75228655299999990E-02,
									        -3.15820393180000010E-02,
									        5.53842200999999980E-04,
									        4.77725751100000020E-03,
									        -1.07730108500000000E-03};
	
	//wavelet koeficienty
	private final static double[] WAVELET = {	-SCALE[11], SCALE[10], 
												-SCALE[9], SCALE[8], 
												-SCALE[7], SCALE[6], 
												-SCALE[5], SCALE[4], 
												-SCALE[3], SCALE[2], 
												-SCALE[1], SCALE[0]};


	
	//škálové koeficienty pro inverzní transformaci
	private final static double[] I_SCALE = {	SCALE[10], WAVELET[10],
												SCALE[8], WAVELET[8],
												SCALE[6], WAVELET[6], 
												SCALE[4], WAVELET[4], 
												SCALE[2], WAVELET[2], 
												SCALE[0], WAVELET[0]};
	
	//wavelet koeficienty pro inverzní transformaci
	private final static double[] I_WAVELET = {	SCALE[11], WAVELET[11],
												SCALE[9], WAVELET[9],
												SCALE[7], WAVELET[7], 
												SCALE[5], WAVELET[5], 
												SCALE[3], WAVELET[3], 
												SCALE[1], WAVELET[1]};
	
	
	/**
	 * Konstruktor waveletu Daubechies12.
	 */
	public Daubechies12()
	{
		super(NAME, SCALE, WAVELET, I_SCALE, I_WAVELET);
	}
}