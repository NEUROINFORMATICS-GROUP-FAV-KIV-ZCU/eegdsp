package pilsner.wt.cwt;

import pilsner.wt.cwt.wavelets.WaveletCWT;

/**
 * T��da spojit� waveletov� transfromace.
 */
public class CWT
{
	//parametry m���tka waveletu
	private double minScale;
	private double maxScale;
	private double stepScale;
	
	//typ waveletu
	private WaveletCWT wavelet;
	
	//pole realn�ch koeficient� vypo�ten�ch cwt
	private double[][] cwtDataReal;	
	//pole imagin�rn�ch koeficient� vypo�ten�ch cwt
	private double[][] cwtDataImag;
	//pole modulus koeficient� komplexn� cwt
	private double[][] modulusCwtData;	
	//pole angle koeficient� komplexn� cwt
	private double[][] angleCwtData;
	//pole angle koeficient� komplexn� cwt
	private double[] reconstructedSignal;
	
	/**
	 * Konstruktor CWT_algotithm.
	 * 
	 * @param minScale - spodn� hranice m���tka.
	 * @param maxScale - horn� hranice m���tka.
	 * @param stepScale - krok m���tka.
	 * @param wavelet - mate�sk� wavelet.
	 */
	public CWT(double minScale, double maxScale, double stepScale, WaveletCWT wavelet)
	{
		this.maxScale = maxScale;
		this.minScale = minScale;
		this.stepScale = stepScale;
		this.wavelet = wavelet;
	}
	
	/**
	 * Spojit� waveletov� transformace.
	 * 
	 * @param inputSignal - vstupn� sign�l.
	 * @return dvourozm�rn� pole koeficient� po transformaci.
	 */
	public void transform(double[] inputSignal)
	{
		cwtDataReal = new double[(int) ((maxScale - minScale)/stepScale) + 1][inputSignal.length];
		cwtDataImag = new double[cwtDataReal.length][inputSignal.length];
		modulusCwtData = new double[cwtDataReal.length][inputSignal.length];
		angleCwtData = new double[cwtDataReal.length][inputSignal.length];
		int b, x, scale = 0;
		double t, a;
		
		
		for (a = minScale; a <= maxScale; a += stepScale)
		{			
			for (b = 0; b < inputSignal.length; b++)
			{
				cwtDataReal[scale][b] = 0;
				cwtDataImag[scale][b] = 0;
				
				for (x = 0; x < inputSignal.length; x++)
				{						
					t = (x - b)/a;
					
					cwtDataReal[scale][b] += inputSignal[x] * wavelet.reCoef(t, a);
					cwtDataImag[scale][b] += inputSignal[x] * wavelet.imCoef(t, a);
				}		
				
				modulusCwtData[scale][b] = Math.sqrt(Math.pow(cwtDataReal[scale][b], 2.0)
											+ Math.pow(cwtDataImag[scale][b], 2.0));
				angleCwtData[scale][b] = Math.atan2(cwtDataImag[scale][b],cwtDataReal[scale][b]);
			}	
			
			scale++;
		}
	}
	
	
	/**
	 * Inverzn� spojit� waveletov� transformace.
	 * 
	 * @param inputSignal - vstupn� sign�l.
	 * @return dvourozm�rn� pole koeficient� po transformaci.
	 */
	public void invTransform()
	{
		reconstructedSignal = new double[cwtDataReal[0].length];
		int b, x, scale = 0;
		double t, a;
		
		
		for (a = maxScale; a >= minScale; a -= stepScale)
		{			
			for (b = 0; b < reconstructedSignal.length; b++)
			{						
				for (x = 0; x < reconstructedSignal.length; x++)
				{			
					t = (x - b)/a;
					
					reconstructedSignal[b] += cwtDataReal[scale][b] * wavelet.reCoef(t, a);
				}		
			}	
			
			scale++;
		}
	}
	
	
	/**
	 * Nastaven� spodn� hranice m���tka.
	 * 
	 * @param minScale - spodn� hranice m���tka.
	 */
	public void setMinScale(double minScale)
	{
		this.minScale = minScale;
	}
	
	/**
	 * Nastaven� horn� hranice m���tka.
	 * 
	 * @param maxScale - horn� hranice m���tka.
	 */
	public void setMaxScale(double maxScale)
	{
		this.maxScale = maxScale;
	}
	
	/**
	 * Nastaven� kroku m���tka.
	 * 
	 * @param stepScale - krok m���tka.
	 */
	public void setStepScale(double stepScale)
	{
		this.stepScale = stepScale;
	}
	
	/**
	 * Nastaven� waveletu.
	 * 
	 * @param wavelet - mate�sk� wavelet.
	 */
	public void setWavelet(WaveletCWT wavelet)
	{
		this.wavelet = wavelet;
	}
	
	/**
	 * Nastaven� parametr� pro cwt.
	 * 
	 * @param minScale - spodn� hranice m���tka.
	 * @param maxScale - horn� hranice m���tka.
	 * @param stepScale - krok m���tka.
	 * @param wavelet - wavelet.
	 */
	public void setCwtParameters(double minScale, double maxScale, double stepScale, WaveletCWT wavelet)
	{
		this.maxScale = maxScale;
		this.minScale = minScale;
		this.stepScale = stepScale;
		this.wavelet = wavelet;
	}
	
	/**
	 * @return spodn� hranice m���tka.
	 */
	public double getMinScale()
	{
		return minScale;
	}
	
	/**
	 * @return spodn� hranice m���tka.
	 */
	public double getMaxScale()
	{
		return maxScale;
	}
	
	/**
	 * @return krok m���tka.
	 */
	public double getStepScale()
	{
		return stepScale;
	}
	
	/**
	 * @return mate�sk� wavelet.
	 */
	public WaveletCWT getWavelet()
	{
		return wavelet;
	}
	
	/**
	 * @return pole re�ln�ch koeficient� vypo�ten�ch cwt.
	 */
	public double[][] getCwtDataReal()
	{
		return cwtDataReal;
	}
	
	/**
	 * @return pole imagin�rn�ch koeficient� vypo�ten�ch cwt.
	 */
	public double[][] getCwtDataImag()
	{		
		return cwtDataImag;
	}
	
	/**
	 * @return pole modulus koeficient� vypo�ten�ch komplexn� cwt.
	 */
	public double[][] getModulusCwtData()
	{		
		return modulusCwtData;
	}
	
	/**
	 * @return pole angle koeficient� vypo�ten�ch komplexn� cwt.
	 */
	public double[][] getAngleCwtData()
	{
		return angleCwtData;
	}
	
	/**
	 * @return rekonstruovan� sign�l z cwt koeficient�.
	 */
	public double[] getReconstructedSignal()
	{
		return reconstructedSignal;
	}
}
