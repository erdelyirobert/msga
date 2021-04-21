package board;

import ThemePark.Building;
import ThemePark.Guest;
import ThemePark.Trash;
import ThemePark.Worker;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;


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
        ArrayList<Connection> connections = new ArrayList<Connection>();
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
    public void MoneyTest() {


    }

    @Test
    public void CoordianteTests() throws IOException {
        TPBoard board = new TPBoard();
        double distance = board.coordinateDistance(0, 4, 1, 4);
        Assert.assertEquals("Distance error1", (double) 5.0, distance, 0);

        double distance2 = board.coordinateDistance(1, 4, 0, 4);
        Assert.assertEquals("Distance error2", (double) 5.0, distance2, 0);

    }

}