package pilsner.utils.similarity;
/**
 * 
 * @author Tomas Rondik
 *
 */
final class Common
{
	static final String ILLEGAL_ARGUMENT_EXCEPTION_NULL = "Reference to one or both signals is null";

	static final String iLLEGAL_ARGUMENT_EXCEPTION_ZERO_LENGTH = "Length of one or both signals is 0";

	static final void inputTest(double[] signal1, double[] signal2)
			throws IllegalArgumentException
	{
		if (signal1 == null || signal2 == null)
			throw new IllegalArgumentException(ILLEGAL_ARGUMENT_EXCEPTION_NULL);
		else if (signal1.length == 0 || signal2.length == 0)
			throw new IllegalArgumentException(
					iLLEGAL_ARGUMENT_EXCEPTION_ZERO_LENGTH);
	}

	static final void inputTest(boolean[] signal1, boolean[] signal2)
			throws IllegalArgumentException
	{
		if (signal1 == null || signal2 == null)
			throw new IllegalArgumentException(ILLEGAL_ARGUMENT_EXCEPTION_NULL);
		else if (signal1.length == 0 || signal2.length == 0)
			throw new IllegalArgumentException(
					iLLEGAL_ARGUMENT_EXCEPTION_ZERO_LENGTH);
	}

	private Common(){}
}
