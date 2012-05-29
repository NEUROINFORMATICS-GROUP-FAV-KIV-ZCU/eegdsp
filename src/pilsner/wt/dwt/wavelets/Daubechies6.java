package pilsner.wt.dwt.wavelets;

/**
 * T��da waveletu Daubechies6.
 * Obsahuje ve�ker� koeficienty pro dop�ednou i inverzn�
 * diskr�tn� waveletovou transformaci.
 */
public class Daubechies6 extends WaveletDWT
{
	//n�zev waveletu
	private final static String NAME = "Daubechies_6";
	
	//�k�lov� koeficienty
	private final static double[] SCALE = {	3.32670552950082630E-01,
                                            8.06891509311092550E-01,
                                            4.59877502118491540E-01,
                                            -1.35011020010254580E-01,
                                            -8.54412738820266580E-02,
                                            3.52262918857095330E-02};
	
	//wavelet koeficienty
	private final static double[] WAVELET = {	-SCALE[5], SCALE[4], 
												-SCALE[3], SCALE[2], 
												-SCALE[1], SCALE[0]};


	
	//�k�lov� koeficienty pro inverzn� transformaci
	private final static double[] I_SCALE = {	SCALE[4], WAVELET[4], 
												SCALE[2], WAVELET[2], 
												SCALE[0], WAVELET[0]};
	
	//wavelet koeficienty pro inverzn� transformaci
	private final static double[] I_WAVELET = {	SCALE[5], WAVELET[5], 
												SCALE[3], WAVELET[3], 
												SCALE[1], WAVELET[1]};
	
	
	/**
	 * Konstruktor waveletu Daubechies6.
	 */
	public Daubechies6()
	{
		super(NAME, SCALE, WAVELET, I_SCALE, I_WAVELET);
	}
}
