package pilsner.wt.dwt.wavelets;

/**
 * Tøída waveletu Coiflet12.
 * Obsahuje veškeré koeficienty pro dopøednou i inverzní
 * diskrétní waveletovou transformaci.
 */
public class Coiflet12 extends WaveletDWT
{
	//název waveletu
	private final static String NAME = "Coiflet_12";
	
	//škálové koeficienty
	private final static double[] SCALE = {	1.63873364631797850E-02,
									        -4.14649367819664850E-02,
									        -6.73725547222998740E-02,
									        3.86110066823092900E-01,
									        8.12723635449606130E-01,
									        4.17005184423777600E-01,
									        -7.64885990782645940E-02,
									        -5.94344186464712400E-02,
									        2.36801719468767500E-02,
									        5.61143481936598850E-03,
									        -1.82320887091009920E-03,
									        -7.20549445368115120E-04};
	
	//wavelet koeficienty
	private final static double[] WAVELET = {	-SCALE[11], SCALE[10], 
												-SCALE[9], SCALE[8], 
												-SCALE[7], SCALE[6], 
												-SCALE[5], SCALE[4], 
												-SCALE[3], SCALE[2], 
												-SCALE[1], SCALE[0]};


	
	//škálové koeficienty pro inverzní transformaci
	private final static double[] I_SCALE = {	SCALE[10], WAVELET[10],
												SCALE[8], WAVELET[8],
												SCALE[6], WAVELET[6], 
												SCALE[4], WAVELET[4], 
												SCALE[2], WAVELET[2], 
												SCALE[0], WAVELET[0]};
	
	//wavelet koeficienty pro inverzní transformaci
	private final static double[] I_WAVELET = {	SCALE[11], WAVELET[11],
												SCALE[9], WAVELET[9],
												SCALE[7], WAVELET[7], 
												SCALE[5], WAVELET[5], 
												SCALE[3], WAVELET[3], 
												SCALE[1], WAVELET[1]};
	
	
	/**
	 * Konstruktor waveletu Coiflet12.
	 */
	public Coiflet12()
	{
		super(NAME, SCALE, WAVELET, I_SCALE, I_WAVELET);
	}
}
