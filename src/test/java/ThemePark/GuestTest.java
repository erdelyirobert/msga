package ThemePark;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.stream.IntStream;

public class GuestTest {

    @Test
    public void TestGuest() {
        Guest guest = new Guest("GUEST", 1, 1, 1, 1);
       Assert.assertEquals("MOOD",10,guest.getMood());

    }

    @Test
    public void TestDirectionOfGuest() {

        ArrayList<Guest> guests = new ArrayList<Guest>();
        guests.add(new Guest("GUEST",1,1,1,1));
        for (int i = 0; i < guests.size(); i++) {
            guests.get(i).setDirection(1);
        }

        for (int i = 0; i < guests.size(); i++) {
            Assert.assertNotNull(guests.get(i).getDirection());
        }

        int bound = guests.size();
        IntStream.range(0, bound).forEachOrdered(i -> Assert.assertNotNull("Guest mood", guests.get(i).getMood()));
    }
}