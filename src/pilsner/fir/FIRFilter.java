package pilsner.fir;

import pilsner.fir.Filter.FilterTypes;
import pilsner.signalwindows.*;
import pilsner.signalwindows.WindowFactory.WINDOWS;
import pilsner.utils.Arrays;

/**
 * Trida poskytujici hodnoty FIR filtru, k cemuz vyuziva hodnot ziskanych z
 * pozadovaneho typu filtru a typu okna pro filtrovani. Hodnoty kontrolovany
 * oproti MATLABu
 * 
 * @author Michal Nykl, 2008
 */
public class FIRFilter {
	private int order; // rad filtru
	// private int windowType; // typ okna pro filtrovani
	private WINDOWS windowType;
	private FilterTypes filterType; // typ filtru
	//tovarna vraceji okna filtru
	private WindowFactory windowF = new WindowFactory(); 
	 // frekvence
	private double bandPassFreqFrom; // dolni
	private double bandPassFreqTo; // horni
	private double samplingFrequency; // vzorkovaci

	private double[] valuesOfFilter; // hodnoty FIR filtru
	private double[] winSeq; // hodnoty zvoleneho okna
	private double[] filtrSeq; // hodnoty zvolenoho filtru

	/**
	 * Nastaveni FIR filtru
	 * 
	 * @param order
	 *          Rad filtru (musi byt sudy)
	 * @param filterType
	 *          Typ filtru LP, HP, BP, BS
	 * @param windowType
	 *          Druh okna (viz. WindowFactory)
	 * @param bandPassFreqFrom
	 *          Dolni frekvence
	 * @param bandPassFreqTo
	 *          Horni frekvence
	 * @param fvz
	 *          Vzorkovaci frekvence
	 */
	public FIRFilter(int order, FilterTypes filterType, WINDOWS windowType,
			double bandPassFreqFrom, double bandPassFreqTo, double samplingFrequency) {
		// orderTest(order);
		this.valuesOfFilter = new double[order + 1];
		this.order = order;
		this.filterType = filterType;
		this.windowType = windowType;
		this.bandPassFreqFrom = bandPassFreqFrom;
		this.bandPassFreqTo = bandPassFreqTo;
		this.samplingFrequency = samplingFrequency;
	}

	/**
	 * Kontrola sudosti radu filtru
	 * 
	 * @param order
	 *          Rad filtru
	 * @throws IllegalArgumentException
	 *           Vyjimka oznamujici chybny lichy rad
	 */
	private void orderTest(int order) throws IllegalArgumentException {
		if (order % 2 != 0)
			throw new IllegalArgumentException("Order is not even.");
	}

	/**
	 * Kontrola, zda-li pozadovane frekvence filtru jsou v rozmezi od 0 do
	 * poloviny vzorkovaci frekvence.
	 * 
	 * @param fd
	 *          Frekvence dolni
	 * @param fh
	 *          Frekvence horni
	 * @param fvz
	 *          Frekvence vzorkovaci
	 * @throws IllegalArgumentException
	 *           Vyjimka oznamujici prekroceni dovolenych hodnot
	 */
	private void rateTest(double bandPassFreqFrom, double bandPassFreqTo, double samplingFrequency)
			throws IllegalArgumentException {
		if ((samplingFrequency / bandPassFreqFrom) < 2 || bandPassFreqFrom < 0)
			throw new IllegalArgumentException(
					"Low rate must be greater than 0 and less than (Sampling Rate / 2).");
		if ((samplingFrequency / bandPassFreqTo) < 2 || bandPassFreqTo < 0)
			throw new IllegalArgumentException(
					"High rate must be greater than 0 and less than (Sampling Rate / 2).");
	}

	/**
	 * Navrh filtru. Pronasobeni signalu ziskaneho z okna se signalem z filtru a
	 * pridani symetricke poloviny.
	 */
	public void firFilterDesign() {
		rateTest(bandPassFreqFrom, bandPassFreqTo, samplingFrequency);
		// pozadovane okno
		Window fWindow = windowF.getFilterWindow(windowType);
		// ziskani hodnot okna
		winSeq = fWindow.getWinSequence(order + 1);
		// ziskani hodnot filtru
		filtrSeq = Filter.getFilterCoef(filterType, order, bandPassFreqFrom,
				bandPassFreqTo, samplingFrequency);
		// pronasobeni okna a filtru
		for (int i = 0; i < winSeq.length; i++) {
			valuesOfFilter[i] = winSeq[i] * filtrSeq[i];
		}
	}

	/**
	 * Poskytuje minimalni odstup signalu [dB] pro dane okno
	 * 
	 * @return double Odstup signalu v [dB] pro dane okno
	 */
	public double getSignalRatio() {
		return windowF.getFilterWindow(windowType).getSignalRatio();
	}

	/**
	 * Nastaveni typu filtru
	 * 
	 * @param filterType
	 *          Typ filtru
	 */
	public void setFilterType(FilterTypes filterType) {
		this.filterType = filterType;
	}

	/**
	 * Nastaveni radu filtru
	 * 
	 * @param order
	 *          Rad filtru (musi byt sudy)
	 */
	public void setOrder(int order) throws Exception {
		orderTest(order);
		this.order = order;
	}

	/**
	 * Nastaveni vzorkovaci frekvence
	 * 
	 * @param fvz
	 *          Vzorkovaci frekvence
	 */
	public void setSamplingRate(double samplingFrequency) {
		this.samplingFrequency = samplingFrequency;
	}

	/**
	 * Nastaveni dolni frekvence
	 * 
	 * @param bandPassFreqFrom
	 *          Dolni frekvence
	 */
	public void setLowRate(double bandPassFreqFrom) {
		this.bandPassFreqFrom = bandPassFreqFrom;
	}

	/**
	 * Nastaveni horni frekvence
	 * 
	 * @param bandPassFreqTo
	 *          Horni frekvence
	 */
	public void setHighRate(double bandPassFreqTo) {
		this.bandPassFreqTo = bandPassFreqTo;
	}

	/**
	 * Nastaveni pozadovaneho okna filtru
	 * 
	 * @param win
	 *          Typ okna
	 */
	public void setWindowType(WINDOWS win) {
		this.windowType = win;
	}

	/**
	 * Poskytuje hodnotu FIR filtru v miste zvoleneho koeficientu
	 * 
	 * @return double Hodnotu filtru pro zvoleny index
	 */
	public double getFIRFilterCoeff(int i) {
		return valuesOfFilter[i];
	}

	/**
	 * Poskytuje cely vysledny FIR filtr
	 * 
	 * @return double[] Pole hodnot ziskanych z FIR filtru
	 */
	public double[] getAllFIRFilterCoeff() {
		return valuesOfFilter;
	}

	/**
	 * Poskytuje hodnoty zvoleneho filtru
	 * 
	 * @return double[] Hodnoty zvoleneho filtru
	 */
	public double[] getAllFilterCoeff() {
		return filtrSeq;
	}

	/**
	 * Poskytuje hodnoty zvoleneho okna
	 * 
	 * @return double[] Hodnoty zvoleneho okna
	 */
	public double[] getAllWindowCoeff() {
		return winSeq;
	}

	/**
	 * Prevedeni pole hodnot ziskanych z FIR filtru na String
	 * 
	 * @param separator
	 *          Oddelovac jednotlivych hodnot
	 * @return String ziskany z hodnot filtru
	 */
	public String allFIRFilterCoeffToString(String separator) {
		return Arrays.toString(valuesOfFilter, separator);
	}
}
