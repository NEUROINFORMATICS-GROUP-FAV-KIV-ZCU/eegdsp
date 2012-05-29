package pilsner.spectra;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

import org.apache.commons.math.transform.FastFourierTransformer;

import pilsner.signalwindows.BlackmanHarrisWindow;
import pilsner.signalwindows.TestWindow;
import pilsner.signalwindows.WindowFactory.WINDOWS;
import pilsner.utils.math.Power2Utils;

/**
 * Statick� t��da poskytuj�c� funkce, kter� po��taj�
 * Periodogram, Bartletovu metodu pro odhad spektra
 * pro rea�ln� sign�l.
 * 
 * 
 * @author MArtin �imek 2008
 * */
public class SpectraEstimation
{
	private static FastFourierTransformer mathLibrary = null;
	
	/**
	 * Ka�d� prvek pole <i>signal</i> p�en�sob� koeficientem <i>factor</i>.
	 * @param signal je pole doubl�.
	 * @param factor je double kter�m se vyn�sob� ka�d� prvek pole <i>signal</i>.  
	 * */
	public static void multiplyBy(double[] signal, double factor)
	{
		if (signal == null) return;
		for (int i = 0; i < signal.length; i += 1) signal[i] *= factor;
	}
	
	/**
	 * Ka�d� prvek pole <i>signal</i> p�en�sob� p��slu�n�m prvkem <i>weightArray</i>.
	 * @param signal je pole doubl�.
	 * @param weightArray je pole doubl�, kter�m se po slo�k�ch vyn�sob� ka�d� prvek pole <i>signal</i>.  
	 * */
	public static void multiplyBy(double[] signal, double[] weightArray)
	{
		if (signal == null || weightArray == null) return;

		int last = Math.min(signal.length, weightArray.length);
		for (int i = 0; i < last; i += 1) signal[i] *= weightArray[i];
	}
	
	/**
	 * Sou�et prvn�ho (<i>first</i>) a druh�ho (<i>second</i>) 
	 * pole ulo�� do c�lov�ho (<i>destination</i>) pole.
	 * Pokud alespo� jedno z pol� bude null nebo pole nebudou m�t
	 * shodnou d�lku funkce neprovede ��dnou akci.
	 * 
	 *  @param destination c�lov� pole. M��e b�t toto�n� s dal��mi parametry.
	 *  @param first prvn� s��tanec, z�rove� m��e b�t i c�lem.
	 *  @param second druh� s��tanec, op�t m��e slou�it i jako c�l.
	 * */
	public static void addArray(double[] destination, double[] first, double[] second)
	{
		if (destination == null || first == null || second == null ||
			destination.length != second.length ||
			destination.length != first.length) return; //nekompatibiln� pole, nelze s��tat

		/*
		 * S��t�n� prvk� a ukl�d�n� do c�le.
		 * */
		for (int i = 0; i < destination.length; i += 1)
			destination[i] = first[i] + second[i];
	}
	
	/**
	 * Funkce kter� p�en�sob� sign�l oknem na z�klad�
	 * vyjmenovan�ho typu WINDOWS p��padn� p�ed�
	 * konkr�tn� funkci p��slu�n� parametr z objektu
	 * windowSettings.
	 * 
	 * @param signal pole se vzorky sign�lu, 
	 * nekontroluje se, kontrolu si prov�d� ka�d� funkce sama.
	 * @param window vyjmenovan� typ podle, kter�ho se rozhodne
	 * ktar� fukce se pou�ije pro vyn�soben� sign�lu oknem.
	 * @param windowSettings N�kter� okna vy�aduj� parametr, kter� m�n�
	 * jejich vlastnosti, proto�e se chceme vyvarovat probl�m�m v budoucnosti
	 * pos�l�me parametr jako objekt, ze kter�ho si p��slu�n� ��st rozhodov�n� extrahuje
	 * co pot�ebuje. Vy�aduje-li okno parametr double po�leme double v obalov� t��d� Double.
	 * Konkr�tn� parametry viz dokumentace jednotliv�ch oken.
	 */
	public static void multiplyByWindow(double[] signal, WINDOWS window, Object windowSettings)
	{
			//FIXME dod�lat
			//TODO dod�lat!!!
	}

	/**
	 * @return 
	 * 		instatnci matematick� knihovny pro v�po�et
	 * 		rychl� fourierovy transformace a ulo�� j� do
	 * 		statick� prom�n�, aby se p�i ka�d�m pou��t�
	 * 		nemusela znova instancovat. 
	 * */
	@SuppressWarnings("unused")
	private static FastFourierTransformer getMathLibrary()
	{
		if (mathLibrary == null)
			mathLibrary = new FastFourierTransformer();
		return mathLibrary;
	}
	
	public static double signalEnergy(double[] signal)
	{
		double energy = 0;
		for (int i = 0; i < signal.length; i += 1)
			energy += signal[i] * signal[i];

		return energy;
	}

	/**
	 * pouze pro testovac� ��ely
	 */
	public static void main(String[] args)
	{
		//na�ten� vstupn�ho sign�lu
		String signalFileName = "res/tested_signal.dat";
		String spectralFileName = "res/tested_spectrum.dat";
		byte[] value = new byte[8];
		int length;
		double[] samples;
		double[] spectrum;
		try
		{
			FileInputStream fs = new FileInputStream(signalFileName);
			fs.read(value, 0, 4);
			length = TestWindow.littleEndianBytesToInt(value);
			samples = new double[Power2Utils.newMajorNumberOfPowerBase2(length)];
			Arrays.fill(samples, 0);

			//bez kontroly a ochrany, je to jenom na testov�n�
			for (int i = 0; i < samples.length; i += 1)
			{
				fs.read(value);
				samples[i] = TestWindow.littleEndianBytesToDouble(value);
			}
			fs.close();
		}
		catch (IOException ioe)
		{
			ioe.printStackTrace();
			return;
		}
		
		//sign�l na�ten
		
		BlackmannTukeyMethod estim = new BlackmannTukeyMethod();
		estim.setOneSided(true);
		estim.setWindowInstance(new BlackmanHarrisWindow());
		spectrum = estim.getBlackmannTukeyEst(samples);
		
		//ulo��me jej do souboru
		try
		{
			FileOutputStream fo = new FileOutputStream(spectralFileName);
			fo.write(TestWindow.intToLittleEndianBytes(spectrum.length));

			for (int i = 0; i < spectrum.length; i += 1){
				fo.write(TestWindow.doubleToLittleEndianBytes(spectrum[i]));
			}
			fo.close();
		}
		catch (IOException ioe)
		{
			ioe.printStackTrace();
			return;
		}
	}
}
