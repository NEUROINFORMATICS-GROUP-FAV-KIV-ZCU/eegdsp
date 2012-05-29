package pilsner.utils.math;

/**
 * Diskretni konvoluce.
 * @author Michal Nykl
 */
public class Convolution {

	  /**
	   * Diskretni konvoluce dvou signalu
	   * @param sig1 Prvni signal konvoluce
	   * @param sig2 Druhy signal konvoluce
	   * @return double[] Vysledna konvoluce
	   */
	  public static double[] convolution(double[] sig1, double[] sig2)
	  {
		  double[] result = new double[sig1.length + sig2.length - 1];
		  // nulovani vysledneho pole
		  for (int i = 0; i < result.length; i++) result[i] = 0.0;
		  
		  int krok = 0;
		  for (double s1: sig1)
		  {
			  for (int j = 0; j < sig2.length; j++) result[j + krok] += s1 * sig2[j];
			  krok++;
		  }

		  return result;
	  }
	  
	  /**
	   * Diskretni periodicka konvoluce dvou signalu.
	   * @param sig1 Prvni signal konvoluce
	   * @param sig2 Druhy signal konvoluce
	   * @return double[] Vysledna konvoluce
	   */
	  public static double[] periodicConvolution(double[] sig1, double[] sig2)
	  {
		  double[] result = new double[sig1.length];
		  // nulovani vysledneho pole
		  for(int i = 0; i < result.length; i++) result[i] = 0.0;
		  
		  int krok = 0;
		  for(double s1: sig1)
		  {
			  for(int j=0; j<sig2.length; j++)
				  result[(j + krok) % sig1.length] += s1 * sig2[j];
			  krok++;
		  }

		  return result;
	  }
}
