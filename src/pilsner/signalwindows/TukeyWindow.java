package pilsner.signalwindows;

/**
 * Tukeyho okno. Vytvo�en� podle p�edpisu v n�pov�d� matlabu a podle
 * zdrojov�ho k�du kter� matlab pou��v� p��mo pro v�po�et. V n�m cituj�
 * 	  [1] fredric j. harris [sic], On the Use of Windows for Harmonic Analysis
 *        with the Discrete Fourier Transform, Proceedings of the IEEE,
 *        Vol. 66, No. 1, January 1978, Page 67, Equation 38.
 *        
 *    w(i) = 1/2 * ( 1 + cos( pi * 2/r * i/(N - 1) * r/2) )  pro 0 <= n < r*N/2
 *    w(i) = 1                                               pro r*N/2 <= n < N/2    
 *    
 * kde r je pom�r konstantn�ho intervalu okna vzhledem k jeho d�lce. Proto se
 * vol� mezi 0 a 1.
 * 
 * Testov�no proti matlabu a maxim�ln� odchylka je do 4e-7
 * */
public class TukeyWindow implements Window {

	private double ratio;
	
	/**
	 * Vytvo�� instanci t��dy pro generov�n� Tuykeyho okna
	 * se zadan�m pom�rem konstatn� oblasti.
	 * 
	 * @param constRatio pom�r konstatn� oblasti ke zbytku vzork�.
	 * */
	public TukeyWindow(double constRatio){
		this.setConstRatio(constRatio);
	}
	
	/**
	 * Vytvo�� instanci t��dy pro generov�n� Tuykeyho okna
	 * s pom�rem konstatn� oblasti 0.5.
	 * */
	public TukeyWindow(){
		this.setConstRatio(0.5);
	}
	
	public void setConstRatio(double constRatio){
		if(constRatio >= 0 && constRatio <= 1){
			this.ratio = constRatio;
		}//jinak to nem� smysl
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
		
		//pomocn� konstanty
		double nHalf = (double)N / (double)2;
		double factor = (double)1 / (double)(N-1);
		int half = N / 2;
		if(N % 2 == 1){
			half += 1;
		}
		double ratio = this.ratio; //kvuli tomu, aby se nemuselo l�zt do �l. prom�n�
		double perInv = 2 / ratio;
		double per = ratio / 2;
		
		//zak�iven� ��st poloviny okna
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
		
		//konstatn� ��st poloviny okna
		for(int i = lastCurvedIndex; i < half; i += 1){
			retval[i] = 1; 
		}
		
		//zrcadlen� poloviny do druh� poloviny
		for(int i = half; i < N; i += 1){
			retval[i] = retval[N - 1 - i];
		}
		
		return retval;
	}

}
