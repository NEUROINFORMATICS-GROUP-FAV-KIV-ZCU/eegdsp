package pilsner.wt.cwt.wavelets;

/**
 * T��da waveletu MexicanHat
 */
public class MexicanHat extends WaveletCWT
{
	//n�zev waveletu
	private final static String NAME = "Mexican_Hat";
	//konstanta pro normov�n� 2/(sqrt(3) * pi^(1/4))
	//private final double NORM = 0.8673250705840776;
	
	/**
	 * Konstruktor waveletu
	 */
	public MexicanHat()
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
		
		return (1.0/Math.sqrt(a))* ((1-tPow2)*Math.exp(-tPow2/2)); 
	}
	
	/**
	 * Imagin�rn� ��st mate�sk�ho waveletu.
	 */
	@Override
	public double imCoef(double t, double a)
	{
		return 0;
	}
}
