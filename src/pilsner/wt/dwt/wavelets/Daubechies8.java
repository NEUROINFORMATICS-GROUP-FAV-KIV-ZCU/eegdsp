package pilsner.wt.dwt.wavelets;

/**
 * Tøída waveletu Daubechies8.
 * Obsahuje veškeré koeficienty pro dopøednou i inverzní
 * diskrétní waveletovou transformaci.
 */
public class Daubechies8 extends WaveletDWT
{
	//název waveletu
	private final static String NAME = "Daubechies_8";
	
	//škálové koeficienty
	private final static double[] SCALE = {	2.30377813309000010E-01,
											7.14846570553000050E-01,
											6.30880767930000030E-01,
											-2.79837694169999990E-02,
											-1.87034811718999990E-01,
											3.08413818359999990E-02,
											3.28830116670000010E-02,
											-1.05974017850000000E-02};
	
	//wavelet koeficienty
	private final static double[] WAVELET = {	-SCALE[7], SCALE[6], 
												-SCALE[5], SCALE[4], 
												-SCALE[3], SCALE[2], 
												-SCALE[1], SCALE[0]};


	
	//škálové koeficienty pro inverzní transformaci
	private final static double[] I_SCALE = {	SCALE[6], WAVELET[6], 
												SCALE[4], WAVELET[4], 
												SCALE[2], WAVELET[2], 
												SCALE[0], WAVELET[0]};
	
	//wavelet koeficienty pro inverzní transformaci
	private final static double[] I_WAVELET = {	SCALE[7], WAVELET[7], 
												SCALE[5], WAVELET[5], 
												SCALE[3], WAVELET[3], 
												SCALE[1], WAVELET[1]};
	
	
	/**
	 * Konstruktor waveletu Daubechies8.
	 */
	public Daubechies8()
	{
		super(NAME, SCALE, WAVELET, I_SCALE, I_WAVELET);
	}
}
