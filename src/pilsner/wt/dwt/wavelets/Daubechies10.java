package pilsner.wt.dwt.wavelets;

/**
 * Tøída waveletu Daubechies10.
 * Obsahuje veškeré koeficienty pro dopøednou i inverzní
 * diskrétní waveletovou transformaci.
 */
public class Daubechies10 extends WaveletDWT
{
	//název waveletu
	private final static String NAME = "Daubechies_10";
	
	//škálové koeficienty
	private final static double[] SCALE = {	1.60102397974000000E-01,
									        6.03829269797000020E-01,
									        7.24308528437999980E-01,
									        1.38428145901000000E-01,
									        -2.42294887066000000E-01,
									        -3.22448695850000020E-02,
									        7.75714938400000050E-02,
									        -6.24149021300000020E-03,
									        -1.25807519990000000E-02,
									        3.33572528500000010E-03};
	
	//wavelet koeficienty
	private final static double[] WAVELET = {	-SCALE[9], SCALE[8], 
												-SCALE[7], SCALE[6], 
												-SCALE[5], SCALE[4], 
												-SCALE[3], SCALE[2], 
												-SCALE[1], SCALE[0]};


	
	//škálové koeficienty pro inverzní transformaci
	private final static double[] I_SCALE = {	SCALE[8], WAVELET[8],
												SCALE[6], WAVELET[6], 
												SCALE[4], WAVELET[4], 
												SCALE[2], WAVELET[2], 
												SCALE[0], WAVELET[0]};
	
	//wavelet koeficienty pro inverzní transformaci
	private final static double[] I_WAVELET = {	SCALE[9], WAVELET[9],
												SCALE[7], WAVELET[7], 
												SCALE[5], WAVELET[5], 
												SCALE[3], WAVELET[3], 
												SCALE[1], WAVELET[1]};
	
	
	/**
	 * Konstruktor waveletu Daubechies10.
	 */
	public Daubechies10()
	{
		super(NAME, SCALE, WAVELET, I_SCALE, I_WAVELET);
	}
}
