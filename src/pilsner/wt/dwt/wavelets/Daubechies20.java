package pilsner.wt.dwt.wavelets;

/**
 * Tøída waveletu Daubechies20.
 * Obsahuje veškeré koeficienty pro dopøednou i inverzní
 * diskrétní waveletovou transformaci.
 */
public class Daubechies20 extends WaveletDWT
{
	//název waveletu
	private final static String NAME = "Daubechies_20";
	
	//škálové koeficienty
	private final static double[] SCALE = {	2.66700579010000010E-02,
									        1.88176800078000000E-01,
									        5.27201188931999960E-01,
									        6.88459039454000000E-01,
									        2.81172343661000020E-01,
									        -2.49846424326999990E-01,
									        -1.95946274376999990E-01,
									        1.27369340336000000E-01,
									        9.30573646040000060E-02,
									        -7.13941471659999970E-02,
									        -2.94575368219999990E-02,
									        3.32126740589999970E-02,
									        3.60655356700000010E-03,
									        -1.07331754830000000E-02,
									        1.39535174700000000E-03,
									        1.99240529500000020E-03,
									        -6.85856695000000030E-04,
									        -1.16466855000000000E-04,
									        9.35886700000000050E-05,
									        -1.32642030000000010E-05};
	
	//wavelet koeficienty
	private final static double[] WAVELET = {	-SCALE[19], SCALE[18],
												-SCALE[17], SCALE[16],
										     	-SCALE[15], SCALE[14],
												-SCALE[13], SCALE[12],
												-SCALE[11], SCALE[10],
										     	-SCALE[9], SCALE[8],
												-SCALE[7], SCALE[6], 
												-SCALE[5], SCALE[4], 
												-SCALE[3], SCALE[2], 
												-SCALE[1], SCALE[0]};


	
	//škálové koeficienty pro inverzní transformaci
	private final static double[] I_SCALE = {	SCALE[18], WAVELET[18],
												SCALE[16], WAVELET[16],
												SCALE[14], WAVELET[14],
												SCALE[12], WAVELET[12], 
												SCALE[10], WAVELET[10], 
												SCALE[8], WAVELET[8],
												SCALE[6], WAVELET[6], 
												SCALE[4], WAVELET[4], 
												SCALE[2], WAVELET[2], 
												SCALE[0], WAVELET[0]};
	
	//wavelet koeficienty pro inverzní transformaci
	private final static double[] I_WAVELET = {	SCALE[19], WAVELET[19],
												SCALE[17], WAVELET[17],
												SCALE[15], WAVELET[15], 
												SCALE[13], WAVELET[13], 
												SCALE[11], WAVELET[11], 
												SCALE[9], WAVELET[9],
												SCALE[7], WAVELET[7], 
												SCALE[5], WAVELET[5], 
												SCALE[3], WAVELET[3], 
												SCALE[1], WAVELET[1]};
	
	
	/**
	 * Konstruktor waveletu Daubechies20.
	 */
	public Daubechies20()
	{
		super(NAME, SCALE, WAVELET, I_SCALE, I_WAVELET);
	}
}
