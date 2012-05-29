package pilsner.signalwindows;

/**
 * Hanningovo okno
 * @author Michal Nykl, 2008
 */
public class HanningWindow implements Window {
	public final double SIG_RATIO = 44;
	public final double BW_CONST = 6.22;
		
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
        	seq[n] = 0.5 - 0.5 * Math.cos(n*r);
        }
		return seq;
	}
}
