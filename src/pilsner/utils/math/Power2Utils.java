package pilsner.utils.math;

import java.util.Arrays;

/**
 * Tøída pro matematické operace.
 * 
 * @author Petr Soukal
 */
public class Power2Utils {
    //konstanty

    public final static int CONST_2 = 2;
    public final static int ZERO = 0;

    /**
     * Metoda vypoèítává logaritmus o základu 2 z vloeného èísla.
     *
     * @param x - èíslo ze kterého se poèítá logaritmus o základu 2.
     * @return log2 z x.
     */
    public static double log2(double x) {
        return Math.log(x) / Math.log(CONST_2);
    }

    /**
	 * Pokud neni vloené èíslo mocninou základu 2, tak vrátí první vìtší èíslo,
	 * které je mocninou základu 2.
	 * 
	 * @param x - èíslo, u kterého se zjišuje zda je základu 2.
	 * @return èíslo x nebo první vìtší èíslo, které je mocninou základu 2.
	 */
	public static int newMajorNumberOfPowerBase2(int x){
		double number = log2(x);
		int temp = (int)number;
		
		if(number%temp == 0)		
			return x;
		else
		{
			temp += 1;
			int newNumber = (int) Math.pow(CONST_2, temp);
			return newNumber;
		}
	}
	
	/**
	 * Pokud neni vloené èíslo mocninou základu 2, tak vrátí první menší èíslo,
	 * které je mocninou základu 2.
	 * 
	 * @param x - èíslo, u kterého se zjišuje zda je základu 2.
	 * @return èíslo x nebo první menší èíslo, které je mocninou základu 2.
	 */
	public static int newMinorNumberOfPowerBase2(int x){
		double number = (int)log2(x);
		
		return (int) Math.pow(CONST_2, number);
	}

	/**
	 * Metoda prodluuje vstupní signál na délku (2^n) pokud takovou délku nemá
	 * a nové místo se vyplní nulami.
	 */
	public static double[] signalPowerBase2(double[] inputSignal)
	{
		int newSignalLength = Power2Utils.newMajorNumberOfPowerBase2(inputSignal.length);
		
		double[] signal = new double[newSignalLength];
		
		if(newSignalLength != inputSignal.length)
		{			
			
			signal = Arrays.copyOf(inputSignal, newSignalLength);
			Arrays.fill(signal, inputSignal.length, signal.length, Power2Utils.ZERO);
		}
		else
			signal = inputSignal.clone();
		
		return signal;
	}
	
    public static double[] createPower2LengthArray(double[] inputSignal) {
        double[] signal;

        int newSignalLength = Power2Utils.newMajorNumberOfPowerBase2(inputSignal.length);

        if (newSignalLength != inputSignal.length) {
            signal = Arrays.copyOf(inputSignal, newSignalLength);
            Arrays.fill(signal, inputSignal.length, signal.length, Power2Utils.ZERO);
        } else {
            signal = inputSignal.clone();
        }

        return signal;
    }
    
    public static double euclideanNorm(double[] array)
    {
    	double retval = 0;
    	
    	for (int i = 0; i < array.length; i++)
    		retval += Math.pow(array[i], 2);
    	
    	return Math.sqrt(retval);
    }
}
