package pilsner.fastica;

import java.io.File;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

import pilsner.fastica.math.EigenValueDecompositionSymm;
import pilsner.fastica.math.Matrix;
import pilsner.fastica.math.Vector;
import pilsner.utils.audio.AudioBuffer;

/**
 * The <code>FastICA<code> class contains the main
 * FastICA algorithm.
 * @author Michael Lambertz
 * @author Vaclav Souhrada (v.souhrada@gmail.com)
 */
public class FastICA {
	// asssume the data is a sequence of column vectors
	
	private double[][] inVectors;
	private double[][] vectorsZeroMean;

	private double[][] whiteningMatrix;
	private double[][] dewhiteningMatrix;
	private double[][] whitenedVectors;

	private double[][] weightMatrix;

	private double[][] mixingMatrix;
	private double[][] separatingMatrix;
	private double[][] icVectors;

	/**
	 * This constructor calls the FastICA algorithm. Note that the constructor
	 * expects the data, which should be analysed, to be an array of signals
	 * from different sources. These signal vectors must have equal length.
	 * 
	 * @param inVectors the set of signals to analyse
	 * @param numICs the number of independent components
	 * @throws FastICAException
	 *             if a computation error occurs
	 */
	public FastICA(double[][] inVectors, int numICs) throws FastICAException {
		algorithm(inVectors, new FastICAConfig(numICs), new TanhCFunction(1.0),
				new BelowEVFilter(1.0e-12, false), new ProgressListener() {
					public void progressMade(ComputationState state,
							int component, int iteration, int maxComps) {
					}
				});
	}
	/**
	 * This constructor calls the FastICA algorithm. Note that the constructor
	 * expects the data, which should be analysed, to be an array of signals
	 * from different sources. These signal vectors must have equal length.
	 * 
	 * @param inVectors the set of signals to analyse
	 * @param config the FastICA configuration
	 * @param conFunction the contrast function
	 * @param evFilter the eigenvalue filter
	 * @param listener the progress listener
	 * @throws FastICAException if a computation error occurs
	 */
	public FastICA(double[][] inVectors, FastICAConfig config,
			ContrastFunction conFunction, EigenValueFilter evFilter,
			ProgressListener listener) throws FastICAException {
		algorithm(inVectors, config, conFunction, evFilter, listener);
	}
	/**
	 * This method is the actual FastICA algorithm.
	 * 
	 * @param inVectors the set of signals to analyse
	 * @param config the FastICA configuration
	 * @param conFunction the contrast function
	 * @param evFilter the eigenvalue filter
	 * @param listener the progress listener
	 * @throws FastICAException if a computation error occurs
	 */
	private synchronized void algorithm(double[][] inVectors,
			FastICAConfig config, ContrastFunction conFunction,
			EigenValueFilter evFilter, ProgressListener listener)
			throws FastICAException {

		listener.progressMade(ProgressListener.ComputationState.WHITENING, 0, 0, config.getNumICs());
		this.inVectors = inVectors;
		this.icVectors = null;
		PCA pca = new PCA(inVectors);          // provide PCA algorithm
		//meanValues = pca.getMeanValues();
		vectorsZeroMean = pca.getVectorsZeroMean();
		double[] eigenValues = pca.getEigenValues();      // eigen values after applicated PCA
		double[][] eigenVectors = pca.getEigenVectors(); // vector values after applicated PCA
		evFilter.passEigenValues(eigenValues, eigenVectors);
		eigenValues = evFilter.getEigenValues();
		if ((eigenValues == null) || (eigenValues.length == 0)) {
			mixingMatrix = null;
			separatingMatrix = null;
			icVectors = null;
			throw (new FastICAException(FastICAException.Reason.NO_MORE_EIGENVALUES));
		}
		eigenVectors = evFilter.getEigenVectors();
		whiteningMatrix = Matrix.mult(Matrix.diag(invVector(sqrtVector(eigenValues))), 
													Matrix.transpose(eigenVectors));
		dewhiteningMatrix = Matrix.mult(eigenVectors, Matrix.diag(sqrtVector(eigenValues)));
		// the whitened vectors' correlation matrix equals unit matrix
		// which is demanded to perform the FastICA algorithm
		whitenedVectors = Matrix.mult(whiteningMatrix, vectorsZeroMean);

		// initialize the weight matrix and some other variables
		int m = Matrix.getNumOfRows(whitenedVectors);
		int n = Matrix.getNumOfColumns(whitenedVectors);
		int numICs = config.getNumICs();
		if (m < numICs)
			numICs = m;
		if (config.getInitialMixingMatrix() == null) {
			weightMatrix = Matrix.random(numICs, m);
		} else {
			if ((Matrix.getNumOfColumns(config.getInitialMixingMatrix()) == numICs)
					&& (Matrix.getNumOfRows(config.getInitialMixingMatrix()) == Matrix
							.getNumOfRows(vectorsZeroMean))) {
				weightMatrix = Matrix.transpose(Matrix.mult(whiteningMatrix,
						config.getInitialMixingMatrix()));
			} else
				weightMatrix = Matrix.random(numICs, m);
		}
		weightMatrix = Matrix.mult(powerSymmMatrix(Matrix.square(weightMatrix),
				-0.5), weightMatrix);
		int iter;
		boolean ready;
		int maxIter = config.getMaxIterations();

		// TODO: NaN problem, exception
		// perform FastICA algorithm
		switch (config.getApproach()) {
		case SYMMETRIC:
			double[][] weightMatrixOld;
			iter = 0;
			ready = false;
			while ((iter < maxIter) && (!ready)) {
				listener.progressMade(
						ProgressListener.ComputationState.SYMMETRIC, 0, iter,
						numICs);

				weightMatrixOld = Matrix.clone(weightMatrix);

				for (int i = 0; i < numICs; ++i) {
					double[] v1 = Matrix.getVecOfRow(weightMatrix, i);
					double[] v2;
					double beta = 0.0;
					double[] exgf = Vector.newVector(m, 0.0);
					double egfd = 0.0;
					for (int j = 0; j < n; ++j) {
						double[] actualXVector = Matrix.getVecOfCol(
								whitenedVectors, j);
						double weightedX = Vector.dot(v1, actualXVector);
						double gff = conFunction.function(weightedX);
						double gfd = conFunction.derivative(weightedX);
						beta += weightedX * gff;
						egfd += gfd;
						exgf = Vector.add(exgf, Vector
								.scale(gff, actualXVector));
					}
					beta /= n;
					egfd /= n;
					exgf = Vector.scale(1.0 / n, exgf);
					v2 = Vector.sub(v1, Vector.scale(1.0 / (egfd - beta),
							Vector.sub(exgf, Vector.scale(beta, v1))));
					// write new vector to the matrix
					for (int j = 0; j < m; ++j)
						weightMatrix[i][j] = v2[j];
				}

				// symmetric decorrelation by orthonormalisation
				weightMatrix = Matrix.mult(powerSymmMatrix(Matrix
						.square(weightMatrix), -0.5), weightMatrix);

				// test if good approximation
				if (deltaMatrices(weightMatrix, weightMatrixOld) < config
						.getEpsilon())
					ready = true;

				iter++;
			}
			break;
		case DEFLATION:
			for (int i = 0; i < numICs; ++i) {
				double[] v2 = Matrix.getVecOfRow(weightMatrix, i);
				iter = 0;
				ready = false;
				while ((iter < maxIter) && (!ready)) {
					listener.progressMade(
							ProgressListener.ComputationState.DEFLATION, i,
							iter, numICs);

					double[] v1 = Vector.clone(v2);
					double beta = 0.0;
					double[] exgf = Vector.newVector(m, 0.0);
					double egfd = 0.0;
					for (int j = 0; j < n; ++j) {
						double[] actualXVector = Matrix.getVecOfCol(
								whitenedVectors, j);
						double weightedX = Vector.dot(v1, actualXVector);
						double gff = conFunction.function(weightedX);
						double gfd = conFunction.derivative(weightedX);
						beta += weightedX * gff;
						egfd += gfd;
						exgf = Vector.add(exgf, Vector
								.scale(gff, actualXVector));
					}
					beta /= n;
					egfd /= n;
					exgf = Vector.scale(1.0 / n, exgf);
					v2 = Vector.sub(v1, Vector.scale(1.0 / (egfd - beta),
							Vector.sub(exgf, Vector.scale(beta, v1))));

					// orthogonalisation of the vector
					for (int j = 0; j < i; ++j)
						v2 = Vector.sub(v2, Vector.scale(Vector.dot(v2,
								weightMatrix[j]), weightMatrix[j]));
					// orthonormalisation of the vector
					v2 = Vector.scale(1 / Math.sqrt(Vector.dot(v2, v2)), v2);

					// write new vector to the matrix
					for (int j = 0; j < m; ++j)
						weightMatrix[i][j] = v2[j];

					// test if good approximation
					if (deltaVectors(v2, v1) < config.getEpsilon())
						ready = true;

					iter++;
				}
			}
			break;
		}

		mixingMatrix = Matrix.mult(dewhiteningMatrix, Matrix.transpose(weightMatrix));
		separatingMatrix = Matrix.mult(weightMatrix, whiteningMatrix);

		listener.progressMade(ProgressListener.ComputationState.READY, numICs, maxIter, numICs);
	}

