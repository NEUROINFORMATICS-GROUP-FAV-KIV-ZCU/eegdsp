package pilsner.signalwindows;

/**
 * Interface oken pro filtraci
 * @author Michal Nykl, 2008
 */
public interface Window {
	
	/**
	 * Poskytuje minimalni odstup signalu v [dB]
	 * @return double Odstup signalu v [dB]
	 */
	public double getSignalRatio();
	
	/**
	 * Poskytuje koeficient sirky pasma
	 * @return double Koeficient sirky pasma
	 */
	public double getBandwidthConst();
	
	/**
	 * Pro rad vzorku vraci posloupnost hodnot vybraneho okna
	 * @param orderRate Pocet vzorku
	 * @return double[] Posloupnosti hodnot
	 */
	public double[] getWinSequence(int orderRate);
}
