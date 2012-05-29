package pilsner.wt.dwt.wavelets;

/**
 * T��da waveletu Coiflet6.
 * Obsahuje ve�ker� koeficienty pro dop�ednou i inverzn�
 * diskr�tn� waveletovou transformaci.
 */
public class Coiflet6 extends WaveletDWT
{
	//n�zev waveletu
	private final static String NAME = "Coiflet_6";
	
	//�k�lov� koeficienty
	private final static double[] SCALE = {-7.27326195125265103E-02,
											3.37897662457481832E-01,
											8.52572020211597717E-01,
											3.84864846864857623E-01,
										   -7.27326195125265103E-02,
										   -1.56557281357920009E-02};
	
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
	 * Konstruktor waveletu Coiflet6.
	 */
	public Coiflet6()
	{
		super(NAME, SCALE, WAVELET, I_SCALE, I_WAVELET);
	}
}