	/**
	 * Calculates a difference measure of two matrices relative to their size.
	 * 
	 * @param mat1 the first matrix
	 * @param mat2 the second matrix
	 * @return the difference measure
	 */
	private static double deltaMatrices(double[][] mat1, double[][] mat2) {
		double[][] test = Matrix.sub(mat1, mat2);
		double delta = 0.0;
		int m = Matrix.getNumOfRows(mat1);
		int n = Matrix.getNumOfColumns(mat1);
		for (int i = 0; i < m; ++i)
			for (int j = 0; j < n; ++j)
				delta += Math.abs(test[i][j]);
		return (delta / (m * n));
	}

	/**
	 * Calculates a difference measure of two vectors relative to their size.
	 * 
	 * @param vec1 the first vector
	 * @param vec2 the second vector
	 * @return the difference measure
	 */
	private static double deltaVectors(double[] vec1, double[] vec2) {
		double[] test = Vector.sub(vec1, vec2);
		double delta = 0.0;
		int m = vec1.length;
		for (int i = 0; i < m; ++i)
			delta += Math.abs(test[i]);
		return (delta / m);
	}

	/**
	 * Calculates the power of a symmetric matrix.
	 * 
	 * @param inMatrix the symmetric matrix
	 * @param power the power
	 * @return the resulting matrix
	 */
	private static double[][] powerSymmMatrix(double[][] inMatrix, double power) {
		EigenValueDecompositionSymm eigenDeco = new EigenValueDecompositionSymm(
				inMatrix);
		int m = Matrix.getNumOfRows(inMatrix);
		double[][] eigenVectors = eigenDeco.getEigenVectors();
		double[] eigenValues = eigenDeco.getEigenValues();
		for (int i = 0; i < m; ++i)
			eigenValues[i] = Math.pow(eigenValues[i], power);
		return (Matrix.mult(
				Matrix.mult(eigenVectors, Matrix.diag(eigenValues)), Matrix
						.transpose(eigenVectors)));
	}

