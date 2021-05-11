package board;

import ThemePark.Building;
import ThemePark.Guest;
import ThemePark.Trash;
import ThemePark.Worker;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

/*
Methods to be tested:
-isItRoad() tesztelve
-onlyOneNeighbourRoad() testelve
-checkBin()
-checkRoad()
-testDirection()
-checkIntersection()
-pointsWithinCircle()
-isCanBuildOn() tesztelve
-changeMoodByGeneralEquipment()
-workerSalary() testelve

*/

public class TPBoardTest {
    int x = 0;
    int y = 0;
    //JUnit Suite Test
    private int segmentSize = 20; //size of one grid

    @Test
    public void ArrayListTest() {
        ArrayList<Building> buildings = new ArrayList<Building>();
        ArrayList<Guest> guests = new ArrayList<Guest>();
        ArrayList<Worker> workers = new ArrayList<Worker>();
        ArrayList<Building> roads = new ArrayList<Building>();
        ArrayList<Trash> trashes = new ArrayList<Trash>();


        Building starterRoad = new Building("ROAD", 0, 0, 60, 80, segmentSize, segmentSize);
        buildings.add(starterRoad);

        buildings.add(new Building("ROAD", 0.0, 10, x - (x % segmentSize), y - (y % segmentSize), segmentSize, segmentSize));
        roads.add(new Building("ROAD", 0.0, 10, x - (x % segmentSize), y - (y % segmentSize), segmentSize, segmentSize));

        buildings.add(new Building("BUSH", 0.0, 10, x - (x % segmentSize), y - (y % segmentSize), segmentSize, segmentSize));
        buildings.add(new Building("BIN", 0.0, 10, x - (x % segmentSize), y - (y % segmentSize), segmentSize, segmentSize));
        buildings.add(new Building("TREE", 0.0, 10, x - (x % segmentSize), y - (y % segmentSize), segmentSize * 2, segmentSize * 2));

        Assert.assertNotNull(buildings);
        Assert.assertEquals("Not good vale for segment", segmentSize, 20);


    }
    @Test
    public void isItRoadTest() throws IOException {
        TPBoard board = new TPBoard();
        ArrayList<Building> buildings = new ArrayList<Building>();
        boolean test1 = board.isItRoad(1,1);
        Assert.assertFalse("No road",test1);

        Building starterRoad = new Building("ROAD", 0, 0, 60, 80, segmentSize, segmentSize);
        buildings.add(starterRoad);

        boolean test2 = board.isItRoad(buildings.get(0).getLocation_X(),buildings.get(0).getLocation_Y());
        Assert.assertTrue("ROAD ON ROAD",test2);


        Building road2 = new Building("ROAD", 0, 0, 100, 100, segmentSize, segmentSize);
        buildings.add(road2);

        boolean test3 = board.isItRoad(buildings.get(1).getLocation_X(),buildings.get(1).getLocation_Y());
        Assert.assertFalse("ROAD ON ROAD",test3);






    }
    @Test
    public void MoneyTest() throws IOException {

        TPBoard board = new TPBoard();
        int budget = board.budget;
        Assert.assertEquals("Budget test", budget, board.getBudget());
    }

    @Test
    public void WorkerSalary_TEST() throws IOException {

        TPBoard board = new TPBoard();
        board.workerSalary();

    }

    @Test
    public void CoordianteTests() throws IOException {
        TPBoard board = new TPBoard();
        double distance = board.coordinateDistance(0, 4, 1, 4);
        Assert.assertEquals("Distance error1", (double) 5.0, distance, 0);

        double distance2 = board.coordinateDistance(1, 4, 0, 4);
        Assert.assertEquals("Distance error2", (double) 5.0, distance2, 0);

    }


    @Test
    public void testDirection() throws IOException {
        int segmentSize = 20;

        TPBoard board = new TPBoard();
        boolean firstTestDir = board.testDirection(1, 1, 0);
        Assert.assertTrue("firstTestDir", firstTestDir);

        boolean wrongDirection = board.testDirection(1, 1, 66);
        Assert.assertTrue("wrongDirection", firstTestDir);

    }

    @Test
    public void isCanBuildOn_TEST() throws IOException {
        TPBoard board = new TPBoard();
        int segmentSize = board.getSegmentSize();
        ArrayList<Building> buildings = new ArrayList<Building>();

        Building starterRoad = new Building("ROAD", 0, 0, 60, 80, segmentSize, segmentSize);
        buildings.add(starterRoad);

        boolean isBuildvarT1 = board.isCanBuildOn(1, 1);
        Assert.assertTrue("isBuildvarT1", isBuildvarT1);

        //Can you build on the starterroad?
        boolean isBuildvarT2 = board.isCanBuildOn(60, 80);
        Assert.assertFalse("Build on starterRoad", isBuildvarT2);

    }
}