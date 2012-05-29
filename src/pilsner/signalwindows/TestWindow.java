package pilsner.signalwindows;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Slouží pro vyexportování sekvence okna v binární podobì do souboru,
 * který se následnì naèítá v matlabu a testuje se proti jeho funkcím.
 * 
 * @author Martin Šimek
 * */
public class TestWindow {

	/**
	 * Pøevede pole 8 bajtù na jeden double. Poøadí bajtù
	 * je jak funkce napovída little endian neboli PCèko
	 * */
	public static double littleEndianBytesToDouble(byte[] doubleBytes){
		long hlp = 0L;
		hlp = (
				((long)((byte)doubleBytes[7] & 0xFF) << 56) |
				((long)((byte)doubleBytes[6] & 0xFF) << 48) |
				((long)((byte)doubleBytes[5] & 0xFF) << 40) |
				((long)((byte)doubleBytes[4] & 0xFF) << 32) |
				((long)((byte)doubleBytes[3] & 0xFF) << 24) |
				((long)((byte)doubleBytes[2] & 0xFF) << 16) |
				((long)((byte)doubleBytes[1] & 0xFF) << 8) |
				(long)((byte)doubleBytes[0] & 0xFF)
		);
		return Double.longBitsToDouble(hlp);
	} 
	

	/**
	 * Pøevede double na pole 8 bajtù. Poøadí bajtù
	 * je jak funkce napovída little endian neboli PCèko
	 * */
	public static byte[] doubleToLittleEndianBytes(double in){
		byte[] bytes = new byte[8];
		long hlp;
		hlp = Double.doubleToLongBits(in);
		 bytes[7] = (byte)((hlp >> 56) & 0xFF);
		 bytes[6] = (byte)((hlp >> 48) & 0xFF);
		 bytes[5] = (byte)((hlp >> 40) & 0xFF);
		 bytes[4] = (byte)((hlp >> 32) & 0xFF);
		 bytes[3] = (byte)((hlp >> 24) & 0xFF);
		 bytes[3] = (byte)((hlp >> 16) & 0xFF);
		 bytes[1] = (byte)((hlp >> 8) & 0xFF);
		 bytes[0] = (byte)((hlp >> 0) & 0xFF);
				 
		return bytes;
	}
	
	/**
	 * Pøevede int na pole 4 bajtù. Poøadí bajtù
	 * je jak funkce napovída little endian neboli PCèko
	 * */
	public static byte[] intToLittleEndianBytes(int in){
		byte[] bytes = new byte[4];
		 bytes[3] = (byte)((in >> 24) & 0xFF);
		 bytes[2] = (byte)((in >> 16) & 0xFF);
		 bytes[1] = (byte)((in >> 8) & 0xFF);
		 bytes[0] = (byte)((in >> 0) & 0xFF);
				 
		return bytes;
	}
	
	/**
	 * Pøevede pole 4 bajtù na jeden int. Poøadí bajtù
	 * je jak funkce napovída little endian neboli PCèko
	 * */
	public static int littleEndianBytesToInt(byte[] intBytes){
		int hlp = 0;
		hlp = (
				((int)((byte)intBytes[3] & 0xFF) << 24) |
				((int)((byte)intBytes[2] & 0xFF) << 16) |
				((int)((byte)intBytes[1] & 0xFF) << 8) |
				(int)((byte)intBytes[0] & 0xFF)
		);
		return hlp;
	}

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int[] series = {1, 2, 3, 4, 8, 10, 11, 100, 101, 1000, 1001, 1024};
		WindowFactory wf = new WindowFactory();
		Window windowGenerator = wf.getFilterWindow(WindowFactory.WINDOWS.FLAT_TOP_WINDOW, new Double(.3));
		int cnt = series[11];
		double[] windowSequence = windowGenerator.getWinSequence(cnt);
		
		//System.out.print(Arrays.toString(windowSequence));
		try{
			FileOutputStream fileOut = new FileOutputStream("res/tested_window.dat");
			fileOut.write(intToLittleEndianBytes(cnt)); //výpis poètu prvkù
			
			// výpis smaplù vokna 
			for(int i = 0; i < cnt; i += 1){
				fileOut.write(doubleToLittleEndianBytes(windowSequence[i]));
			}
			
			fileOut.close();
			System.out.print("hotovo");
		}catch(IOException ioe){
			System.out.print("všechno se mi sype pod rukama");
			ioe.printStackTrace();
		}

	} 

}
