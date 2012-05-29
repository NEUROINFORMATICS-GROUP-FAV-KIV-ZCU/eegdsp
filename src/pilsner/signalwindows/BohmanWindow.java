package pilsner.signalwindows;

/**
 * Bohmanovo okno, které je napsáno podle definice z:
 * http://zone.ni.com/reference/en-XX/help/371361D-01/lvanls/bohman/
 * s modifikací tedy má pøedpis:
 * 
 * 		w(i) = ( 1 - abs(i - (n - 1)/2)/((n - 1)/2) )*
 * 				cos(pi * abs(i - (n - 1)/2)/((n - 1)/2)) +
 *  			1/pi * sin(pi * abs(i - (n - 1)/2)/((n - 1)/2)),
 * 
 * kde 0 <= i < n
 * 
 * potom to pøesnì koresponduje s matlabem s chybou do 4e-7
 * 
 * @author Martin Šimek
 * */
public class BohmanWindow implements Window {

	public double getBandwidthConst() {
		// TODO Auto-generated method stub
		return 0;
	}

	public double getSignalRatio() {
		// TODO Auto-generated method stub
		return 0;
	}

	public double[] getWinSequence(int N) {
		double[] retval = new double[N];
		
		int half = N/2;
		if(half % 2 == 1){
			half += 1; //aby to napoèítalo až na prostøední prvek
		}
		
		double factor = (double)1 / ((double)(N - 1) / (double)2);
		double nHalf = ((double)(N - 1) / (double)2);
		double invertedPi = 1 / Math.PI;
		
		/* napoèítání první pùlky okna */
		retval[0] = 0;
		for(int i = 1; i <= half; i += 1){
			retval[i] = (1 - Math.abs(i - nHalf) * factor)*Math.cos(Math.PI * Math.abs(i - nHalf) * factor) + 
			invertedPi * Math.sin(Math.PI * Math.abs(i - nHalf) * factor); 
		}
		
		/* kopírování první pùlky do druhé */
		for(int i = half + 1; i < N; i += 1){
			retval[i] = retval[N - 1 - i];
		}
		
		return retval;
	}

}
