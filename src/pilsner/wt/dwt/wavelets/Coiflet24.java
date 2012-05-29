package pilsner.wt.dwt.wavelets;

/**
 * Tøída waveletu Coiflet24.
 * Obsahuje veškeré koeficienty pro dopøednou i inverzní
 * diskrétní waveletovou transformaci.
 */
public class Coiflet24 extends WaveletDWT
{
	//název waveletu
	private final static String NAME = "Coiflet_24";
	
	//škálové koeficienty
	private final static double[] SCALE = {	8.92313668220275710E-04,
									        -1.62949201311084900E-03,
									        -7.34616632765623490E-03,
									        1.60689439640692360E-02,
									        2.66823001556288040E-02,
									        -8.12666996803130540E-02,
									        -5.60773133164719500E-02,
									        4.15308407030430150E-01,
									        7.82238930920498790E-01,
									        4.34386056491468390E-01,
									        -6.66274742630007520E-02,
									        -9.62204420335636970E-02,
									        3.93344271229132190E-02,
									        2.50822618451469330E-02,
									        -1.52117315272391490E-02,
									        -5.65828668594603800E-03,
									        3.75143615692490270E-03,
									        1.26656192867951870E-03,
									        -5.89020756811437840E-04,
									        -2.59974552319421750E-04,
									        6.23390338657646180E-05,
									        3.12298760780433580E-05,
									        -3.25968044485761290E-06,
									        -1.78498455869993380E-06};
	
	//wavelet koeficienty
	private final static double[] WAVELET = {	-SCALE[23], SCALE[22],
										     	-SCALE[21], SCALE[20],
												-SCALE[19], SCALE[18],
												-SCALE[17], SCALE[16],
	                                         	-SCALE[15], SCALE[14],
												-SCALE[13], SCALE[12],
												-SCALE[11], SCALE[10],
	                                         	-SCALE[9], SCALE[8],
												-SCALE[7], SCALE[6], 
												-SCALE[5], SCALE[4], 
												-SCALE[3], SCALE[2], 
												-SCALE[1], SCALE[0]};


	
	//škálové koeficienty pro inverzní transformaci
	private final static double[] I_SCALE = {	SCALE[22], WAVELET[22],
												SCALE[20], WAVELET[20],
												SCALE[18], WAVELET[18],
												SCALE[16], WAVELET[16],
												SCALE[14], WAVELET[14],
												SCALE[12], WAVELET[12], 
												SCALE[10], WAVELET[10], 
												SCALE[8], WAVELET[8],
												SCALE[6], WAVELET[6], 
												SCALE[4], WAVELET[4], 
												SCALE[2], WAVELET[2], 
												SCALE[0], WAVELET[0]};
	
	//wavelet koeficienty pro inverzní transformaci
	private final static double[] I_WAVELET = {	SCALE[23], WAVELET[23],
												SCALE[21], WAVELET[21], 
												SCALE[19], WAVELET[19],
												SCALE[17], WAVELET[17],
												SCALE[15], WAVELET[15], 
												SCALE[13], WAVELET[13],
												SCALE[11], WAVELET[11], 
												SCALE[9], WAVELET[9],
												SCALE[7], WAVELET[7], 
												SCALE[5], WAVELET[5], 
												SCALE[3], WAVELET[3], 
												SCALE[1], WAVELET[1]};
	
	
	/**
	 * Konstruktor waveletu Coiflet24.
	 */
	public Coiflet24()
	{
		super(NAME, SCALE, WAVELET, I_SCALE, I_WAVELET);
	}
}