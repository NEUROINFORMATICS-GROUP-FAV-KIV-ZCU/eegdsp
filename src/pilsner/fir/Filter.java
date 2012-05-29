package pilsner.fir;

/**
 * Trida, jejiz hlavni ulohou je poskytovat
 * hodnoty zvoleneho typu filtru.
 * @author Michal Nykl, 2008
 */
public class Filter {
	
	/**
	 * Vsechny filtry, ktere tato metoda poskytuje
	 * @author Michal Nykl
	 */
	public enum FilterTypes {LOW_PASS, HIGH_PASS, BANDTH_PASS, BAND_REJECT};
	
	/**
	 * Poskytuje koeficienty filtru pro zvoleny typ filtru.
	 * Vysledna posloupnost je symetrickou pulkou konecneho filtru.
	 * Hodnoty kotrolovany oproti MATLABu. 
	 * @param filterType Typ pozadovaneho filtru
	 * @param orderRate Pocet pozadovanych hodnot
	 * @param fd Dolni frekvence
	 * @param fh Horni frekvence
	 * @param fvz Vzorkovaci frekvence
	 * @return double[] Pole koeficientu filtru (dolni symetricka polovina)
	 */
	public static double[] getFilterCoef(FilterTypes filterType, int orderRate,
            double bandPassFreqFrom, double bandPassFreqTo, double samplingFrequency) {
		 double[] valuesOfFilter = new double[orderRate + 1];
		 double halfOrder = ((double)orderRate) / 2;
		 double w1 = 2 * Math.PI * bandPassFreqTo / samplingFrequency; // horni kmitocet
		 double w0 = 2 * Math.PI * bandPassFreqFrom / samplingFrequency; // dolni kmitocet
		 double m;

		 switch (filterType) {
			  case LOW_PASS: // dolni propust
				  for (int n = 0; n <= halfOrder; n++){
					  m = halfOrder - n;
					  if (m > 0){
						  valuesOfFilter[n] = (Math.sin(m * w1) / (m * Math.PI));
					  } else {
						  valuesOfFilter[n] = (w1 * Math.cos(m * w1) / (Math.PI));
					  }
				  }
				  break;
			  case HIGH_PASS: // horni propust
				  for (int n = 0; n <= halfOrder; n++){
					  m = halfOrder - n;
					  if (m > 0){
						  valuesOfFilter[n] = (0 - Math.sin(m * w0)) / (m * Math.PI);
					  } else {
						  valuesOfFilter[n] = 1 - (w0 / (Math.PI));
					  }
				  }
			      break;
			  case BANDTH_PASS: // pasmova propust
				  for (int n = 0; n <= halfOrder; n++){
					  m = halfOrder - n;
					  if (m > 0){
						  valuesOfFilter[n] = (Math.sin(m * w1) - Math.sin(m * w0)) / (m * Math.PI);
					  } else {
						  valuesOfFilter[n] = ((w1 - w0) / (Math.PI));
					  }
				  }
			      break;
			  case BAND_REJECT: // pasmova zadrz
				  for (int n = 0; n <= halfOrder; n++){
					  m = halfOrder - n;
					  if (m > 0){
						  valuesOfFilter[n] = (Math.sin(m * w0) - Math.sin(m * w1)) / (m * Math.PI);
					  } else {
						  valuesOfFilter[n] = 1 - ((w1 - w0) / (Math.PI));
					  }
				  }
				  break;
		  }
		 
		 // doplneni symetricke poloviny filtru
		 for (int i = 0; i <= halfOrder; i++) {
			 valuesOfFilter[(valuesOfFilter.length - 1 - i)] = valuesOfFilter[i];
		 }
		 return valuesOfFilter;
	}
}
