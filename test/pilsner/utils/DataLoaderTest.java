package pilsner.utils;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Testovaci trida nacitani dat (double hodnot) ze souboru.
 * Jednotlive hodnoty musi byt oddeleny ENTEREM.
 *
 * @author Jiri Novotny
 */
public class DataLoaderTest {

    /**
     * Test of load method, of class DataLoader.
     */
    @Test
    public void testLoad() throws Exception {
        System.out.println("load");
        // TEST nacitani hodnot bez enteru na konci
        String path = "test/data/loader1.txt";
        double[] expResult = {1.0, 1.0, 1.0, 1.0, 1.0, 1.0};
        double[] result = DataLoader.load(path);
        assertArrayEquals(expResult, result, 0.0);

        // TEST nacitani hodnot s entery na konci
        path = "test/data/loader2.txt";
        double[] expResult2 = {1.0, 2.0, 3.0};
        result = DataLoader.load(path);
        assertArrayEquals(expResult2, result, 0.0);

        // TEST komentovana data
        path = "test/data/loader3.txt";
        double[] expResult3 = {1.0, 2.0, 3.0, 4.0, 5.0};
        result = DataLoader.load(path);
        assertArrayEquals(expResult3, result, 0.0);
    }

    @Test
    public void testLoad2() throws Exception {
        System.out.println("load with line-count");
        String path = "test/data/loader1.txt";
        double[] expResult = {1.0, 1.0, 1.0};
        double[] result = DataLoader.load(path, 3);
        assertArrayEquals(expResult, result, 0.0);
    }
}