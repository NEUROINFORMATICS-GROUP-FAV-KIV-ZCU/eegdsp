package pilsner.wt.dwt.wavelets;

/**
 * Tøída waveletu Daubechies4.
 * Obsahuje veškeré koeficienty pro dopøednou i inverzní
 * diskrétní waveletovou transformaci.
 */
public class Daubechies4 extends WaveletDWT
{
	//název waveletu
	private final static String NAME = "Daubechies_4";
	
	//parametry pro vypoèítání koeficientù waveletu
	private final static double SQRT_3 = Math.sqrt(3);
	private final static double DENOM = 4 * Math.sqrt(2);
	
	//škálové koeficienty
	private final static double[] SCALE = 	{	(1 + SQRT_3) / DENOM,
												(3 + SQRT_3) / DENOM,
												(3 - SQRT_3) / DENOM,
												(1 - SQRT_3) / DENOM};
	
	//wavelet koeficienty
	private final static double[] WAVELET = {	-SCALE[3], SCALE[2],
												-SCALE[1], SCALE[0]	};
	
	
	//škálové koeficienty pro inverzní transformaci
	private final static double[] I_SCALE = 	{	SCALE[2], WAVELET[2],
													SCALE[0], WAVELET[0]};
	
	//wavelet koeficienty pro inverzní transformaci
	private final static double[] I_WAVELET = 	{	SCALE[3], WAVELET[3],
													SCALE[1], WAVELET[1]};	
	
	/**
	 * Konstruktor waveletu Daubechies4.
	 */
	public Daubechies4()
	{
		super(NAME, SCALE, WAVELET, I_SCALE, I_WAVELET);
	}
}
