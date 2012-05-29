package pilsner.wt.dwt.wavelets;

/**
 * Tøída waveletu Daubechies18.
 * Obsahuje veškeré koeficienty pro dopøednou i inverzní
 * diskrétní waveletovou transformaci.
 */
public class Daubechies18 extends WaveletDWT
{
	//název waveletu
	private final static String NAME = "Daubechies_18";
	
	//škálové koeficienty
	private final static double[] SCALE = {	3.80779473639999980E-02,
									        2.43834674613000010E-01,
									        6.04823123690000020E-01,
									        6.57288078050999980E-01,
									        1.33197385824999990E-01,
									        -2.93273783279000000E-01,
									        -9.68407832229999930E-02,
									        1.48540749337999990E-01,
									        3.07256814790000010E-02,
									        -6.76328290610000020E-02,
									        2.50947114999999980E-04,
									        2.23616621239999990E-02,
									        -4.72320475800000040E-03,
									        -4.28150368199999970E-03,
									        1.84764688300000000E-03,
									        2.30385764000000010E-04,
									        -2.51963189000000020E-04,
									        3.93473200000000030E-05};
	
	//wavelet koeficienty
	private final static double[] WAVELET = {	-SCALE[17], SCALE[16],
	                                         	-SCALE[15], SCALE[14],
												-SCALE[13], SCALE[12],
												-SCALE[11], SCALE[10],
	                                         	-SCALE[9], SCALE[8],
												-SCALE[7], SCALE[6], 
												-SCALE[5], SCALE[4], 
												-SCALE[3], SCALE[2], 
												-SCALE[1], SCALE[0]};


	
	//škálové koeficienty pro inverzní transformaci
	private final static double[] I_SCALE = {	SCALE[16], WAVELET[16],
												SCALE[14], WAVELET[14],
												SCALE[12], WAVELET[12], 
												SCALE[10], WAVELET[10], 
												SCALE[8], WAVELET[8],
												SCALE[6], WAVELET[6], 
												SCALE[4], WAVELET[4], 
												SCALE[2], WAVELET[2], 
												SCALE[0], WAVELET[0]};
	
	//wavelet koeficienty pro inverzní transformaci
	private final static double[] I_WAVELET = {	SCALE[17], WAVELET[17],
												SCALE[15], WAVELET[15], 
												SCALE[13], WAVELET[13], 
												SCALE[11], WAVELET[11], 
												SCALE[9], WAVELET[9],
												SCALE[7], WAVELET[7], 
												SCALE[5], WAVELET[5], 
												SCALE[3], WAVELET[3], 
												SCALE[1], WAVELET[1]};
	
	
	/**
	 * Konstruktor waveletu Daubechies18.
	 */
	public Daubechies18()
	{
		super(NAME, SCALE, WAVELET, I_SCALE, I_WAVELET);
	}
}
