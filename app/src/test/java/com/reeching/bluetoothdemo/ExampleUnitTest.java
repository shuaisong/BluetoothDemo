package com.reeching.bluetoothdemo;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
        assertNotEquals(4, 3 + 2);
        int[] a = {1, 12, 3};
        int[] b = {1, 12, 3};
        assertArrayEquals("XXX", a, b);
        String s = null;
        assertNull(s);
        assertFalse(1 == 3);
    }
}