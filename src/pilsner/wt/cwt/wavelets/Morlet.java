package pilsner.wt.cwt.wavelets;

/**
 * Tøída waveletu Morlet
 */
public class Morlet extends WaveletCWT
{
	//název waveletu
	private final static String NAME = "Morlet";
	
	/**
	 * Konstruktor waveletu
	 */
	public Morlet()
	{
		super(NAME);
	}
	
	/**
	 * Reálná èást mateøského waveletu.
	 */
	@Override
	public double reCoef(double t, double a)
	{
		double tPow2 = t*t;
		
		return (1.0/Math.sqrt(a))*Math.cos(5*t)*Math.exp(-tPow2/2); 
	}
	
	/**
	 * Imaginární èást mateøského waveletu.
	 */
	@Override
	public double imCoef(double t, double a)
	{
		return 0;
	}
}
