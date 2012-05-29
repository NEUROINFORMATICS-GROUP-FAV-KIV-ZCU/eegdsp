package pilsner.wt.dwt.wavelets;


/**
 * T��da waveletu Symmlet8.
 * Obsahuje ve�ker� koeficienty pro dop�ednou i inverzn�
 * diskr�tn� waveletovou transformaci.
 */
public class Symmlet8 extends WaveletDWT
{
	
	//n�zev waveletu
	private final static String NAME = "Symmlet_8";
	
	//�k�lov� koeficienty
	private static final double[] SCALE = {	0.0322231006040782,
											-0.0126039672622638,
											-0.0992195435769564,
											0.297857795605605,
											0.803738751805386,
											0.497618667632563,
											-0.0296355276459604,
											-0.0757657147893567 };
	
	//wavelet koeficienty
	private static final double[] WAVELET = {	-SCALE[7], SCALE[6], -SCALE[5], 
												SCALE[4], -SCALE[3], SCALE[2], 
												-SCALE[1], SCALE[0]};
	
	//�k�lov� koeficienty pro inverzn� transformaci
	private static final double[] I_SCALE = {SCALE[6], WAVELET[6], 
											SCALE[4], WAVELET[4], 
											SCALE[2], WAVELET[2], 
											SCALE[0], WAVELET[0]};
	
	//wavelet koeficienty pro inverzn� transformaci
	private static final double[] I_WAVELET = {	SCALE[7], WAVELET[7], 
												SCALE[5], WAVELET[5], 
												SCALE[3], WAVELET[3], 
												SCALE[1], WAVELET[1]};
	
	/**
	 * Konstruktor waveletu Symmlet8.
	 */
	public Symmlet8()
	{
		super(NAME, SCALE, WAVELET, I_SCALE, I_WAVELET);
	}
}
