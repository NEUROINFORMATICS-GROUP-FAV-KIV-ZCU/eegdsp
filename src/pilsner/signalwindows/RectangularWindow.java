package pilsner.signalwindows;

/**
 * Pravouhle okno
 * @author Michal Nykl, 2008
 */
public class RectangularWindow implements Window {
	public final double SIG_RATIO = 21;
	public final double BW_CONST = 1.84;
	
	public double getSignalRatio() {
		return SIG_RATIO;
	}
	
	public double getBandwidthConst() {
		return BW_CONST;
	}
	
	public double[] getWinSequence(int orderRate) {
		orderRate = orderRate -1;
		double seq[] = new double[orderRate+1];
        for (int n=0; n <= orderRate; n++){
	          seq[n] = 1;
        }
		return seq;
	}
}
