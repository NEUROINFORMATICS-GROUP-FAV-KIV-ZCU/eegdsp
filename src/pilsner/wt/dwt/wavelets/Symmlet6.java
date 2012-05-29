package pilsner.wt.dwt.wavelets;


/**
 * T��da waveletu Symmlet6.
 * Obsahuje ve�ker� koeficienty pro dop�ednou i inverzn�
 * diskr�tn� waveletovou transformaci.
 */
public class Symmlet6 extends WaveletDWT
{
	
	//n�zev waveletu
	private final static String NAME = "Symmlet_6";
	
	//�k�lov� koeficienty
	private static final double[] SCALE = { 3.32665456277023148E-01,	
											8.06893690147593111E-01,
											4.59873966212483048E-01,
										   -1.35010726159072265E-01,
										   -8.54411265843329105E-02,
											3.52266456251514246E-02};
	
	//wavelet koeficienty
	private static final double[] WAVELET = {	-SCALE[5], SCALE[4], 
												-SCALE[3], SCALE[2], 
												-SCALE[1], SCALE[0]};
	
	//�k�lov� koeficienty pro inverzn� transformaci
	private static final double[] I_SCALE = {	SCALE[4], WAVELET[4], 
												SCALE[2], WAVELET[2], 
												SCALE[0], WAVELET[0]};
	
	//wavelet koeficienty pro inverzn� transformaci
	private static final double[] I_WAVELET = {	SCALE[5], WAVELET[5], 
												SCALE[3], WAVELET[3], 
												SCALE[1], WAVELET[1]};
	
	/**
	 * Konstruktor waveletu Symmlet6.
	 */
	public Symmlet6()
	{
		super(NAME, SCALE, WAVELET, I_SCALE, I_WAVELET);
	}
}
