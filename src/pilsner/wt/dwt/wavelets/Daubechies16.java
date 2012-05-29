package pilsner.wt.dwt.wavelets;

/**
 * Tøída waveletu Daubechies16.
 * Obsahuje veškeré koeficienty pro dopøednou i inverzní
 * diskrétní waveletovou transformaci.
 */
public class Daubechies16 extends WaveletDWT
{
	//název waveletu
	private final static String NAME = "Daubechies_16";
	
	//škálové koeficienty
	private final static double[] SCALE = {	5.44158422430000010E-02,
											3.12871590914000020E-01,
											6.75630736296999990E-01,
											5.85354683654000010E-01,
											-1.58291052559999990E-02,
											-2.84015542961999990E-01,
											4.72484573999999990E-04,
											1.28747426619999990E-01,
											-1.73693010020000010E-02,
											-4.40882539310000000E-02,
											1.39810279170000000E-02,
											8.74609404700000050E-03,
											-4.87035299299999960E-03,
											-3.91740372999999990E-04,
											6.75449405999999950E-04,											
											-1.17476784000000000E-04 };
	
	//wavelet koeficienty
	private final static double[] WAVELET = {	-SCALE[15], SCALE[14], 
												-SCALE[13], SCALE[12], 
												-SCALE[11], SCALE[10], 
												-SCALE[9], SCALE[8], 
												-SCALE[7], SCALE[6], 
												-SCALE[5], SCALE[4], 
												-SCALE[3], SCALE[2], 
												-SCALE[1], SCALE[0]};
	
	//škálové koeficienty pro inverzní transformaci
	private final static double[] I_SCALE = {	SCALE[14], WAVELET[14], 
												SCALE[12], WAVELET[12], 
												SCALE[10], WAVELET[10], 
												SCALE[8], WAVELET[8],
												SCALE[6], WAVELET[6], 
												SCALE[4], WAVELET[4], 
												SCALE[2], WAVELET[2], 
												SCALE[0], WAVELET[0]};
	
	//wavelet koeficienty pro inverzní transformaci
	private final static double[] I_WAVELET = {	SCALE[15], WAVELET[15], 
												SCALE[13], WAVELET[13], 
												SCALE[11], WAVELET[11], 
												SCALE[9], WAVELET[9],
												SCALE[7], WAVELET[7], 
												SCALE[5], WAVELET[5], 
												SCALE[3], WAVELET[3], 
												SCALE[1], WAVELET[1]};
	
	
	/**
	 * Konstruktor waveletu Daubechies16.
	 */
	public Daubechies16()
	{
		super(NAME, SCALE, WAVELET, I_SCALE, I_WAVELET);
	}
}
