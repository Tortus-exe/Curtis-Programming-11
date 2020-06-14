import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TheatreTests {
    Theatre test;

    @Before
    public void setUp(){
        int[] firstRowAisleSeats = {3, 4};
        test = new Theatre(60, firstRowAisleSeats, 10);
    }

    @Test
    public void testPurchaseSeat(){
        test.purchaseSeat(10);
        assertEquals(test.getSize(), 59);
        assertTrue(test.isPurchased(10));
        test.purchaseSeat(10);
        assertEquals(test.getSize(), 59);
    }

    @Test
    public void testSeatRemaining(){
        IntegerSet comparison  = new IntegerSet();
        for(int i = 0; i < 60;i++){
            comparison.insert(i+1);
        }
        test.purchaseSeat(15);
        comparison.remove(15);
        IntegerSet seatsRemaining = test.getSeatsRemaining();
        for(int i = 1; i<seatsRemaining.size(); i++){
            if(i==15){
                assertFalse(comparison.contains(i) || seatsRemaining.contains(i));
            }else{
                assertTrue(comparison.contains(i) && seatsRemaining.contains(i));
            }
        }
    }

    @Test public void testisAisle(){
        assertTrue(test.isAisle(3));
        assertTrue(test.isAisle(46));
        assertFalse(test.isAisle(7));
        assertFalse(test.isAisle(19));

    }
}
