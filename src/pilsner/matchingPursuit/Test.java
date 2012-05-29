package pilsner.matchingPursuit;

import java.io.IOException;
import java.util.Arrays;
import pilsner.utils.DataLoader;

import org.apache.commons.math.MathException;

public class Test {

    public static void main(String[] args) {
        try {
            //double[] inputValues = DataLoader.load("D:\\programming\\eeg\\dsplib\\trunk\\mysinT.txt");
            double[] inputValues = DataLoader.load("D:\\programming\\eeg\\dsplib\\trunk\\mysin3t.txt");

            double[] sig = new double[512];
            for (int i = 0; i < sig.length; i++) {
                sig[i] = inputValues[i];
            }


            System.out.print("Na�ten� data: ");
            System.out.println(Arrays.toString(inputValues));
            try {
                double[][] resultSet = FourierMP.doMP(sig, 20); //FIXME !!! jen jedna iterace MP

                /*WignerMap map = new WignerMap(512, 256, 0, 512, 0, 256, 512);
                map.MakeWignerMap(result);

                File file = new File("D:\\map2.txt");
                try
                {
                BufferedWriter wr = new BufferedWriter(new FileWriter(file));
                for (int i = 0; i < map.SizeY; i++)
                {
                for (int j = 0; j < map.SizeX; j++)
                {
                if (j == 192)
                wr.write(new String(map.Map[j][i] + " XXX| "));
                else
                wr.write(new String(map.Map[j][i] + " | "));

                }
                wr.newLine();
                }
                wr.close();
                }
                catch (IOException e1)
                {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                }

                 */

                for (int i = 0; i < resultSet.length; i++) {
                    System.out.print(i + "| ");
                    for (int j = 0; j < resultSet[i].length; j++) {
                        System.out.print(resultSet[i][j] + ", ");
                    }
                    System.out.println();
                }


            } catch (IllegalArgumentException exception) {
                System.err.println(exception.toString());
            } catch (MathException e) {
                e.printStackTrace();
            }
        } catch (IOException exception) {
            System.err.println("Chyba p�i na��t�n� vstupn�ho souboru.");
            exception.printStackTrace();
        }


    }
}
