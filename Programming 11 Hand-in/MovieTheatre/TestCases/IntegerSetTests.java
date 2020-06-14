import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class IntegerSetTests {
    IntegerSet testSet;

    @Before
    public void setup(){
        testSet = new IntegerSet();
    }

    @Test
    public void testInsertNotThere(){
        //check number is not already in set
        assertEquals(testSet.size(), 0);
        assertFalse(testSet.contains(3));
        //insert a number
        testSet.insert(3);
        //check the number is in the set
        assertEquals(testSet.size(), 1);
        assertTrue(testSet.contains(3));
    }
    @Test
    public void testInsertThere(){
        //check number is not already in set
        assertEquals(testSet.size(), 0);
        assertFalse(testSet.contains(3));
        //insert a number
        testSet.insert(3);
        //check the number is in the set
        assertEquals(testSet.size(), 1);
        assertTrue(testSet.contains(3));
        testSet.insert(3);
        //check the number is in the set
        assertEquals(testSet.size(), 1);
        assertTrue(testSet.contains(3));
    }
    @Test
    public void testRemove(){
        testSet.remove(3);
        assertEquals(testSet.size(), 0);
        assertFalse(testSet.contains(3));
        testSet.insert(3);
        testSet.remove(3);
        assertEquals(testSet.size(), 0);
        assertFalse(testSet.contains(3));

    }
}
