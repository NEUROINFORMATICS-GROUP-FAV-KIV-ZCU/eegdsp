package pilsner.utils.similarity;

import java.util.Arrays;
/**
 * 
 * @author Tomas Rondik
 *
 */
public final class BinaryMeasures
{
	public static enum Technique
	{
		JACCARD, OCHIAI, HAMANN, GOODMAN_KRUSKAL_LAMBDA, NOVEL
	};
	
	private static final int A_INDEX = 0;
	
	private static final int B_INDEX = 1;
	
	private static final int C_INDEX = 2;
	
	private static final int D_INDEX = 3;
	
	private static final int[] computeABCD(boolean[] signal1, boolean[] signal2)
	{
		int[] retval = new int[D_INDEX + 1];
		Arrays.fill(retval, 0);
		
		for (int i = 0; i < signal1.length; i++)
		{
			// positive matches
			if (signal1[i] && signal2[i])
				++retval[A_INDEX];
			// left mismatches
			else if (!signal1[i] && signal2[i])
				++retval[B_INDEX];
			// right mismatches
			else if (signal1[i] && !signal2[i])
				++retval[C_INDEX];
			// negative matches
			else
				++retval[D_INDEX];
		}
		
		return retval;
	}
	
	public static final double measure(Technique technique, boolean[] signal1, boolean[] signal2) 
			throws IllegalArgumentException
	{
		Common.inputTest(signal1, signal2);
		
		int[] abcd = computeABCD(signal1, signal2);
		
		switch (technique)
		{
			case JACCARD:
				return jaccard(abcd[A_INDEX], abcd[B_INDEX], abcd[C_INDEX], abcd[D_INDEX]);
			case OCHIAI:
				return ochiai(abcd[A_INDEX], abcd[B_INDEX], abcd[C_INDEX], abcd[D_INDEX]);
			case HAMANN:
				return hamann(abcd[A_INDEX], abcd[B_INDEX], abcd[C_INDEX], abcd[D_INDEX]);
			case GOODMAN_KRUSKAL_LAMBDA:
				return goodmanKurskalLambda(abcd[A_INDEX], abcd[B_INDEX], abcd[C_INDEX], abcd[D_INDEX]);
			default:
				throw new IllegalArgumentException("Unknown technique"); //This case should never happen
				
		}
	}
	
	private static final double jaccard(int a, int b, int c, int d)
	{
		return a / ((double)(a + b + c));
	}
	
	private static final double ochiai(int a, int b, int c, int d)
	{
		return Math.sqrt((a / ((double)(a+b))) * (a / ((double)(a+c)))); //LISP sucks!
	}
	
	private static final double hamann(int a, int b, int c, int d)
	{
		return ((a + d) - (b + c)) / ((double)(a + b + c + d));
	}
	
	private static final double goodmanKurskalLambda(int a, int b, int c, int d)
	{
		int t1 = Math.max(a, b) + Math.max(c, d) + Math.max(a, c) + Math.max(b, d);
		int t2 = Math.max(a + c, b + d) + Math.max(a + b, c + d);
		
		return (t1 - t2) / ((double)(2 * (a + b + c + d) - t2));
	}
	
	private BinaryMeasures(){}
}
