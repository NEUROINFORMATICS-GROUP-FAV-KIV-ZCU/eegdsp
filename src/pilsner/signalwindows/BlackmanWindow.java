package pilsner.signalwindows;

/**
 * Blackmanovo okno
 * @author Michal Nykl, 2008
 */
public class BlackmanWindow implements Window {
	public final double SIG_RATIO = 74;
	public final double BW_CONST = 11.13;
	
	public double getSignalRatio() {
		return SIG_RATIO;
	}
	
	public double getBandwidthConst() {
		return BW_CONST;
	}
	
	public double[] getWinSequence(int orderRate) {
		orderRate = orderRate-1;
		double halfOrder = ((double) orderRate) / 2;
		// spravne vysledky vuci Matlabu,
		// pouze 1 je zde 0.99999999999
		double[] seq = new double[orderRate+1];
		double r = Math.PI / halfOrder;
        for (int n=0; n <= orderRate; n++){
          seq[n] = 0.42 - 0.5 * Math.cos(n*r) + 0.08 * Math.cos(2*n*r);
        }
        return seq;
	}
}
