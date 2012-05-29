package pilsner.spectra;

import org.apache.commons.math.MathException;
import org.apache.commons.math.complex.Complex;
import org.apache.commons.math.transform.FastFourierTransformer;

import pilsner.signalwindows.HammingWindow;
import pilsner.signalwindows.Window;
import pilsner.utils.math.Convolution;
import pilsner.utils.math.Power2Utils;

/**
 * Tøída implementuje BlackmannTukey metodu spektrálního odhadu
 * Využívá FFT od apache.org a konvoluci od Michala Nykla
 * */
public class BlackmannTukeyMethod{
	
	/* Pomìr délky okna vzhledem k délce vstupního signálu */
	private double windowScale = 0.1;
	
	/* instance používaného okna */
	private Window windowInstance = null;
	
	/* navzorkované okno, muže se hodit */
	private double[] windowSequence = null;
	
	/* Indikuje zda se má použít periodická konvoluce èi nikoliv */
	private boolean periodicConvolution = false;
	
	/* drží informaci o tom jesli udìlat jenom jednostranný odhad
	 * tedy na pùlce intervalu frekvencí */
	private boolean oneSided = false;
	
	/**
	 * @return pomìr okna vzhledem k délce sigálu.
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
	 * @return instanci okna, které se používá pro vyhlazení autokorelaèního odhadu 
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
	 * 1) nastavit signál nulami.
	 * 2) udìlat autokorelaci pomocí FFT
	 * 3) vytvoøení autokorelaèního odhadu (ifft z toho co máme)
	 * 4) konvoluce autokorelaèního odhadu a okna
	 * */
	public double[] getBlackmannTukeyEst(double[] inSignal)
	{
		//1) nastavit sgnál na dýlku 2N pøípadnì nejbližší vyšší mocninu dvou
		int N = Power2Utils.newMajorNumberOfPowerBase2(2*inSignal.length);
		double[] signal = new double[N];
		System.arraycopy(inSignal, 0, signal, 0, inSignal.length);
		for (int i = inSignal.length; i < N; i += 1)
			signal[i] = 0;
		
		FastFourierTransformer fftMethod = new FastFourierTransformer();
		Complex[] fftSignal;
		
		fftSignal = fftMethod.transform(signal);
		
		//do signálu spoèteme ty hodnoty autokorelace
		double real, imag;
		for (int i = 0; i < N; i += 1)
		{
			real = fftSignal[i].getReal();
			imag = fftSignal[i].getImaginary();
			signal[i] = real*real - imag*imag; 
		}
		
		Complex[] ifftSignal;
		
		ifftSignal = fftMethod.inversetransform(signal);
		
		//pøepis do doublù a kontrola reálnosti
		//prostì imaginární zahodíme.
		for (int i = 0; i < N; i += 1)
			signal[i] = ifftSignal[i].getReal(); 
		
		//kontrola pøítomnosti okna
		if (this.windowInstance == null)
		{
			//pak nastavíme tøeba hammingovo okno
			this.windowInstance = new HammingWindow();
			this.windowSequence = null;
		}
		
		//pøípadné vzorkování okna
		if (this.windowSequence == null || 
			this.windowSequence.length != ((int) (inSignal.length * this.getWindowScale())))
				this.windowSequence = this.windowInstance.getWinSequence((int)(inSignal.length * this.getWindowScale()));
		
		//4) konvoluce
		if (this.periodicConvolution)
			Convolution.periodicConvolution(signal, this.windowSequence);
		else
			Convolution.convolution(signal, this.windowSequence);
	
		fftSignal = fftMethod.transform(signal);
		
		//ještì výpoèet èehosi jako periodogramu
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
