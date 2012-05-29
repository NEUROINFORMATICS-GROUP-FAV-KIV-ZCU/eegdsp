package pilsner.wt.cwt.wavelets;

/**
 * T��da waveletu ComplexGaussian
 */
public class ComplexGaussian extends WaveletCWT
{
	//n�zev waveletu
	private final static String NAME = "Complex_Gaussian";
	
	/**
	 * Konstruktor waveletu
	 */
	public ComplexGaussian()
	{
		super(NAME);
	}
	
	/**
	 * Re�ln� ��st mate�sk�ho waveletu.
	 */
	@Override
	public double reCoef(double t, double a)
	{
		double tPow2 = t*t;
		
		return (1.0/Math.sqrt(a))*Math.exp(-tPow2)*(-Math.sin(t)-(2.0 * t * Math.cos(t))); 
	}
	
	/**
	 * Imagin�rn� ��st mate�sk�ho waveletu.
	 */
	@Override
	public double imCoef(double t, double a)
	{
		double tPow2 = t*t;
		
		return (1.0/Math.sqrt(a))*Math.exp(-tPow2)*(-Math.cos(t)+(2.0 * t * Math.sin(t))); 
	}
}
