package pilsner.utils.math;

import org.junit.Test;
import pilsner.utils.Arrays;
import static org.junit.Assert.*;

/**
 *
 * @author k4chn1k
 */
public class ConvolutionTest {

    /**
     * Test of convolution method, of class Convolution.
     */
    @Test
    public void testConvolution() {
        System.out.println("convolution");
        double[] sig1 = {1.0, 2.0, 3.0, 4.0};
        double[] sig2 = {5.0, 6.0, 7.0};
        double[] expResult = {5.0, 16.0, 34.0, 52.0, 45.0, 28.0};
        double[] result = Convolution.convolution(sig1, sig2);
        System.out.println(Arrays.toString(result));
        assertArrayEquals(expResult, result, 0.0);

        double[] sig3 = {1.0, 2.0, 2.0, 3.0};
        double[] sig4 = {2.0, -1.0, 3.0};
        double[] expResult2 = {2.0, 3.0, 5.0, 10.0, 3.0, 9.0};
        result = Convolution.convolution(sig3, sig4);
        System.out.println(Arrays.toString(result));
        assertArrayEquals(expResult2, result, 0.0);
    }

    /**
     * Test of periodicConvolution method, of class Convolution.
     */
    @Test
    public void testPeriodicConvolution() {
        System.out.println("periodicConvolution");
        double[] sig1 = {1.0, 0.0, 1.0, 1.0};
        double[] sig2 = {1.0, 2.0, 3.0, 1.0};
        double[] expResult = {6.0, 6.0, 5.0, 4.0};
        double[] result = Convolution.periodicConvolution(sig1, sig2);
        System.out.println(Arrays.toString(result));
        assertArrayEquals(expResult, result, 0.0);
    }

}