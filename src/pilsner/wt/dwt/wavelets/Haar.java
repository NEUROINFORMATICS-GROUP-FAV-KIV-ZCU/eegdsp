package pilsner.wt.dwt.wavelets;

/**
 * Tøída waveletu Haar.
 * Obsahuje veškeré koeficienty pro dopøednou i inverzní
 * diskrétní waveletovou transformaci.
 */
public class Haar extends WaveletDWT
{
	//název waveletu
	private static final String NAME = "Haar";
	
	//škálové koeficienty
	private static final double[] SCALE = {1.0 / Math.sqrt(2), 1.0 / Math.sqrt(2)};
	
	//wavelet koeficienty
	private static final double[] WAVELET = {-SCALE[1], SCALE[0]};
	
	//škálové koeficienty pro inverzní transformaci
	private static final double[] I_SCALE = { SCALE[0], WAVELET[0]};
	
	//wavelet koeficienty pro inverzní transformaci
	private static final double[] I_WAVELET = { SCALE[1], WAVELET[1]};
	
	/**
	 * Konstruktor waveletu Haar.
	 */
	public Haar()
	{
		super(NAME, SCALE, WAVELET, I_SCALE, I_WAVELET);
	}
}
