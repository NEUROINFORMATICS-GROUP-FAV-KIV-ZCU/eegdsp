package pilsner.utils.math;

import org.junit.Test;
import pilsner.utils.Arrays;
import static org.junit.Assert.*;

/**
 * Testovaci trida.
 *
 * @author Jiri Novotny
 */
public class Power2UtilsTest {

    /**
     * Test of log2 method, of class Power2Utils.
     */
    @Test
    public void testLog2() {
        System.out.println("log2");
        int x = 2;
        double expResult = 1.0;
        double result = Power2Utils.log2(x);
        System.out.println(result);
        assertEquals(expResult, result, 0.0);
        x = 3;
        expResult = 1.584962501;
        result = Power2Utils.log2(x);
        System.out.println(result);
        assertEquals(expResult, result, 0.000000001);
    }

    /**
     * Test of newNumberOfPowerBase2 method, of class Power2Utils.
     */
    @Test
    public void testNewNumberOfPowerBase2() {
        System.out.println("newNumberOfPowerBase2");
        int x = 5;
        int expResult = 8;
        int result = Power2Utils.newMajorNumberOfPowerBase2(x);
        System.out.println(result);
        assertEquals(expResult, result);
        x = 16;
        expResult = 16;
        result = Power2Utils.newMajorNumberOfPowerBase2(x);
        System.out.println(result);
        assertEquals(expResult, result);
        x = 1000;
        expResult = 1024;
        result = Power2Utils.newMajorNumberOfPowerBase2(x);
        System.out.println(result);
        assertEquals(expResult, result);
    }

    /**
     * Test of createPower2LengthArray method, of class Power2Utils.
     */
    @Test
    public void testCreatePower2LengthArray() {
        System.out.println("createPower2LengthArray");
        double[] inputSignal = {1.0, 1.0, 1.0};
        double[] expResult = {1.0, 1.0, 1.0, 0.0};
        double[] result = Power2Utils.createPower2LengthArray(inputSignal);
        System.out.println(Arrays.toString(result));
        assertArrayEquals(expResult, result, 0.0);

        double[] inputSignal2 = {1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0};
        double[] expResult2 = {1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
        result = Power2Utils.createPower2LengthArray(inputSignal2);
        System.out.println(Arrays.toString(result));
        assertArrayEquals(expResult2, result, 0.0);
    }

}