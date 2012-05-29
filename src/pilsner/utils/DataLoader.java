package pilsner.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class DataLoader {

    public static double[] load(String path) throws IOException {
        return load(path, -1);
    }

    public static double[] load(String path, int count) throws IOException {
        ArrayList<String> loadedValues = new ArrayList<String>();
        int counter = 1;

        BufferedReader br = new BufferedReader(new FileReader(path));

        String line;
        while ((line = br.readLine()) != null) {
            if (line.trim().equals("") || line.charAt(0) == '#') {
                continue;
            }
            loadedValues.add(line);
            if (counter == count) break;
            else counter++;
        }

        return listToArray(loadedValues);
    }

    private static double[] listToArray(ArrayList<String> list) {
        double[] array = new double[list.size()];

        for (int i = 0; i < array.length; i++) {
            array[i] = Double.parseDouble(list.get(i));
        }

        return array;
    }
}
