package pilsner.signalwindows;

/**
 * Hammingovo okno
 * @author Michal Nykl, 2008
 */
public class HammingWindow implements Window {
	public final double SIG_RATIO = 53;
	public final double BW_CONST = 6.64;
	
	public double getSignalRatio() {
		return SIG_RATIO;
	}
	
	public double getBandwidthConst() {
		return BW_CONST;
	}
	
	public double[] getWinSequence(int orderRate) {
		orderRate = orderRate-1;
		double halfOrder = ((double) orderRate) / 2;
		// spravne vysledky vuci Matlabu
		double[] seq = new double[orderRate+1];
        double r = Math.PI / halfOrder;
        for (int n=0; n <= orderRate; n++){
        	seq[n]= 0.54 - 0.46 * Math.cos(n*r);
        }
		return seq;
	}
}