	/**
	 * Inverts every element of the vector.
	 * 
	 * @param inVector the vector
	 * @return the resulting vector
	 */
	private static double[] invVector(double[] inVector) {
		int m = inVector.length;
		double[] outVector = new double[m];
		for (int i = 0; i < m; ++i)
			outVector[i] = 1 / inVector[i];
		return (outVector);
	}

	/**
	 * Square roots every element of the vector.
	 * 
	 * @param inVector the vector
	 * @return the resulting vector
	 */
	private static double[] sqrtVector(double[] inVector) {
		int m = inVector.length;
		double[] outVector = new double[m];
		for (int i = 0; i < m; ++i)
			outVector[i] = Math.sqrt(inVector[i]);
		return (outVector);
	}

	/**
	 * Returns the resulting set of vectors containing the independent
	 * components.
	 * 
	 * @return the resulting set of vectors
	 */
	public synchronized double[][] getICVectors() {
		if (icVectors == null) {
			// calculate independent component vectors and readd the mean
			icVectors = Matrix.mult(separatingMatrix, inVectors);
		}
		return (icVectors);
	}

	/**
	 * Returns the assumed mixing matrix.
	 * 
	 * @return the assumed mixing matrix
	 */
	public double[][] getMixingMatrix() {
		return (mixingMatrix);
	}

