package pilsner.fastica.testpackage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import pilsner.fastica.FastICA;
import pilsner.fastica.FastICAException;

public class TestFastICA {
	
	private BufferedReader br;
	
	private String[] fileName = {"sinT.txt", "sin3t.txt", "t-t.txt", "t-t-1.txt"};
	double[][] inputSignals;
	double[][] outputSignals;
	
	//private static int NUM_ICS = 4;
	
	public TestFastICA() {
		inputSignals = new double[4][601];
		testAlgorithm();
	}
	
	private void testAlgorithm() {
		readFromFile();
		//FastICAConfig config = new FastICAConfig(TestFastICA.NUM_ICS, FastICAConfig.Approach.SYMMETRIC, 1.0, 1.0e-12, 1000, null);
		try {
			FastICA fica = new FastICA(inputSignals, 4);
			outputSignals = fica.getICVectors();
		} catch (FastICAException e) {
			e.printStackTrace();
		}
	}
	
	private void readFromFile() {
		for(int i = 0; i < fileName.length; i++) {
			File file = new File(fileName[i]);
			try {
				br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
				String line = "";
				int j = 0;
				while((line = br.readLine()) != null) {
					String[] signals = line.split(" ");
					for(String item : signals) {
						inputSignals[i][j++] = Double.valueOf(item);
						System.out.println(Double.valueOf(item));
					}
				}
				br.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
	/*	String inputFile1 = "b.txt";
		String inputFile2 = "test.txt";
		
		String[] filename = new String[4];
		filename[0] = "sinT.txt";
		filename[1] = "sin3t.txt";
		filename[2] = "t-t.txt";
		filename[3] = "t-t-1.txt"; */
		new TestFastICA();
	}
	
	/**
	 * (VS)
	 * Method ensures that given path is relative or absolute and returns either absolute 
	 * path directly or absolute path made from relative path and current working directory.
	 * If given path does not exist, null is returned.
	 * 
	 * @param path absolute or relative path to file
	 * @return correct absolute path 
	 */
	public String getCorrectPath(String path) {
		//double[][] inputSignals = readDataFromFile(filename);
		if(path == null || path.length() == 0) {
			return null;
		}
		try {
			boolean exists = true;
			File relativePath = new File(path);
			exists = relativePath.exists() && relativePath.isFile();
			
			if(relativePath != null && exists) {
				relativePath = relativePath.getCanonicalFile();
				return relativePath.toString();
			}
		} catch (IOException e) {
			return null;
		}
		
		try {
			boolean exists = true;
			File absolutePath = new File(path);
			exists = absolutePath.exists() && absolutePath.isFile();
			absolutePath = absolutePath.getCanonicalFile();
			
			if(absolutePath != null && exists) {
				return absolutePath.toString();
			}
		} catch (IOException e) {
			return null;
		}
		return null;
	}
}