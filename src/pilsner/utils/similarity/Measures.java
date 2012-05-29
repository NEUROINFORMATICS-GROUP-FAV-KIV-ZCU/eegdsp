package pilsner.utils.similarity;

import pilsner.utils.math.Power2Utils;


/**
 * 
 * @author Tomas Rondik
 * 
 */
public final class Measures
{
	public static final double minkovski(double[] signal1, double[] signal2, int root) throws IllegalArgumentException
	{
		Common.inputTest(signal1, signal2);
		
		double retval = 0;
		
		for (int i = 0; i < signal1.length; i++)
			retval += Math.pow(Math.abs(signal1[i] - signal2[i]), root);
		
		return Math.pow(retval, 1 / root);
	}
	
	public static enum Technique
	{
		EUCLIDIAN, CHEBYCHEV, BLOCK, HAMMING, COSINE, CORRELATION, CHI_SQUARE, JENSEN
	};
	
	public static final double measure(Technique technique, double[] signal1, double[] signal2) throws IllegalArgumentException
	{
		Common.inputTest(signal1, signal2);
		
		switch (technique)
		{
			case EUCLIDIAN:
				return euclidian(signal1, signal2);
			case CHEBYCHEV:
				return chebychev(signal1, signal2);
			// BLOCK is only another name for HAMMING
			case BLOCK:
			case HAMMING:
				return hamming(signal1, signal2);
			case COSINE:
				return cosine(signal1, signal2);
			case CORRELATION:
				return correlation(signal1, signal2);
			case CHI_SQUARE:
				return chiSquare(signal1, signal2);
			case JENSEN:
				return jensen(signal1, signal2);
			default:
				throw new IllegalArgumentException("Unknown technique"); //This case should never happen
		}
	}
	
	private static final double euclidian(double[] signal1, double[] signal2)
	{
		double retval = 0;
		
		for (int i = 0; i < signal1.length; i++)
			retval += Math.pow(signal1[i] - signal2[i], 2);
		
		return Math.sqrt(retval);
	}
	
	private static final double chebychev(double[] signal1, double[] signal2)
	{
		double max = -1;
		double absDifference;
		
		
		for (int i = 0; i < signal1.length; i++)
		{
			absDifference = Math.abs(signal1[i] - signal2[i]);
			
			if (absDifference > max)
				max = absDifference;
		}
		
		return max;
	}
	
	private static final double hamming(double[] signal1, double[] signal2)
	{
		double retval = 0;
		
		for (int i = 0; i < signal1.length; i++)
			retval += Math.abs(signal1[i] - signal2[i]);
		
		return retval;
	}
	
	private static final double cosine(double[] signal1, double[] signal2)
	{
		double mult = 0;
		double xPow = 0;
		double yPow = 0;
		
		for (int i = 0; i < signal1.length; i++)
		{
			mult += signal1[i] * signal2[i];
			xPow += Math.pow(signal1[i], 2);
			yPow += Math.pow(signal2[i], 2);
		}
		
		return mult / Math.sqrt(mult / (xPow * yPow));
	}
	
	private static final double correlation(double[] signal1, double[] signal2)
	{
		double xAvg = pilsner.utils.Arrays.average(signal1);
		double yAvg = pilsner.utils.Arrays.average(signal2);
		
		double mult = 0;
		double xPow = 0;
		double yPow = 0;
		double xDiff;
		double yDiff;
		
		for (int i = 0; i < signal1.length; i++)
		{
			xDiff = signal1[i] - xAvg;
			yDiff = signal2[i] - yAvg;
			
			mult += xDiff * yDiff;
			xPow += Math.pow(xDiff, 2);
			yPow += Math.pow(yDiff, 2);
		}
		
		return mult / (Math.sqrt(xPow) * Math.sqrt(yPow));
	}
	
	private static final double chiSquare(double[] signal1, double[] signal2)
	{
		double xAvg = pilsner.utils.Arrays.average(signal1);
		double yAvg = pilsner.utils.Arrays.average(signal2);
		
		double xPart = 0;
		double yPart = 0;
		
		for (int i = 0; i < signal1.length; i++)
		{
			xPart += Math.pow(signal1[i] - xAvg, 2) / xAvg;
			yPart += Math.pow(signal2[i] - yAvg, 2) / yAvg;
		}
		
		return Math.sqrt(xPart + yPart);
	}
	
	private static final double jensen(double[] signal1, double[] signal2)
	{
		double xSum = pilsner.utils.Arrays.sum(signal1);
		double ySum = pilsner.utils.Arrays.sum(signal2);
		
		double xRatio;
		double yRatio;
		
		double retval = 0;
		
		for (int i = 0; i < signal1.length; i++)
		{
			xRatio = signal1[i] / xSum;
			yRatio = signal2[i] / ySum;
	
			retval += (xRatio * Power2Utils.log2(xRatio)) 
					+ (yRatio * Power2Utils.log2(yRatio))
					- ((xRatio + yRatio) * Power2Utils.log2((xRatio + yRatio) / 2d));
		}
		
		return retval / 2d;
	}
}
