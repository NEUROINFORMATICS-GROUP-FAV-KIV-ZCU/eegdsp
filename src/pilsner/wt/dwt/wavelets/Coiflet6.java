package pilsner.wt.dwt.wavelets;

/**
 * Tøída waveletu Coiflet6.
 * Obsahuje veškeré koeficienty pro dopøednou i inverzní
 * diskrétní waveletovou transformaci.
 */
public class Coiflet6 extends WaveletDWT
{
	//název waveletu
	private final static String NAME = "Coiflet_6";
	
	//škálové koeficienty
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


	
	//škálové koeficienty pro inverzní transformaci
	private final static double[] I_SCALE = {	SCALE[4], WAVELET[4], 
												SCALE[2], WAVELET[2], 
												SCALE[0], WAVELET[0]};
	
	//wavelet koeficienty pro inverzní transformaci
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
