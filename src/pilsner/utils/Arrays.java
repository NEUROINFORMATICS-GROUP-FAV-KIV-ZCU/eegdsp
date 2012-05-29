/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pilsner.utils;

/**
 * Trida pro praci s poli.
 * 
 * @author Jiri Novotny
 */
public class Arrays {

	/**
	 * Prevedeni libovolneho signalu na String.
	 * Jednotlive hodnoty jsou oddeleny carkou.
         *
	 * @param sig
	 *          double pole s hodnotami signalu
	 * @return String ziskany z hodnot filtru
	 */
	public static String toString(double[] signal) {
		return toString(signal, ", ");
	}

        /**
	 * Prevedeni libovolneho signalu na String.
         * Hodnoty jsou oddeleny separatorem.
	 *
	 * @param sig
	 *          double pole s hodnotami signalu
	 * @param separator
	 *          Oddelovac jednotlivych hodnot
	 * @return String ziskany z hodnot filtru
	 */
	public static String toString(double[] signal, String separator) {
		String result = "";
                int i;
		for (i = 0; i < signal.length - 1; i++) {
			result += signal[i] + separator;
		}
                // posledni polozka bez separatoru
                result += signal[i];
		return result;
	}
	
	/**
	 * Computes sum of values in input array.
	 * @param array Input array
	 * @return Sum of values in input array.
	 */
	public static double sum(double[] array)
	{
		double sum = 0;
		
		for (int i = 0; i < array.length; i++)
		{
			sum += array[i];
		}
		
		return sum;
		
	}
	
	/**
	 * Computes arithmetical average of values in input array.
	 * @param array Input array
	 * @return Arithmetical average of values in input array.
	 */
	public static double average(double[] array)
	{	
		return sum(array) / ((double) array.length);
	}
}