	/**
	 * Returns the assumed seperating matrix.
	 * 
	 * @return the assumed seperating matrix
	 */
	public double[][] getSeparatingMatrix() {
		return (separatingMatrix);
	}	
	/**
	 * This <code>main</code> Method has been written for testing purposes.<br>
	 * Usage:<br>
	 * java org.fastica.FastICA [input wave] [number of independent components]
	 * [output wave]
	 * 
	 * @param args
	 *            the arguments as described above
	 */
	public static void main(String[] args) {
		if (args.length != 3) {
			System.out.println("Usage:");
			System.out.println("java org.fastica.FastICA [input wave] [number of independent components] [output wave]");
			System.out.println();
			return;
		}
		try {
			// open a wave file and convert the signals
			AudioBuffer buffer1 = new AudioBuffer(new File(args[0]));
			// mix the signals
			double[][] mixingMatrix = Matrix.newMatrix(5, 2);
			mixingMatrix[0][0] = 0.5;
			mixingMatrix[0][1] = 0.5;
			mixingMatrix[1][0] = 0.3;
			mixingMatrix[1][1] = 0.7;
			mixingMatrix[2][0] = 0.6;
			mixingMatrix[2][1] = 0.2;
			mixingMatrix[3][0] = 0.2;
			mixingMatrix[3][1] = 0.6;
			mixingMatrix[4][0] = 0.3;
			mixingMatrix[4][1] = 0.5;
			double[][] mixedSignal = Matrix.mult(mixingMatrix, buffer1.getData());
			// join some filters into a standard filter
			CompositeEVFilter filter = new CompositeEVFilter();
			filter.add(new BelowEVFilter(1.0e-8, false));
			filter.add(new SortingEVFilter(true, true));
			// build a ICA configuration
			FastICAConfig config = new FastICAConfig(Integer.parseInt(args[1]),
					FastICAConfig.Approach.DEFLATION, 1.0, 1.0e-16, 1000, null);
			// build the progress listener
			ProgressListener listener = new ProgressListener() {
				public void progressMade(ComputationState state, int component,	int iteration, int maxComps) {
					System.out.print("\r" + Integer.toString(component) + " - "
							+ Integer.toString(iteration) + "     ");
				}
			};
			// perform the independent component analysis
			System.out.println("Performing ICA");
			FastICA fica = new FastICA(mixedSignal, config,	new Power3CFunction(), filter, listener);
			System.out.println();
			// write the resulting signals to a wave file
			AudioBuffer buffer2 = new AudioBuffer(fica.getICVectors(), buffer1.getSampleRate());
			AudioInputStream stream2 = buffer2.getStream();
			AudioSystem.write(stream2, AudioFileFormat.Type.WAVE, new File(args[2]));
		} catch (Exception exc) {
			exc.printStackTrace(System.err);
		}
	}

}
