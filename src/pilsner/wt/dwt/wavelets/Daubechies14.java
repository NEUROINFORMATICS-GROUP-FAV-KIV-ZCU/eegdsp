package pilsner.wt.dwt.wavelets;

/**
 * Tøída waveletu Daubechies14.
 * Obsahuje veškeré koeficienty pro dopøednou i inverzní
 * diskrétní waveletovou transformaci.
 */
public class Daubechies14 extends WaveletDWT
{
	//název waveletu
	private final static String NAME = "Daubechies_14";
	
	//škálové koeficienty
	private final static double[] SCALE = {	7.78520540849999970E-02,
									        3.96539319482000000E-01,
									        7.29132090845999950E-01,
									        4.69782287405000000E-01,
									        -1.43906003928999990E-01,
									        -2.24036184993999990E-01,
									        7.13092192669999990E-02,
									        8.06126091510000060E-02,
									        -3.80299369350000010E-02,
									        -1.65745416310000000E-02,
									        1.25509985560000000E-02,
									        4.29577973000000010E-04,
									        -1.80164070400000000E-03,
									        3.53713800000000020E-04};
	
	//wavelet koeficienty
	private final static double[] WAVELET = {	-SCALE[13], SCALE[12],
												-SCALE[11], SCALE[10],
												-SCALE[9], SCALE[8],
												-SCALE[7], SCALE[6], 
												-SCALE[5], SCALE[4], 
												-SCALE[3], SCALE[2], 
												-SCALE[1], SCALE[0]};


	
	//škálové koeficienty pro inverzní transformaci
	private final static double[] I_SCALE = {	SCALE[12], WAVELET[12], 
												SCALE[10], WAVELET[10], 
												SCALE[8], WAVELET[8],
												SCALE[6], WAVELET[6], 
												SCALE[4], WAVELET[4], 
												SCALE[2], WAVELET[2], 
												SCALE[0], WAVELET[0]};
	
	//wavelet koeficienty pro inverzní transformaci
	private final static double[] I_WAVELET = {	SCALE[13], WAVELET[13], 
												SCALE[11], WAVELET[11], 
												SCALE[9], WAVELET[9], 
												SCALE[7], WAVELET[7], 
												SCALE[5], WAVELET[5], 
												SCALE[3], WAVELET[3], 
												SCALE[1], WAVELET[1]};
	
	
	/**
	 * Konstruktor waveletu Daubechies14.
	 */
	public Daubechies14()
	{
		super(NAME, SCALE, WAVELET, I_SCALE, I_WAVELET);
	}
}
