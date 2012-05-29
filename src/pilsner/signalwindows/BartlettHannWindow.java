package pilsner.signalwindows;

import java.util.Arrays;

/**
 * T��da s implementac� modifikovan�ho Bartlett-Hannova okna.
 * 
 * podle p�edpisu na: http://en.wikipedia.org/wiki/Window_function
 * tj.:
 * 
 * 		w(i) = 0.62 - 0.48 * abs(i / (N - 1) - 0.5) -
 * 				0.38*cos(2 * pi * i / (N - 1)),
 * 
 * kde N je po�et vzork� a i = 0, 1 ... N - 1
 * 
 * V matlabu je to implementov�no trochu jinak, proto�e cos je
 * sud� funkce a oni si tam napo��taj� parametr <-0.5, 0.5>.
 * Ka�dop�dn� odchylka od matlab�ho okna a� 4e-7
 * 
 * @author Martin �imek
 * */
public class BartlettHannWindow implements Window {

	public double getBandwidthConst() {
		// TODO Auto-generated method stub
		return 1.46;
	}

	public double getSignalRatio() {
		// TODO Auto-generated method stub
		return 0;
	}

	public double[] getWinSequence(int orderRate) {
		//vytvo�en� pole pro okno
		double[] retval = new double[orderRate];
		
		//vypln�n� jedni�kou
		Arrays.fill(retval, 1);
		
		//p�en�soben� oknem, co� je jako zaps�n� okna
		multiplyByBartlettHannWindow(retval);
		
		//n�vrat
		return retval;
	}
	
	/**
	 * P�en�sob� pole doubl� bartlettov�m-hannov�m oknem
	 * podle p�edpisu na: http://en.wikipedia.org/wiki/Window_function
	 * tj.:
	 * 
	 * 		w(i) = 0.62 - 0.48 * abs(i / (N - 1) - 0.5) -
	 * 				0.38*cos(2 * pi * i / (N - 1)),
	 * 
	 * kde N je po�et vzork� a i = 0, 1 ... N - 1
	 * 
	 * V matlabu je to implementov�no trochu jinak, proto�e cos je
	 * sud� funkce a oni si tam napo��taj� parametr <-0.5, 0.5>.
	 * Ka�dop�dn� odchylka od matlab�ho okna a� 4e-7
	 * */
	public static void multiplyByBartlettHannWindow(double[] signal){
		if(signal == null || signal.length < 2){
			return;
		}
	
		//konstantn� ��sti vzorce
		double konst1 = (double)1 / (double)(signal.length - 1);
		double konst2 = konst1 * 2 * Math.PI;
		
		for(int i = 0; i < signal.length; i += 1){
			signal[i] *= 0.62 - 0.48 * Math.abs(i * konst1 - 0.5) -
						0.38* Math.cos(i * konst2);
		}
	}

}
