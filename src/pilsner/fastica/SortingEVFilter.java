package pilsner.fastica;

import pilsner.fastica.math.*;

import java.util.Collections;
import java.util.LinkedList;

/**
 * This <code>SortingEVFilter</code> does not really filter some eigenvalues,
 * but sorts them and their related eigenvectors.
 * 
 * @author Michael Lambertz
 */

public class SortingEVFilter implements EigenValueFilter {

	private class Pair implements Comparable<Pair> {
		public double value;
		public int index;

		public int compareTo(Pair pair) {
			return (new Double(this.value).compareTo(new Double(
					((Pair) pair).value)));
		}

	}

	private boolean descending;
	private boolean absolute;
	private double[] eigenValues;
	private double[][] eigenVectors;

	public SortingEVFilter(boolean descending, boolean absolute) {
		this.descending = descending;
		this.absolute = absolute;
	}

	public void passEigenValues(double[] eigenValues, double[][] eigenVectors) {
		int n = eigenValues.length;
		// generate value list with identity permutation
		LinkedList<Pair> list = new LinkedList<Pair>();
		for (int j = 0; j < n; ++j) {
			Pair pair = new Pair();
			if (this.absolute)
				pair.value = Math.abs(eigenValues[j]);
			else
				pair.value = eigenValues[j];
			pair.index = j;
			list.add(pair);
		}
		Collections.sort(list);
		// reverse the permutation if descending
		if (this.descending)
			Collections.reverse(list);
		// copy eigenvectors according to the permutation
		int m = Matrix.getNumOfRows(eigenVectors);
		this.eigenValues = Vector.newVector(n);
		this.eigenVectors = Matrix.newMatrix(m, n);
		for (int j = 0; j < n; ++j) {
			int k = list.get(j).index;
			this.eigenValues[j] = eigenValues[k];
			for (int i = 0; i < m; ++i)
				this.eigenVectors[i][j] = eigenVectors[i][k];
		}
	}
	/**
	 * This method return eigen values
	 * return double[] - eigenValues
	 */
	public double[] getEigenValues() {
		return (eigenValues);
	}
	/**
	 * This method return eigen vectors
	 * return double[] - eigenVectors
	 */
	public double[][] getEigenVectors() {
		return (eigenVectors);
	}

}
