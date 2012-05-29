package pilsner.dwt;

import java.io.IOException;
import org.junit.Test;
import pilsner.utils.DataLoader;
import static org.junit.Assert.*;

/**
 * Test metody FastDaubechies8.
 *
 * @author Jiri Novotny
 */
public class FastDaubechies8Test {

    private double DELTA = 1E-14;
    private double DELTA_I = 2;

    /**
     * Test of transform method, of class FastDaubechies8.
     */
    @Test
    public void testTransform() {
        System.out.println("transform fast daubechies 8");
        double[] inputSignal = null, expResult = null;
        try {
            inputSignal = DataLoader.load("test/data/dwt/src_signal.txt");
            expResult = DataLoader.load("test/data/dwt/sig_db8.txt");
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        double[] result = FastDaubechies8.transform(inputSignal);
        assertArrayEquals(expResult, result, DELTA);
    }

    /**
     * Test of invTransform method, of class FastDaubechies8.
     */
    @Test
    public void testInvTransform() {
        System.out.println("invTransform fast daubechies 8");
        double[] inputSignal = null, expResult = null;
        try {
            inputSignal = DataLoader.load("test/data/dwt/sig_db8.txt");
            expResult = DataLoader.load("test/data/dwt/src_signal.txt");
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        double[] result = FastDaubechies8.invTransform(inputSignal);
        assertArrayEquals(expResult, result, DELTA_I);
    }

}