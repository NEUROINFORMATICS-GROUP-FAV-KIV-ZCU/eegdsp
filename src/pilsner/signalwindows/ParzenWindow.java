package pilsner.signalwindows;

/**
 * Parzen (de la Valle-Poussin) window
 * okno je definováno jako:
 * 
 * 		w(i) = 2 * (1 - abs(i - (N-1)/2)/(N/2))^3		0 <= |i| < N/4
 * 		w(i) = 1 - 6 * ((i - (N-1)/2) / (N/2))^2 * (1 - (i - (N-1)/2)/(N/2))		N/4 <= |i| <= N/2
 * 
 * kde N jepoèet vzorkù a i = 0, 1... N-1
 * V dokumentaci matlabu je sice uveden vzorec podle kterého by údajnì mìlo okno být definováno
 * ale je uplnì špatnì. Takže podle zdrojáku matalabu se podaøilo rekontruovat pøedpis okna
 * a okno se od matlabovské implementace liší nejvýše o 4e-7.
 * 
 *  @author Martin Šimek
 *
 * */
public class ParzenWindow implements Window {

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
		
		int quarter = N / 4;
		int half = N / 2;
		if(N % 2 == 1){
			half += 1;
			quarter += 1;
		}
		/*
		double nHalf = (double)(N - 1)/ (double)2;
		double nHalfInverted = (double)2 / (double)(N - 1);
		double nHalfInvertedSquared = (double)4 / (double)((N - 1) * (N - 1));
		*/
		
		double nHalf = (double)(N - 1)/ (double)2;
		double nHalfInverted = (double)2 / (double)N;
		double nHalfInvertedSquared = (double)4 / (double)(N * N);
		
		//první ètvrtina
		double hlp;
		for(int i = 0; i < quarter; i += 1){
			hlp = 1 - Math.abs(i - nHalf) * nHalfInverted;
			hlp *= hlp * hlp; //výroba tøetí mocniny
			retval[i] = 2*hlp;
		}
		
		//druhá ètvrtina
		for(int i = quarter; i < half; i += 1){
			hlp = (i - nHalf);
			retval[i] = 1 - 6 * (hlp * hlp * nHalfInvertedSquared)*(1 - Math.abs(hlp) * nHalfInverted);
			
		}
		
		//kopírování pùlky
		for(int i = half; i < N; i += 1){
			retval[i] = retval[N - 1 - i];
			//retval[N - 1 - i] = retval[i]; 
		}
		
		return retval;
	}

}
