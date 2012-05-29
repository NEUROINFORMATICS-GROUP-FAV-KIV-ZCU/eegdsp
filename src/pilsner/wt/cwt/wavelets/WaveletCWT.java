package pilsner.wt.cwt.wavelets;

/**
 * Abstraktn� t��da waveletu pro spojitou waveletovou transformaci.
 */
public abstract class WaveletCWT
{
	//n�zev waveletu
	private String name;

    /**
     *  Konstruktor WaveletCWT
     *
     *  @param name - n�zev waveletu.
    */
    public WaveletCWT(String name)
    {
    	this.name = name;
    }

    /**
	 * Re�ln� ��st mate�sk�ho waveletu.
	 */
    public abstract double reCoef(double t, double a);
    
    /**
	 * Imagin�rn� ��st mate�sk�ho waveletu.
	 */
    public abstract double imCoef(double t, double a);
    
    /**
     * @return n�zev waveletu.
    */
    public String getName()
    {
        return name;
    }
}
