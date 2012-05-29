package pilsner.dwt;

import java.io.IOException;
import org.junit.Test;
import pilsner.utils.Arrays;
import pilsner.utils.DataLoader;
import static org.junit.Assert.*;

/**
 * Test metody FastDaubechies2.
 *
 * @author Jiri Novotny
 */
public class FastDaubechies2Test {

    private double DELTA = 1E-14;
    private double DELTA_I = 5;

    /**
     * Test of transform method, of class FastDaubechies2.
     */
    @Test
    public void testTransform() {
        System.out.println("transform fast daubechies 2");
        double[] inputSignal = null, expResult = null;
        try {
            inputSignal = DataLoader.load("test/data/dwt/src_signal.txt");
            expResult = DataLoader.load("test/data/dwt/sig_db2.txt");
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        double[] result = FastDaubechies2.transform(inputSignal);
        assertArrayEquals(expResult, result, DELTA);
    }

    /**
     * Test of invTransform method, of class FastDaubechies2.
     */
    @Test
    public void testInvTransform() {
        System.out.println("invTransform fast daubechies 2");
        double[] inputSignal = null, expResult = null;
        try {
            inputSignal = DataLoader.load("test/data/dwt/sig_db2.txt");
            expResult = DataLoader.load("test/data/dwt/src_signal.txt");
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        double[] result = FastDaubechies2.invTransform(inputSignal);
        assertArrayEquals(expResult, result, DELTA_I);
    }

}