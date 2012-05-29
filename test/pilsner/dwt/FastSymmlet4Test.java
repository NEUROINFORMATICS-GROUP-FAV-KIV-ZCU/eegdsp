package pilsner.dwt;

import java.io.IOException;
import org.junit.Test;
import pilsner.utils.DataLoader;
import static org.junit.Assert.*;

/**
 * Test metody FastSymmlet4.
 *
 * @author Jiri Novotny
 */
public class FastSymmlet4Test {

    private double DELTA = 1E-13;
    private double DELTA_I = 2;

    /**
     * Test of transform method, of class FastSymmlet4.
     */
    @Test
    public void testTransform() {
        System.out.println("transform fast symmlet 4");
        double[] inputSignal = null, expResult = null;
        try {
            inputSignal = DataLoader.load("test/data/dwt/src_signal.txt");
            expResult = DataLoader.load("test/data/dwt/sig_sym4.txt");
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        double[] result = FastSymmlet4.transform(inputSignal);
        assertArrayEquals(expResult, result, DELTA);
    }

    /**
     * Test of invTransform method, of class FastSymmlet4.
     */
    @Test
    public void testInvTransform() {
        System.out.println("invTransform fast symmlet 4");
        double[] inputSignal = null, expResult = null;
        try {
            inputSignal = DataLoader.load("test/data/dwt/sig_sym4.txt");
            expResult = DataLoader.load("test/data/dwt/src_signal.txt");
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        double[] result = FastSymmlet4.invTransform(inputSignal);
        assertArrayEquals(expResult, result, DELTA_I);
    }

}