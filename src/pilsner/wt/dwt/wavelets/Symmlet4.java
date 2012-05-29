package pilsner.wt.dwt.wavelets;


/**
 * Tøída waveletu Symmlet4.
 * Obsahuje veškeré koeficienty pro dopøednou i inverzní
 * diskrétní waveletovou transformaci.
 */
public class Symmlet4 extends WaveletDWT
{
	
	//název waveletu
	private final static String NAME = "Symmlet_4";
	
	//škálové koeficienty
	private static final double[] SCALE = {	4.82968073686035690E-01,
											8.36521464279309452E-01,
											2.24138707500511834E-01,
											-1.29409026238512436E-01};
	
	//wavelet koeficienty
	private static final double[] WAVELET = {	-SCALE[3], SCALE[2], 
												-SCALE[1], SCALE[0]};
	
	//škálové koeficienty pro inverzní transformaci
	private static final double[] I_SCALE = {	SCALE[2], WAVELET[2], 
												SCALE[0], WAVELET[0]};
	
	//wavelet koeficienty pro inverzní transformaci
	private static final double[] I_WAVELET = {	SCALE[3], WAVELET[3], 
												SCALE[1], WAVELET[1]};
	
	/**
	 * Konstruktor waveletu Symmlet4.
	 */
	public Symmlet4()
	{
		super(NAME, SCALE, WAVELET, I_SCALE, I_WAVELET);
	}
}
