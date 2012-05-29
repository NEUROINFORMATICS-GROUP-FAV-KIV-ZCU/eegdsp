package pilsner.wt.cwt.wavelets;

/**
 * Tøída waveletu ComplexMorlet
 */
public class ComplexMorlet extends WaveletCWT
{
	//název waveletu
	private final static String NAME = "Complex_Morlet";
	//konstanta parametru pásmové šíøky
	private final double FB;
	//konstanta støední frekvence waveletu
	private final double FC;
	//konstanta pro normování
	private final double NORM;
	
	/**
	 * Konstruktor waveletu
	 */
	public ComplexMorlet(double fb, double fc)
	{
		super(NAME);
		this.FB = fb;
		this.FC = fc;
		this.NORM = 1.0 / Math.sqrt(Math.PI * fb);	
	}
	
	/**
	 * Reálná èást mateøského waveletu.
	 */
	@Override
	public double reCoef(double t, double a)
	{
		double tPow2 = t*t;
		
		return NORM * Math.cos(2.0 * Math.PI * FC * t) * Math.exp(-tPow2/FB); 
	}
	
	/**
	 * Imaginární èást mateøského waveletu.
	 */
	@Override
	public double imCoef(double t, double a)
	{
		double tPow2 = t*t;
		
		return NORM * Math.sin(2.0 * Math.PI * FC * t) * Math.exp(-tPow2/FB); 
	}
}
