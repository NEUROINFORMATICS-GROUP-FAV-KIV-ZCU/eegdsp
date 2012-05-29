package pilsner.signalwindows;

/**
 * Tukeyho okno. Vytvoøené podle pøedpisu v nápovìdì matlabu a podle
 * zdrojového kódu který matlab používá pøímo pro výpoèet. V nìm citují
 * 	  [1] fredric j. harris [sic], On the Use of Windows for Harmonic Analysis
 *        with the Discrete Fourier Transform, Proceedings of the IEEE,
 *        Vol. 66, No. 1, January 1978, Page 67, Equation 38.
 *        
 *    w(i) = 1/2 * ( 1 + cos( pi * 2/r * i/(N - 1) * r/2) )  pro 0 <= n < r*N/2
 *    w(i) = 1                                               pro r*N/2 <= n < N/2    
 *    
 * kde r je pomìr konstantního intervalu okna vzhledem k jeho délce. Proto se
 * volí mezi 0 a 1.
 * 
 * Testováno proti matlabu a maximální odchylka je do 4e-7
 * */
public class TukeyWindow implements Window {

	private double ratio;
	
	/**
	 * Vytvoøí instanci tøídy pro generování Tuykeyho okna
	 * se zadaným pomìrem konstatní oblasti.
	 * 
	 * @param constRatio pomìr konstatní oblasti ke zbytku vzorkù.
	 * */
	public TukeyWindow(double constRatio){
		this.setConstRatio(constRatio);
	}
	
	/**
	 * Vytvoøí instanci tøídy pro generování Tuykeyho okna
	 * s pomìrem konstatní oblasti 0.5.
	 * */
	public TukeyWindow(){
		this.setConstRatio(0.5);
	}
	
	public void setConstRatio(double constRatio){
		if(constRatio >= 0 && constRatio <= 1){
			this.ratio = constRatio;
		}//jinak to nemá smysl
	}
	
	public double getConstRatio(){
		return this.ratio;
	}
	
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
		
		//pomocné konstanty
		double nHalf = (double)N / (double)2;
		double factor = (double)1 / (double)(N-1);
		int half = N / 2;
		if(N % 2 == 1){
			half += 1;
		}
		double ratio = this.ratio; //kvuli tomu, aby se nemuselo lézt do èl. promìný
		double perInv = 2 / ratio;
		double per = ratio / 2;
		
		//zakøivená èást poloviny okna
		int lastCurvedIndex = (int)Math.floor(nHalf * ratio);
		
		/*
		System.out.println("lastCurvedIndex = " + lastCurvedIndex);
		System.out.println("nHalf = " + nHalf);
		System.out.println("half = " + half);
		 //debug bordel   */
		
		for(int i = 0; i < lastCurvedIndex; i += 1){
			retval[i] = 0.5 * (1 + Math.cos(
					Math.PI * perInv * (i * factor - per)   
					));
		}
		
		//konstatní èást poloviny okna
		for(int i = lastCurvedIndex; i < half; i += 1){
			retval[i] = 1; 
		}
		
		//zrcadlení poloviny do druhé poloviny
		for(int i = half; i < N; i += 1){
			retval[i] = retval[N - 1 - i];
		}
		
		return retval;
	}

}
