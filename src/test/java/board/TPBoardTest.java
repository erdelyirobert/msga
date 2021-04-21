package board;

import ThemePark.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.ArrayList;


public class TPBoardTest {
    //JUnit Suite Test
    private int segmentSize = 20; //size of one grid
    int x=0;
    int y=0;


    @Test
    public void ArrayListTest(){
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
        buildings.add(new Game("rollercoaster_underconstruction", 5.0, 1000, x - (x % segmentSize) - 3 * segmentSize, y - (y % segmentSize) - 2 * segmentSize, segmentSize * 6, segmentSize * 4, 15));
        buildings.add(new Game("train_underconstruction", 5.0, 800, x - (x % segmentSize) - 2 * segmentSize, y - (y % segmentSize) - segmentSize, segmentSize * 4, segmentSize * 4, 15));
        buildings.add(new Game("waterpark_underconstruction", 5.0, 1000, x - (x % segmentSize) - 2 * segmentSize, y - (y % segmentSize) - 2 * segmentSize, segmentSize * 6, segmentSize * 4, 15));
        buildings.add(new Game("waterpark_underconstruction", 5.0, 1000, x - (x % segmentSize) - 2 * segmentSize, y - (y % segmentSize) - 2 * segmentSize, segmentSize * 6, segmentSize * 4, 15));
        buildings.add(new Game("wheel_underconstruction", 5.0, 1500, x - (x % segmentSize) - 2 * segmentSize, y - (y % segmentSize) - 2 * segmentSize, segmentSize * 6, segmentSize * 6, 15));
        buildings.add(new Game("slide_underconstruction", 5.0, 800, x - (x % segmentSize) - segmentSize, y - (y % segmentSize) - segmentSize, segmentSize * 4, segmentSize * 4, 15));
        buildings.add(new Restaurant("RESTAURANT", 0.0, 600, x - (x % segmentSize), y - (y % segmentSize), segmentSize * 3, segmentSize * 2, 15));
        Assert.assertNotNull(buildings);
        Assert.assertEquals("Not good vale for segment",segmentSize,20);


    }


    @Test
    public void MoneyTest(){


    }

}