package pilsner.wt.dwt.wavelets;

/**
 * Tøída waveletu Coiflet30.
 * Obsahuje veškeré koeficienty pro dopøednou i inverzní
 * diskrétní waveletovou transformaci.
 */
public class Coiflet30 extends WaveletDWT
{
	//název waveletu
	private final static String NAME = "Coiflet_30";
	
	//škálové koeficienty
	private final static double[] SCALE = {	-2.12080863336306810E-04,
									         3.58589677255698600E-04,
									         2.17823630484128470E-03,
									        -4.15935878160399350E-03,
									        -1.01311175380455940E-02,
									         2.34081567615927950E-02,
									         2.81680290621414970E-02,
									        -9.19200105488064130E-02,
									        -5.20431632162377390E-02,
									         4.21566206728765440E-01,
									         7.74289603740284550E-01,
									         4.37991626228364130E-01,
									        -6.20359639056089690E-02,
									        -1.05574208705835340E-01,
									         4.12892087407341690E-02,
									         3.26835742832495350E-02,
									        -1.97617790117239590E-02,
									        -9.16423115304622680E-03,
									         6.76418541866332000E-03,
									         2.43337320922405380E-03,
									        -1.66286376908581340E-03,
									        -6.38131296151377520E-04,
									         3.02259519791840680E-04,
									         1.40541148901077230E-04,
									        -4.13404844919568560E-05,
									        -2.13150140622449170E-05,
									         3.73459674967156050E-06,
									         2.06380639023316330E-06,
									        -1.67408293749300630E-07,
									        -9.51579170468293560E-08};
	
	//wavelet koeficienty
	private final static double[] WAVELET = {	-SCALE[29], SCALE[28],
										     	-SCALE[27], SCALE[26],
												-SCALE[25], SCALE[24],
												-SCALE[23], SCALE[22],
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
	private final static double[] I_SCALE = {	SCALE[28], WAVELET[28],
												SCALE[26], WAVELET[26],
												SCALE[24], WAVELET[24],
												SCALE[22], WAVELET[22],
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
	private final static double[] I_WAVELET = {	SCALE[29], WAVELET[29],
												SCALE[27], WAVELET[27], 
												SCALE[25], WAVELET[25],
												SCALE[23], WAVELET[23],
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
	 * Konstruktor waveletu Coiflet30.
	 */
	public Coiflet30()
	{
		super(NAME, SCALE, WAVELET, I_SCALE, I_WAVELET);
	}
}
