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
 * Statická tøída poskytující funkce, které poèítají
 * Periodogram, Bartletovu metodu pro odhad spektra
 * pro reaálný signál.
 * 
 * 
 * @author MArtin Šimek 2008
 * */
public class SpectraEstimation
{
	private static FastFourierTransformer mathLibrary = null;
	
	/**
	 * Každý prvek pole <i>signal</i> pøenásobí koeficientem <i>factor</i>.
	 * @param signal je pole doublù.
	 * @param factor je double kterým se vynásobí každý prvek pole <i>signal</i>.  
	 * */
	public static void multiplyBy(double[] signal, double factor)
	{
		if (signal == null) return;
		for (int i = 0; i < signal.length; i += 1) signal[i] *= factor;
	}
	
	/**
	 * Každý prvek pole <i>signal</i> pøenásobí pøíslušným prvkem <i>weightArray</i>.
	 * @param signal je pole doublù.
	 * @param weightArray je pole doublù, kterým se po složkách vynásobí každý prvek pole <i>signal</i>.  
	 * */
	public static void multiplyBy(double[] signal, double[] weightArray)
	{
		if (signal == null || weightArray == null) return;

		int last = Math.min(signal.length, weightArray.length);
		for (int i = 0; i < last; i += 1) signal[i] *= weightArray[i];
	}
	
	/**
	 * Souèet prvního (<i>first</i>) a druhého (<i>second</i>) 
	 * pole uloží do cílového (<i>destination</i>) pole.
	 * Pokud alespoò jedno z polí bude null nebo pole nebudou mít
	 * shodnou délku funkce neprovede žádnou akci.
	 * 
	 *  @param destination cílové pole. Múže být totožné s dalšími parametry.
	 *  @param first první sèítanec, zároveò mùže být i cílem.
	 *  @param second druhý sèítanec, opìt mùže sloužit i jako cíl.
	 * */
	public static void addArray(double[] destination, double[] first, double[] second)
	{
		if (destination == null || first == null || second == null ||
			destination.length != second.length ||
			destination.length != first.length) return; //nekompatibilní pole, nelze sèítat

		/*
		 * Sèítání prvkù a ukládání do cíle.
		 * */
		for (int i = 0; i < destination.length; i += 1)
			destination[i] = first[i] + second[i];
	}
	
	/**
	 * Funkce která pøenásobí signál oknem na základì
	 * vyjmenovaného typu WINDOWS pøípadnì pøedá
	 * konkrétní funkci pøíslušný parametr z objektu
	 * windowSettings.
	 * 
	 * @param signal pole se vzorky signálu, 
	 * nekontroluje se, kontrolu si provádí každá funkce sama.
	 * @param window vyjmenovaný typ podle, kterého se rozhodne
	 * ktará fukce se použije pro vynásobení signálu oknem.
	 * @param windowSettings Nìkterá okna vyžadují parametr, který mìní
	 * jejich vlastnosti, protože se chceme vyvarovat problémùm v budoucnosti
	 * posíláme parametr jako objekt, ze kterého si pøíslušná èást rozhodování extrahuje
	 * co potøebuje. Vyžaduje-li okno parametr double pošleme double v obalové tøídì Double.
	 * Konkrétní parametry viz dokumentace jednotlivých oken.
	 */
	public static void multiplyByWindow(double[] signal, WINDOWS window, Object windowSettings)
	{
			//FIXME dodìlat
			//TODO dodìlat!!!
	}

	/**
	 * @return 
	 * 		instatnci matematické knihovny pro výpoèet
	 * 		rychlé fourierovy transformace a uloží jí do
	 * 		statické promìné, aby se pøi každém použítí
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
	 * pouze pro testovací úèely
	 */
	public static void main(String[] args)
	{
		//naètení vstupního signálu
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

			//bez kontroly a ochrany, je to jenom na testování
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
		
		//signál naèten
		
		BlackmannTukeyMethod estim = new BlackmannTukeyMethod();
		estim.setOneSided(true);
		estim.setWindowInstance(new BlackmanHarrisWindow());
		spectrum = estim.getBlackmannTukeyEst(samples);
		
		//uložíme jej do souboru
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
