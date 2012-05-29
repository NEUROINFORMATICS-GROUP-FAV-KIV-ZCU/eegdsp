package pilsner.wt.cwt.wavelets;

/**
 * Abstraktní tøída waveletu pro spojitou waveletovou transformaci.
 */
public abstract class WaveletCWT
{
	//název waveletu
	private String name;

    /**
     *  Konstruktor WaveletCWT
     *
     *  @param name - název waveletu.
    */
    public WaveletCWT(String name)
    {
    	this.name = name;
    }

    /**
	 * Reálná èást mateøského waveletu.
	 */
    public abstract double reCoef(double t, double a);
    
    /**
	 * Imaginární èást mateøského waveletu.
	 */
    public abstract double imCoef(double t, double a);
    
    /**
     * @return název waveletu.
    */
    public String getName()
    {
        return name;
    }
}
