package ThemePark;

import org.junit.Assert;
import org.junit.Test;

public class BuildingTest {

    @Test
    public void SUMXA_TEST() {
        Building building1 = new Building("ROAD", 0, 0, 1, 1, 1, 1);

        int sumXA_TEST1 = building1.sumXA();
        int SUMYB_TEST1 = building1.sumYB();

        Assert.assertEquals("sumXA_TEST1", 1, sumXA_TEST1);
        Assert.assertEquals("SUMYB_TEST1", 1, SUMYB_TEST1);

        //Test 2
        Building building2 = new Building("ROAD", 0, 0, 60, 60, 6, 6);

        int sumXA_TEST2 = building2.sumXA();
        int sumYB_TEST2 = building2.sumYB();

        Assert.assertEquals("sumXA_TEST2", 66, sumXA_TEST2);
        Assert.assertEquals("sumYB_TEST2", 66, sumYB_TEST2);
    }

}
