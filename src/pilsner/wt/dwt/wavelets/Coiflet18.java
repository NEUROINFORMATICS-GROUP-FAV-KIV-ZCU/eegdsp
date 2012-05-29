package pilsner.wt.dwt.wavelets;

/**
 * Tøída waveletu Coiflet18.
 * Obsahuje veškeré koeficienty pro dopøednou i inverzní
 * diskrétní waveletovou transformaci.
 */
public class Coiflet18 extends WaveletDWT
{
	//název waveletu
	private final static String NAME = "Coiflet_18";
	
	//škálové koeficienty
	private final static double[] SCALE = {	-3.79351286437787590E-03,
									        7.78259642567078690E-03,
									        2.34526961421191030E-02,
									        -6.57719112814312280E-02,
									        -6.11233900029556980E-02,
									        4.05176902409616790E-01,
									        7.93777222625620340E-01,
									        4.28483476377618690E-01,
									        -7.17998216191705900E-02,
									        -8.23019271063202830E-02,
									        3.45550275733444640E-02,
									        1.58805448636159010E-02,
									        -9.00797613673228960E-03,
									        -2.57451768812796920E-03,
									        1.11751877082696180E-03,
									        4.66216959820144030E-04,
									        -7.09833025057049280E-05,
									        -3.45997731974026950E-05};
	
	//wavelet koeficienty
	private final static double[] WAVELET = {	-SCALE[17], SCALE[16],
	                                         	-SCALE[15], SCALE[14],
												-SCALE[13], SCALE[12],
												-SCALE[11], SCALE[10],
	                                         	-SCALE[9], SCALE[8],
												-SCALE[7], SCALE[6], 
												-SCALE[5], SCALE[4], 
												-SCALE[3], SCALE[2], 
												-SCALE[1], SCALE[0]};


	
	//škálové koeficienty pro inverzní transformaci
	private final static double[] I_SCALE = {	SCALE[16], WAVELET[16],
												SCALE[14], WAVELET[14],
												SCALE[12], WAVELET[12], 
												SCALE[10], WAVELET[10], 
												SCALE[8], WAVELET[8],
												SCALE[6], WAVELET[6], 
												SCALE[4], WAVELET[4], 
												SCALE[2], WAVELET[2], 
												SCALE[0], WAVELET[0]};
	
	//wavelet koeficienty pro inverzní transformaci
	private final static double[] I_WAVELET = {	SCALE[17], WAVELET[17],
												SCALE[15], WAVELET[15], 
												SCALE[13], WAVELET[13], 
												SCALE[11], WAVELET[11], 
												SCALE[9], WAVELET[9],
												SCALE[7], WAVELET[7], 
												SCALE[5], WAVELET[5], 
												SCALE[3], WAVELET[3], 
												SCALE[1], WAVELET[1]};
	
	
	/**
	 * Konstruktor waveletu Coiflet18.
	 */
	public Coiflet18()
	{
		super(NAME, SCALE, WAVELET, I_SCALE, I_WAVELET);
	}
}
