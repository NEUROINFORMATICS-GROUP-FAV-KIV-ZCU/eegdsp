package pilsner.fastica;

/**
 * The <code>ProgressListener</code> can be used to observe the progress of
 * the FastICA algorithm.
 * 
 * @author Michael Lambertz
 */

public interface ProgressListener {

	public enum ComputationState {
		WHITENING, SYMMETRIC, DEFLATION, READY
	}

	public void progressMade(ComputationState state, int component,
			int iteration, int maxComps);

}
