package pilsner.wt.dwt.wavelets;

/**
 * Rozhraní waveletu pro diskrétní waveletovou transformaci.
 */
public abstract class WaveletDWT
{
	private String name;
	private double[] scale;
	private double[] wavelet;
	private double[] iScale;
	private double[] iWavelet;
	
	
	/**
	 * Konstruktor WaveletDWT.
	 */
	public WaveletDWT(String name, double[] scale, double[] wavelet, double[] iScale, double[] iWavelet)
	{
		this.name = name;
		this.scale = scale;
		this.wavelet = wavelet;
		this.iScale = iScale;
		this.iWavelet = iWavelet;
	}
	
	/**
	 * @return pole škálových keoficientù.
	 */
    public double[] getScaleArray()
    {
    	return scale;
    }
    
    /**
	 * @return pole waveletových koeficientù.
	 */
    public double[] getWaveletArray()
    {
    	return wavelet;
    }
    
    /**
	 * @return pole škálových koeficientù pro inverzní dwt.
	 */
    public double[] getIScaleArray()
    {
    	return iScale;
    }
    
    /**
	 * @return pole waveletých koeficientù pro inverzní dwt.
	 */
    public double[] getIWaveletArray()
    {
    	return iWavelet;
    }
    
    /**
	 * @return škálový koeficient na indexu i.
	 */
    public double getScaleCoef(int i)
    {
    	return scale[i];
    }
    
    /**
	 * @return waveletový koeficient na indexu i.
	 */
    public double getWaveletCoef(int i)
    {
    	return wavelet[i];
    }
    
    /**
	 * @return škálový koeficient pro inverzní dwt na indexu i.
	 */
    public double getIScaleCoef(int i)
    {
    	return iScale[i];
    }
    
    /**
	 * @return waveletový koeficient pro inverzní dwt na indexu i.
	 */
    public double getIWaveletCoef(int i)
    {
    	return iWavelet[i];
    }    
    
    /**
     * @return název waveletu.
    */
    public String getName()
    {
        return name;
    }
}
