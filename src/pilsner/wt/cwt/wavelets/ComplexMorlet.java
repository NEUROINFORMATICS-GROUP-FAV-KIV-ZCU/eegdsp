package pilsner.wt.cwt.wavelets;

/**
 * T��da waveletu ComplexMorlet
 */
public class ComplexMorlet extends WaveletCWT
{
	//n�zev waveletu
	private final static String NAME = "Complex_Morlet";
	//konstanta parametru p�smov� ���ky
	private final double FB;
	//konstanta st�edn� frekvence waveletu
	private final double FC;
	//konstanta pro normov�n�
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
	 * Re�ln� ��st mate�sk�ho waveletu.
	 */
	@Override
	public double reCoef(double t, double a)
	{
		double tPow2 = t*t;
		
		return NORM * Math.cos(2.0 * Math.PI * FC * t) * Math.exp(-tPow2/FB); 
	}
	
	/**
	 * Imagin�rn� ��st mate�sk�ho waveletu.
	 */
	@Override
	public double imCoef(double t, double a)
	{
		double tPow2 = t*t;
		
		return NORM * Math.sin(2.0 * Math.PI * FC * t) * Math.exp(-tPow2/FB); 
	}
}
