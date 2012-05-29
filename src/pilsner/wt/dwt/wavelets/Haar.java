package pilsner.wt.dwt.wavelets;

/**
 * T��da waveletu Haar.
 * Obsahuje ve�ker� koeficienty pro dop�ednou i inverzn�
 * diskr�tn� waveletovou transformaci.
 */
public class Haar extends WaveletDWT
{
	//n�zev waveletu
	private static final String NAME = "Haar";
	
	//�k�lov� koeficienty
	private static final double[] SCALE = {1.0 / Math.sqrt(2), 1.0 / Math.sqrt(2)};
	
	//wavelet koeficienty
	private static final double[] WAVELET = {-SCALE[1], SCALE[0]};
	
	//�k�lov� koeficienty pro inverzn� transformaci
	private static final double[] I_SCALE = { SCALE[0], WAVELET[0]};
	
	//wavelet koeficienty pro inverzn� transformaci
	private static final double[] I_WAVELET = { SCALE[1], WAVELET[1]};
	
	/**
	 * Konstruktor waveletu Haar.
	 */
	public Haar()
	{
		super(NAME, SCALE, WAVELET, I_SCALE, I_WAVELET);
	}
}
