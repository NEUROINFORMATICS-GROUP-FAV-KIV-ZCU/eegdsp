package pilsner.wt.cwt.wavelets;

/**
 * Tøída waveletu MexicanHat
 */
public class MexicanHat extends WaveletCWT
{
	//název waveletu
	private final static String NAME = "Mexican_Hat";
	//konstanta pro normování 2/(sqrt(3) * pi^(1/4))
	//private final double NORM = 0.8673250705840776;
	
	/**
	 * Konstruktor waveletu
	 */
	public MexicanHat()
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
		
		return (1.0/Math.sqrt(a))* ((1-tPow2)*Math.exp(-tPow2/2)); 
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
