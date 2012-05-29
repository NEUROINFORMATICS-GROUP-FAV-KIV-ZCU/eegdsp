package pilsner.utils;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Testování výpisů pole.
 *
 * @author Jiri Novotny
 */
public class ArraysTest {

    /**
     * Test of toString method, of class Arrays.
     */
    @Test
    public void testToString_doubleArr() {
        System.out.println("toString");
        double[] signal = {1.0, 2.0, 3.0};
        String expResult = "1.0, 2.0, 3.0";
        String result = Arrays.toString(signal);
        System.out.println(result);
        assertEquals(expResult, result);
    }

    /**
     * Test of toString method, of class Arrays.
     */
    @Test
    public void testToString_doubleArr_String() {
        System.out.println("toStringSep");
        double[] signal = {1.0, 2.0, 3.0};
        // separace stredniky
        String separator = ";";
        String expResult = "1.0;2.0;3.0";
        String result = Arrays.toString(signal, separator);
        System.out.println(result);
        assertEquals(expResult, result);

        // separace entery
        separator = "\n";
        expResult = "1.0\n2.0\n3.0";
        result = Arrays.toString(signal, separator);
        System.out.println(result);
        assertEquals(expResult, result);
    }

}