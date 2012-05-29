package pilsner.spectra;

import org.apache.commons.math.MathException;
import org.apache.commons.math.complex.Complex;
import org.apache.commons.math.transform.FastFourierTransformer;

import pilsner.signalwindows.HammingWindow;
import pilsner.signalwindows.Window;
import pilsner.utils.math.Convolution;
import pilsner.utils.math.Power2Utils;

/**
 * T��da implementuje BlackmannTukey metodu spektr�ln�ho odhadu
 * Vyu��v� FFT od apache.org a konvoluci od Michala Nykla
 * */
public class BlackmannTukeyMethod{
	
	/* Pom�r d�lky okna vzhledem k d�lce vstupn�ho sign�lu */
	private double windowScale = 0.1;
	
	/* instance pou��van�ho okna */
	private Window windowInstance = null;
	
	/* navzorkovan� okno, mu�e se hodit */
	private double[] windowSequence = null;
	
	/* Indikuje zda se m� pou��t periodick� konvoluce �i nikoliv */
	private boolean periodicConvolution = false;
	
	/* dr�� informaci o tom jesli ud�lat jenom jednostrann� odhad
	 * tedy na p�lce intervalu frekvenc� */
	private boolean oneSided = false;
	
	/**
	 * @return pom�r okna vzhledem k d�lce sig�lu.
	 */
	public double getWindowScale()
	{
		return windowScale;
	}

	/**
	 * @param windowScale the windowScale to set
	 */
	public void setWindowScale(double windowScale)
	{
		if (this.windowScale >= 0)
			this.windowScale = windowScale;
	}
	
	/**
	 * @return instanci okna, kter� se pou��v� pro vyhlazen� autokorela�n�ho odhadu 
	 */
	public Window getWindowInstance()
	{
		return windowInstance;
	}

	/**
	 * @param windowInstance the windowInstance to set
	 */
	public void setWindowInstance(Window windowInstance)
	{
		this.windowInstance = windowInstance;
		this.windowSequence = null;
	}

	/**
	 * @return the oneSided
	 */
	public boolean isOneSided()
	{
		return oneSided;
	}

	/**
	 * @param oneSided the oneSided to set
	 */
	public void setOneSided(boolean oneSided)
	{
		this.oneSided = oneSided;
	}

	/**
	 * Implementace blackmann-tukeyho metody
	 * 1) nastavit sign�l nulami.
	 * 2) ud�lat autokorelaci pomoc� FFT
	 * 3) vytvo�en� autokorela�n�ho odhadu (ifft z toho co m�me)
	 * 4) konvoluce autokorela�n�ho odhadu a okna
	 * */
	public double[] getBlackmannTukeyEst(double[] inSignal)
	{
		//1) nastavit sgn�l na d�lku 2N p��padn� nejbli��� vy��� mocninu dvou
		int N = Power2Utils.newMajorNumberOfPowerBase2(2*inSignal.length);
		double[] signal = new double[N];
		System.arraycopy(inSignal, 0, signal, 0, inSignal.length);
		for (int i = inSignal.length; i < N; i += 1)
			signal[i] = 0;
		
		FastFourierTransformer fftMethod = new FastFourierTransformer();
		Complex[] fftSignal;
		
		fftSignal = fftMethod.transform(signal);
		
		//do sign�lu spo�teme ty hodnoty autokorelace
		double real, imag;
		for (int i = 0; i < N; i += 1)
		{
			real = fftSignal[i].getReal();
			imag = fftSignal[i].getImaginary();
			signal[i] = real*real - imag*imag; 
		}
		
		Complex[] ifftSignal;
		
		ifftSignal = fftMethod.inversetransform(signal);
		
		//p�epis do doubl� a kontrola re�lnosti
		//prost� imagin�rn� zahod�me.
		for (int i = 0; i < N; i += 1)
			signal[i] = ifftSignal[i].getReal(); 
		
		//kontrola p��tomnosti okna
		if (this.windowInstance == null)
		{
			//pak nastav�me t�eba hammingovo okno
			this.windowInstance = new HammingWindow();
			this.windowSequence = null;
		}
		
		//p��padn� vzorkov�n� okna
		if (this.windowSequence == null || 
			this.windowSequence.length != ((int) (inSignal.length * this.getWindowScale())))
				this.windowSequence = this.windowInstance.getWinSequence((int)(inSignal.length * this.getWindowScale()));
		
		//4) konvoluce
		if (this.periodicConvolution)
			Convolution.periodicConvolution(signal, this.windowSequence);
		else
			Convolution.convolution(signal, this.windowSequence);
	
		fftSignal = fftMethod.transform(signal);
		
		//je�t� v�po�et �ehosi jako periodogramu
		if (this.oneSided)
		{
			N /= 2;
			N += 1;
		}
		
		signal = new double[N];
		for (int i = 0; i < N; i += 1)
		{
			real = fftSignal[i].getReal();
			imag = fftSignal[i].getImaginary();
			signal[i] = real * real + imag * imag;
		}
		
		if (this.oneSided)
			for (int i = 1; i < N - 1; i += 1) signal[i] *= 2;
		
		return signal;
	}

	/**
	 * @return the periodicConvolution
	 */
	public boolean isPeriodicConvolution()
	{
		return periodicConvolution;
	}

	/**
	 * @param periodicConvolution the periodicConvolution to set
	 */
	public void setPeriodicConvolution(boolean periodicConvolution)
	{
		this.periodicConvolution = periodicConvolution;
	}
}
