package pilsner.wt.dwt;

import java.util.Arrays;

import pilsner.utils.math.Power2Utils;
import pilsner.wt.dwt.wavelets.WaveletDWT;

/**
 * Tøída diskrétní waveletové transfromace.
 */
public class DWT
{
	//typ waveletu
	private WaveletDWT wavelet;
	
	//délka waveletu
	private int waveletLength;
	
	//délka waveletu
	private int majorWaveletLengthOfPow2;
	
	//pole koeficientù vypoètených dwt
	private double[] dwtData;
	
	//pole hodnot rekonstruovaného signálu
	private double[] reconstructedSignal;
	
	/**
	 * Konstruktor DWT_algotithm.
	 * 
	 * @param wavelet - wavelet.
	 */
	public DWT(WaveletDWT wavelet)
	{
		this.wavelet = wavelet;
		this.waveletLength = wavelet.getScaleArray().length;
		this.majorWaveletLengthOfPow2 = Power2Utils.newMajorNumberOfPowerBase2(waveletLength);
	}
	
	
	/**
	 * Metoda upravuje signál a spouští transformaci, pokud má signál délku waveletu nebo vìtší.
	 * Koneèná úroveò transformace je odvozena od délky signálu.
	 * 
	 * @param inputSignal - originální vstupní signál.  
	 * @return signál po transformaci.
	 */
	public double[] transform(double[] inputSignal)
	{		
		dwtData = Power2Utils.signalPowerBase2(inputSignal);		

		for (int last = dwtData.length; last >= majorWaveletLengthOfPow2; last /= 2)
		{
			transform(dwtData, last);
		}
		
		return dwtData;
	}

	/**
	 * Metoda spouští inverzní transformaci, pokud má pole koeficientù délku waveletu nebo vìtší. 
	 * 
	 * @param inputCoef - transformované vstupní koeficienty.  
	 * @return signál po inverzní transformaci.
	 */
	public double[] invTransform(double[] inputCoef)
	{
		reconstructedSignal = Power2Utils.signalPowerBase2(inputCoef);		
		
		for (int last = majorWaveletLengthOfPow2; last <= reconstructedSignal.length; last *= 2)
		{
			invTransform(reconstructedSignal, last);
		}
		
		return reconstructedSignal;
	}
	
	/**
	 * Metoda transformující signál pomocí nastaveného waveletu.
	 * V první polovinì úseku transformovaného signálu je ukládáná aproximaèní složka
	 * získaná škálovými koeficienty a v druhé polovinì je ukládána detailní složka
	 * získaná pomocí waveletových koeficientù.
	 * 
	 * 
	 * @param signal - transformovaný signál.
	 * @param last - délka transformovaného úseku signálu.
	 */
	private void transform(double[] signal, int last)
	{
		int half = last/2;
		double tmp[] = new double[last];
		int i = 0, j, k;
			
		for (j = 0; j < last; j += 2)
		{
			for(k = 0; k < waveletLength; k++)
			{
				tmp[i] += signal[(j+k)%last] * wavelet.getScaleCoef(k);					
				tmp[i + half] += signal[(j+k)%last] * wavelet.getWaveletCoef(k);
			}
			
			i++;
		}
			
		
		for (i = 0; i < last; i++)
		{
			signal[i] = tmp[i];
		}		
	} 

	/**
	 * Metoda transformující koeficienty zpátky na originální signál 
	 * pomocí nastaveného waveletu. 
	 * Skládá pùvodní signál z aproximaèních a detailních složek
	 * transformovaného signálu.
	 * 
	 * pø:
	 * tmp[0] = [s6....s0 s2 s4 | s7....s1 s3 s5]
	 * tmp[1] = [w6....w0 w2 w4 | w7....w1 w3 w5]
	 * ...
	 * tmp[.] = [s0 s2 s4 s6 .. | s1 s3 s5 s7 ..]
	 * tmp[.] = [w0 w2 w4 w6 .. | w1 w3 w5 w7 ..]
	 * ...
	 * tmp[n-2] = [.. s0 s2 s4 s6 | .. s1 s3 s5 s7]
	 * tmp[n-1] = [.. w0 w2 w4 w6 | .. w1 w3 w5 w7]
	 * 
	 * @param coef - transformovaný signál.
	 * @param last - délka transformovaného úseku signálu.
	 */
	private void invTransform(double[] coef, int last)
	{
		double tmp[] = new double[last];
		int half = last/2;
		int i, j = 0, k, index;
		
		i = half - ((waveletLength/2) - 1);
			
		for (index = 0; index < half; index++)
		{
			for(k = 0; k < waveletLength - 1; k += 2)
			{
				tmp[j] += 	coef[(i+(k/2))%half] * wavelet.getIScaleCoef(k) + 
							coef[((i+(k/2))%half) + half] * wavelet.getIScaleCoef(k+1);
				
				tmp[j + 1] += 	coef[(i+(k/2))%half] * wavelet.getIWaveletCoef(k) + 
								coef[((i+(k/2))%half) + half] * wavelet.getIWaveletCoef(k+1);
			}
			
			j += 2;
			
			i++;
		}
			
		for (i = 0; i < last; i++)
		{
			coef[i] = tmp[i];
		}		
	}		
}