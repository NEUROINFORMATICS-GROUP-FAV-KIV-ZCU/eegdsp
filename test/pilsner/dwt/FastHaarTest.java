package pilsner.dwt;

import java.io.IOException;
import org.junit.Test;
import pilsner.utils.DataLoader;
import static org.junit.Assert.*;

/**
 * Test metody FastHaar.
 *
 * @author Jiri Novotny
 */
public class FastHaarTest {

    private double DELTA = 1E-13;
    private double DELTA_I = 1E-14;

    /**
     * Test Haar transformace.
     * Pro testovani se pouzivaji dva signaly (16 a 2048 prvku).
     * Referencni vysledky jsou generovany v MATLABu 2009b.
     */
    @Test
    public void testTransform() {
        System.out.println("transform fast haar");
        double[] inputSignal = null, expResult = null;
        try {
            inputSignal = DataLoader.load("test/data/dwt/src_signal2.txt");
            expResult = DataLoader.load("test/data/dwt/sig_haar2.txt");
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        double[] result = FastHaar.transform(inputSignal);
        assertArrayEquals(expResult, result, DELTA);

        try {
            inputSignal = DataLoader.load("test/data/dwt/src_signal.txt");
            expResult = DataLoader.load("test/data/dwt/sig_haar.txt");
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        double[] result2 = FastHaar.transform(inputSignal);
        assertArrayEquals(expResult, result2, DELTA);
        
    }

    /**
     * Test rekonstrukce signalu z transformovaneho.
     * Pouzivaji se referencni vysledky a porovnavaji se s puvodnim signalem.
     * Testuji se stejne signaly jako v predchozim pripade.
     */
    @Test
    public void testInvTransform() {
        System.out.println("invTransform fast haar");
        double[] inputSignal = null, expResult = null;
        try {
            inputSignal = DataLoader.load("test/data/dwt/sig_haar2.txt");
            expResult = DataLoader.load("test/data/dwt/src_signal2.txt");
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        double[] result = FastHaar.invTransform(inputSignal);
        assertArrayEquals(expResult, result, DELTA_I);

        try {
            inputSignal = DataLoader.load("test/data/dwt/sig_haar.txt");
            expResult = DataLoader.load("test/data/dwt/src_signal.txt");
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        double[] result2 = FastHaar.invTransform(inputSignal);
        assertArrayEquals(expResult, result2, DELTA_I);
    }

}