package board;

import ThemePark.*;
import gui.ThemeParkGUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;


/**
 * The type Tp board.
 */
public class TPBoard extends JPanel implements MouseListener {
    private final int WIDTH = 600; //width of the park
    private final int HEIGHT = 600; //height of the park
    public int budget;
    /*
     * Building informations
     */
    public int x;
    public int y;
    public boolean canBuild; // Can we build next to the road
    public boolean canBuildOn; //Not to build an object to antoher object
    public ArrayList<Building> buildings = new ArrayList<Building>();
    public ArrayList<Guest> guests = new ArrayList<Guest>();
    public ArrayList<Worker> workers = new ArrayList<Worker>();
    public ArrayList<Connection> connections = new ArrayList<Connection>();
    public ArrayList<Building> roads = new ArrayList<Building>();

    public Timer timer;
    public int TD = 100;
    Random random = new Random();
    Color clr1 = new Color(0, 153, 0);
    Image img = null;
    JFrame frame = new JFrame();
    private int maxGuests = 1;
    private int randomTimer;
    private int guestNumber = 0;
    private int segmentSize = 20; //size of one grid
    private int stepsPerSegment = 5;
    private boolean putRoad = false;
    private int roadNumber = 1;

    public TPBoard() throws IOException {
        this.addMouseListener(this);
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.startGame();
    }

    public void startGame() {
        randomTimer = ThreadLocalRandom.current().nextInt(500, 1000);
        Building starterRoad = new Building("ROAD", 0, 0, 60, 80, segmentSize, segmentSize);
        buildings.add(starterRoad);
        roads.add(starterRoad);

        /*Building starterRoad2 = new Building("ROAD", 0, 0, 60, 100, segmentSize, segmentSize);
        buildings.add(starterRoad2);

        connections.add(new Connection(new Pair(60, 80), new Pair(60, 100)));*/

        System.out.println("szia");
        timer = new Timer(TD, (ActionEvent e) -> {
            setClosestPointsToGames();
            changeMoodByGeneralEquipment();
            generateGuest();
            generateWorker();
            moveCleaner();
            moveGuest();
            reduceConstructionTime();
            //if (putRoad && buildings.get(buildings.size() - 1).getBuildingsImages().equals("ROAD")) {
                addEdge();

            //}
            //putRoad = false;
            repaint();
        });
        timer.start();
    }

    //Graph logic
    /*public void addEdge() {
        connections.clear();
        int connectionIndex = 1;
        int previousX = 60;
        int previousY = 80;

        if (roadNumber >= 2) {
            for (int i = 0; i < buildings.size() - 1; i++) {
                if (buildings.get(i).getBuildingsImages().equals("ROAD")) {
                    if(buildings.get(i).getLocation_X() - segmentSize != previousX  && buildings.get(i).getLocation_Y() != previousY)
                    connections.add(new Connection(new Pair(buildings.get(i).getLocation_X(), buildings.get(i).getLocation_Y()), new Pair(buildings.get(i + 1).getLocation_X(), buildings.get(i + 1).getLocation_Y())));
                    previousX = buildings.get(i).getLocation_X();
                    previousY = buildings.get(i).getLocation_Y();
                    connections.get(i).setIndex(connectionIndex);
                    connectionIndex++;
                }
            }
        }

        for (int i = 0; i < connections.size(); i++) {
            System.out.println(i + 1 + ". kapcsolat:  (" + connections.get(i).getFirst().getX() + ", " + connections.get(i).getFirst().getY() + ")  (" + connections.get(i).getSecond().getX() + ", " + connections.get(i).getSecond().getY() + ")");
        }
    }*/

    public void addEdge() {
        connections.clear();
        for (int i = 0; i < roads.size() - 1; i += segmentSize) {
            int j = i + segmentSize;
            int k = 0;
            if (i == roads.get(k).getLocation_X() && (j - segmentSize) == roads.get(k).getLocation_Y()
                && i == roads.get(k).getLocation_X() && j == roads.get(k).getLocation_Y()) {
                connections.add(new Connection(new Pair(i, j - segmentSize), new Pair(i, j)));
                System.out.println("beleraktam");
            }
            if (j-segmentSize == roads.get(k).getLocation_X() && i == roads.get(k).getLocation_Y()
                    && j == roads.get(k).getLocation_X() && i == roads.get(k).getLocation_Y()) {
                connections.add(new Connection(new Pair(j - segmentSize, i), new Pair(j, i)));
                System.out.println("beleraktam");
            }
        }

        for (int i = 0; i < connections.size(); i++) {
            System.out.println(i + 1 + ". kapcsolat:  (" + connections.get(i).getFirst().getX() + ", " + connections.get(i).getFirst().getY() + ")  (" + connections.get(i).getSecond().getX() + ", " + connections.get(i).getSecond().getY() + ")");
        }
    }


    /*public int[] BFS(int starter){
        LinkedList<Integer> queue = new LinkedList<Integer>();

    }*/

    public boolean isItRoad(int x, int y) {
        for (int j = 0; j < buildings.size(); j++) {
            if (buildings.get(j).getBuildingsImages().equals("ROAD")) {
                if (x == buildings.get(j).getLocation_X()
                        && y == buildings.get(j).getLocation_Y()) {
                    return true;
                }
            }
        }
        return false;
    }

    public void reduceConstructionTime() {
        for (int i = 0; i < buildings.size(); i++) {
            if (buildings.get(i).getConstructionTime() >= 0) {
                buildings.get(i).setConstructionTime((buildings.get(i).getConstructionTime() - 0.1));
                System.out.println();
            }

            if (buildings.get(i).getConstructionTime() <= 0) {
                if (buildings.get(i).getBuildingsImages().equals("slide_underconstruction")) {
                    buildings.get(i).setBuildingsImages("SLIDE");
                }
                if (buildings.get(i).getBuildingsImages().equals("waterpark_underconstruction")) {
                    buildings.get(i).setBuildingsImages("WATERPARK");
                }
                if (buildings.get(i).getBuildingsImages().equals("rollercoaster_underconstruction")) {
                    buildings.get(i).setBuildingsImages("rollercoaster");
                }
                if (buildings.get(i).getBuildingsImages().equals("wheel_underconstruction")) {
                    buildings.get(i).setBuildingsImages("WHEEL");
                }
                if (buildings.get(i).getBuildingsImages().equals("train_underconstruction")) {
                    buildings.get(i).setBuildingsImages("TRAIN");
                }
            }
        }
    }

    public boolean checkRoad(int x, int y) {                 //amig nem egész szám a segment size, addig nem értünk a szélére, tehát léptetjük
        if (x % segmentSize != 0 || y % segmentSize != 0) {   // check for whole segment size stepped
            return true;                                    // if not continue stepping the same segment
        }
        for (int j = 0; j < buildings.size(); j++) {
            if (buildings.get(j).getBuildingsImages().equals("ROAD")) {
                if (x == buildings.get(j).getLocation_X()
                        && y == buildings.get(j).getLocation_Y()) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean testDirection(int x, int y, int direction) {          //a megadott irányba(direction) van-e út, tehát a következő négyzet út-e
        switch (direction) {
            case 0:
                if (checkRoad(x + segmentSize, y)) {
                    return true;          //jobbra
                }
                break;
            case 1:
                if (checkRoad(x, y + segmentSize)) {
                    return true;
                }
                break;
            case 2:
                if (checkRoad(x - segmentSize, y)) {
                    return true;
                }
                break;
            case 3:
                if (checkRoad(x, y - segmentSize)) {
                    return true;
                }
                break;
        }
        return false;
    }

    public int checkIntersection(int x, int y, int originalDirection, int direction1, int direction2) { //van-e kereszteződés ebben a pontban és ha igen véletlenszerűen választ irányt

        if (x % segmentSize != 0 || y % segmentSize != 0) {   // csak a segmentSize végén teszteli az irányt, addig tartja az irányt
            return originalDirection;
        }

        Random random = new Random();
        int r = random.nextInt(2);
        if (r % 2 == 1) {
            return originalDirection;
        }
        int r2 = random.nextInt(2);
        if (r2 % 2 == 0) {                                // ha páros a szám, akkor az első utvonalat teszteli(pl. jobbra)
            if (testDirection(x, y, direction1)) {
                return direction1;
            } else {
                if (testDirection(x, y, direction2)) {
                    return direction2;
                }
            }
        } else {                                           //ha páratlan a szám, akkor az első utvonalat teszteli(pl. balra)
            if (testDirection(x, y, direction2)) {
                return direction2;
            } else {
                if (testDirection(x, y, direction1)) {
                    return direction1;
                }
            }
        }
        return originalDirection;                       //ha egyik iárnyba se lehet haladni, return az eredeti számot
    }

    public void doStep(int guest, int Direction, int stepLength) {                  // itt léptetjük a a guestet az adott(direction) irányba egy stepLengthet
        switch (Direction) {
            case 0: {
                guests.get(guest).setLocation_X(guests.get(guest).getLocation_X() + stepLength);
            }
            break;
            case 1: {
                guests.get(guest).setLocation_Y(guests.get(guest).getLocation_Y() + stepLength);
            }
            break;
            case 2: {
                guests.get(guest).setLocation_X(guests.get(guest).getLocation_X() - stepLength);
            }
            break;
            default: {
                guests.get(guest).setLocation_Y(guests.get(guest).getLocation_Y() - stepLength);
            }
            break;
        }
    }

    public void moveOneStep(int iGuest, int actualDirection, int crossDirection1, int crossDirection2, int segmentSizeX, int segmentSizeY) {
// iGuest = i-edik Guest, crossDirection-ok a keresztező útvonalak, segmentSizeX-Y-t aszerint növeljük, hogy merre lép a guest
        if (checkRoad(guests.get(iGuest).getLocation_X() + segmentSizeX, guests.get(iGuest).getLocation_Y() + segmentSizeY)) {
            int tempDirection = checkIntersection(guests.get(iGuest).getLocation_X(), guests.get(iGuest).getLocation_Y(), actualDirection, crossDirection1, crossDirection2);
            if (tempDirection == actualDirection) {
                doStep(iGuest, actualDirection, segmentSize / stepsPerSegment);
            } else {
                guests.get(iGuest).setDirection(tempDirection);
            }

        } else {
            Random random = new Random();
            int r;
            do {
                r = random.nextInt(4);
            } while (r == actualDirection);
            guests.get(iGuest).setDirection(r);
        }
    }

    public void moveGuest() {           //guest léptetése iránytól függően
        for (int i = 0; i < guests.size(); i++) {
            switch (guests.get(i).getDirection()) {
                case 0: {
                    moveOneStep(i, 0, 1, 3, segmentSize, 0);  //jobbra
                }
                break;
                case 1: {
                    moveOneStep(i, 1, 0, 2, 0, segmentSize);  //lefele
                }
                break;
                case 2: {
                    moveOneStep(i, 2, 1, 3, -segmentSize, 0); //balra
                }
                break;
                default: {
                    moveOneStep(i, 3, 0, 2, 0, -segmentSize); //felfele
                }
                break;

            }
        }
    }

    //move Cleaner functions
    public void doStepCleaner(int cleaner, int Direction, int stepLength) {                  // itt léptetjük a a guestet az adott(direction) irányba egy stepLengthet
        switch (Direction) {
            case 0: {
                workers.get(cleaner).setLocation_X(workers.get(cleaner).getLocation_X() + stepLength);
            }
            break;
            case 1: {
                workers.get(cleaner).setLocation_Y(workers.get(cleaner).getLocation_Y() + stepLength);
            }
            break;
            case 2: {
                workers.get(cleaner).setLocation_X(workers.get(cleaner).getLocation_X() - stepLength);
            }
            break;
            default: {
                workers.get(cleaner).setLocation_Y(workers.get(cleaner).getLocation_Y() - stepLength);
            }
            break;
        }
    }

    public void moveOneStepCleaner(int iCleaner, int actualDirection, int crossDirection1, int crossDirection2, int segmentSizeX, int segmentSizeY) {
        if (checkRoad(workers.get(iCleaner).getLocation_X() + segmentSizeX, workers.get(iCleaner).getLocation_Y() + segmentSizeY)) {
            int tempDirection = checkIntersection(workers.get(iCleaner).getLocation_X(), workers.get(iCleaner).getLocation_Y(), actualDirection, crossDirection1, crossDirection2);
            if (tempDirection == actualDirection) {
                doStepCleaner(iCleaner, actualDirection, segmentSize / stepsPerSegment);
            } else {
                workers.get(iCleaner).setDirection(tempDirection);
            }

        } else {
            Random random = new Random();
            int r;
            do {
                r = random.nextInt(4);
            } while (r == actualDirection);
            workers.get(iCleaner).setDirection(r);
        }
    }

    public void moveCleaner() {
        for (int i = 0; i < workers.size(); i++) {
            if (workers.get(i).getPersonImages().equals("cleaner")) {
                switch (workers.get(i).getDirection()) {
                    case 0: {
                        moveOneStepCleaner(i, 0, 1, 3, segmentSize, 0);
                    }
                    break;
                    case 1: {
                        moveOneStepCleaner(i, 1, 0, 2, 0, segmentSize);
                    }
                    break;
                    case 2: {
                        moveOneStepCleaner(i, 2, 1, 3, -segmentSize, 0);
                    }
                    break;
                    default: {
                        moveOneStepCleaner(i, 3, 0, 2, 0, -segmentSize);
                    }
                    break;

                }
            }
        }
    }

    public void generateGuest() {
        Random random = new Random();
        int r = random.nextInt(100);
        if (guestNumber < maxGuests) {
            if (r % 10 == 0) {
                guests.add(new Guest("guest", 60, 80, segmentSize, segmentSize));
                guestNumber++;

            }
        }
    }

    public void generateWorker() {
        if (ThemeParkGUI.selected_ge.equals(EGeneralEquipment.CLEANER)) {
            workers.add(new Worker("cleaner", 60, 80, segmentSize, segmentSize));
            ThemeParkGUI.selected_ge = EGeneralEquipment.NOTHING;
        } else if (ThemeParkGUI.selected_ge.equals(EGeneralEquipment.MAINTENANCE)) {
            workers.add(new Worker("maintenance", 60, 80, segmentSize, segmentSize));
            ThemeParkGUI.selected_ge = EGeneralEquipment.NOTHING;
        }
    }

    public double coordinateDistance(int x1, int x2, int y1, int y2) {      //pitagorasz tétel
        double first = Math.abs(x1 - x2);
        double second = Math.abs(y1 - y2);
        double distance = Math.sqrt(Math.pow(first, 2) + Math.pow(second, 2));
        return distance;
    }

    public void setClosestPointsToGames() {
        for (int i = 0; i < buildings.size(); i++) {
            if (!buildings.get(i).getBuildingsImages().equals("ROAD")) {
                double min = 100000.0;
                for (int j = 0; j < buildings.size(); j++) {
                    if (buildings.get(j).getBuildingsImages().equals("ROAD")) {
                        if (min > coordinateDistance(buildings.get(i).getLocation_X(), buildings.get(j).getLocation_X(), buildings.get(i).getLocation_Y(), buildings.get(j).getLocation_Y())) {
                            {
                                min = coordinateDistance(buildings.get(i).getLocation_X(), buildings.get(j).getLocation_X(), buildings.get(i).getLocation_Y(), buildings.get(j).getLocation_Y());
                                buildings.get(i).setClosestPoint_X(buildings.get(j).getLocation_X());
                                buildings.get(i).setClosestPoint_Y(buildings.get(j).getLocation_Y());
                            }
                        }
                    }
                }
            }
        }
    }

    public void changeMoodByGeneralEquipment() {            //ha elmennek bokor vagy fa mellett a guestek, növeljük a kedvüket
        for (int i = 0; i < guests.size(); i++) {
            for (int j = 0; j < buildings.size(); ++j) {
                if (buildings.get(j).getLocation_X() + segmentSize == guests.get(i).getLocation_X()
                        && buildings.get(j).getLocation_Y() == guests.get(i).getLocation_Y()
                        || buildings.get(j).getLocation_X() - segmentSize == guests.get(i).getLocation_X()
                        && buildings.get(j).getLocation_Y() == guests.get(i).getLocation_Y()
                        || buildings.get(j).getLocation_X() == guests.get(i).getLocation_X()
                        && buildings.get(j).getLocation_Y() == guests.get(i).getLocation_Y() + segmentSize
                        || buildings.get(j).getLocation_X() == guests.get(i).getLocation_X()
                        && buildings.get(j).getLocation_Y() == guests.get(i).getLocation_Y() - segmentSize) {
                    if (buildings.get(j).getBuildingsImages().equals("BUSH")) {
                        guests.get(i).setMood(guests.get(i).getMood() + 1);
                    }
                }
                if (buildings.get(j).getLocation_X() + segmentSize == guests.get(i).getLocation_X()
                        && buildings.get(j).getLocation_Y() == guests.get(i).getLocation_Y()
                        || buildings.get(j).getLocation_X() - segmentSize == guests.get(i).getLocation_X()
                        && buildings.get(j).getLocation_Y() == guests.get(i).getLocation_Y()
                        || buildings.get(j).getLocation_X() == guests.get(i).getLocation_X()
                        && buildings.get(j).getLocation_Y() == guests.get(i).getLocation_Y() + segmentSize
                        || buildings.get(j).getLocation_X() == guests.get(i).getLocation_X()
                        && buildings.get(j).getLocation_Y() == guests.get(i).getLocation_Y() - segmentSize
                        || buildings.get(j).getLocation_X() + segmentSize * 2 == guests.get(i).getLocation_X()
                        && buildings.get(j).getLocation_Y() == guests.get(i).getLocation_Y()
                        || buildings.get(j).getLocation_X() - segmentSize * 2 == guests.get(i).getLocation_X()
                        && buildings.get(j).getLocation_Y() == guests.get(i).getLocation_Y()
                        || buildings.get(j).getLocation_X() == guests.get(i).getLocation_X()
                        && buildings.get(j).getLocation_Y() == guests.get(i).getLocation_Y() + segmentSize * 2
                        || buildings.get(j).getLocation_X() == guests.get(i).getLocation_X()
                        && buildings.get(j).getLocation_Y() == guests.get(i).getLocation_Y() - segmentSize * 2) {
                    if (buildings.get(j).getBuildingsImages().equals("TREE")) {
                        guests.get(i).setMood(guests.get(i).getMood() + 1);
                    }
                }
                if (buildings.get(j).getLocation_X() + segmentSize == guests.get(i).getLocation_X()
                        && buildings.get(j).getLocation_Y() == guests.get(i).getLocation_Y()
                        || buildings.get(j).getLocation_X() - segmentSize == guests.get(i).getLocation_X()
                        && buildings.get(j).getLocation_Y() == guests.get(i).getLocation_Y()
                        || buildings.get(j).getLocation_X() == guests.get(i).getLocation_X()
                        && buildings.get(j).getLocation_Y() == guests.get(i).getLocation_Y() + segmentSize
                        || buildings.get(j).getLocation_X() == guests.get(i).getLocation_X()
                        && buildings.get(j).getLocation_Y() == guests.get(i).getLocation_Y() - segmentSize) {
                    if (buildings.get(j).getBuildingsImages().equals("BIN")) {
                        guests.get(i).setMood(guests.get(i).getMood() - 1);
                    }
                }
            }
            //System.out.println(guests.get(i).getMood());
        }
    }

    /**
     * @param mouse_X x coordinate of clicked mouse
     * @param mouse_Y y coordinate of clicked mouse
     * @return does the building near the road
     * <br>
     * Checks if a road near if you try to make a building.
     * Input mause X coordiante, and mause Y coordianate
     * <br>
     * return true if you can build
     * return false if no road near
     */
    public boolean PointIsWithinCircle(double mouse_X, double mouse_Y) {
        boolean joe = false;

        for (int i = 0; i < buildings.size(); i++) {

            if (buildings.get(i).getBuildingsImages().equals("ROAD")) {

                int pointToCheckX = buildings.get(i).getLocation_X();
                int pointToCheckY = buildings.get(i).getLocation_Y();

                //rendgeteg if 10 építmény ;->
                //1. if BUSHRA
                if (ThemeParkGUI.selected_ge.equals(EGeneralEquipment.BUSH)) {
                    double circleRadius_A = (double) segmentSize * 1.5;
                    double circleRadius_B = (double) segmentSize * 1.5;

                    if ((Math.pow(pointToCheckX + segmentSize / 2 - mouse_X, 2) + Math.pow(pointToCheckY + segmentSize / 2 - mouse_Y, 2)) <= (Math.pow(circleRadius_A, 2)) && (Math.pow(pointToCheckX + segmentSize / 2 - mouse_X, 2) + Math.pow(pointToCheckY + segmentSize / 2 - mouse_Y, 2)) <= (Math.pow(circleRadius_B, 2))) {
                        joe = true;
                        break;
                    } else {
                        joe = false;
                    }
                }

                //2. if BINre
                if (ThemeParkGUI.selected_ge.equals(EGeneralEquipment.BIN)) {
                    double circleRadius_A = (double) segmentSize * 1.5;
                    double circleRadius_B = (double) segmentSize * 1.5;

                    if ((Math.pow(pointToCheckX + segmentSize / 2 - mouse_X, 2) + Math.pow(pointToCheckY + segmentSize / 2 - mouse_Y, 2)) <= (Math.pow(circleRadius_A, 2)) && (Math.pow(pointToCheckX + segmentSize / 2 - mouse_X, 2) + Math.pow(pointToCheckY + segmentSize / 2 - mouse_Y, 2)) <= (Math.pow(circleRadius_B, 2))) {
                        joe = true;
                        break;
                    } else {
                        joe = false;
                    }
                }


                //3. if TREEre
                if (ThemeParkGUI.selected_ge.equals(EGeneralEquipment.TREE)) {
                    double circleRadius_A = (double) segmentSize * 2 * 1.5;
                    double circleRadius_B = (double) segmentSize * 2 * 1.5;

                    if ((Math.pow(pointToCheckX + segmentSize - mouse_X, 2) + Math.pow(pointToCheckY + segmentSize - mouse_Y, 2)) <= (Math.pow(circleRadius_A, 2)) && (Math.pow(pointToCheckX + segmentSize - mouse_X, 2) + Math.pow(pointToCheckY + segmentSize - mouse_Y, 2)) <= (Math.pow(circleRadius_B, 2))) {
                        joe = true;
                        break;
                    } else {
                        joe = false;
                    }
                }

                //4. if ROLLERCOASTEREe
                if (ThemeParkGUI.selected_ge.equals(EGeneralEquipment.ROLLERCOASTER)) {
                    double circleRadius_A = (double) segmentSize * 6 * 1.5;
                    double circleRadius_B = (double) segmentSize * 4 * 1.5;

                    if ((Math.pow(pointToCheckX + segmentSize * 6 / 2 - mouse_X, 2) + Math.pow(pointToCheckY + segmentSize * 4 / 2 - mouse_Y, 2)) <= (Math.pow(circleRadius_A, 2)) && (Math.pow(pointToCheckX + segmentSize * 6 / 2 - mouse_X, 2) + Math.pow(pointToCheckY + segmentSize * 4 / 2 - mouse_Y, 2)) <= (Math.pow(circleRadius_B, 2))) {
                        joe = true;
                        break;
                    } else {
                        joe = false;
                    }
                }


                //5. if TRAIN
                if (ThemeParkGUI.selected_ge.equals(EGeneralEquipment.TRAIN)) {
                    double circleRadius_A = (double) segmentSize * 4 * 1.5;
                    double circleRadius_B = (double) segmentSize * 4 * 1.5;

                    if ((Math.pow(pointToCheckX + segmentSize * 4 / 2 - mouse_X, 2) + Math.pow(pointToCheckY + segmentSize * 4 / 2 - mouse_Y, 2)) <= (Math.pow(circleRadius_A, 2)) && (Math.pow(pointToCheckX + segmentSize * 4 / 2 - mouse_X, 2) + Math.pow(pointToCheckY + segmentSize * 4 / 2 - mouse_Y, 2)) <= (Math.pow(circleRadius_B, 2))) {
                        joe = true;
                        break;
                    } else {
                        joe = false;
                    }
                }


                //6. if WATERPARK
                if (ThemeParkGUI.selected_ge.equals(EGeneralEquipment.WATERPARK)) {
                    double circleRadius_A = (double) segmentSize * 6 * 1.5;
                    double circleRadius_B = (double) segmentSize * 4 * 1.5;

                    if ((Math.pow(pointToCheckX + segmentSize * 6 / 2 - mouse_X, 2) + Math.pow(pointToCheckY + segmentSize * 4 / 2 - mouse_Y, 2)) <= (Math.pow(circleRadius_A, 2)) && (Math.pow(pointToCheckX + segmentSize * 6 / 2 - mouse_X, 2) + Math.pow(pointToCheckY + segmentSize * 4 / 2 - mouse_Y, 2)) <= (Math.pow(circleRadius_B, 2))) {
                        joe = true;
                        break;
                    } else {
                        joe = false;
                    }
                }

                //7. if WHEEL
                if (ThemeParkGUI.selected_ge.equals(EGeneralEquipment.WHEEL)) {
                    double circleRadius_A = (double) segmentSize * 6 * 1.5;
                    double circleRadius_B = (double) segmentSize * 6 * 1.5;

                    if ((Math.pow(pointToCheckX + segmentSize * 6 / 2 - mouse_X, 2) + Math.pow(pointToCheckY + segmentSize * 6 / 2 - mouse_Y, 2)) <= (Math.pow(circleRadius_A, 2)) && (Math.pow(pointToCheckX + segmentSize * 6 / 2 - mouse_X, 2) + Math.pow(pointToCheckY + segmentSize * 6 / 2 - mouse_Y, 2)) <= (Math.pow(circleRadius_B, 2))) {
                        joe = true;
                        break;
                    } else {
                        joe = false;
                    }
                }

                //8. if SLIDE
                if (ThemeParkGUI.selected_ge.equals(EGeneralEquipment.SLIDE)) {
                    double circleRadius_A = (double) segmentSize * 6 * 1.5;
                    double circleRadius_B = (double) segmentSize * 4 * 1.5;

                    if ((Math.pow(pointToCheckX + segmentSize * 4 / 2 - mouse_X, 2) + Math.pow(pointToCheckY + segmentSize * 4 / 2 - mouse_Y, 2)) <= (Math.pow(circleRadius_A, 2)) && (Math.pow(pointToCheckX + segmentSize * 4 / 2 - mouse_X, 2) + Math.pow(pointToCheckY + segmentSize * 4 / 2 - mouse_Y, 2)) <= (Math.pow(circleRadius_B, 2))) {
                        joe = true;
                        break;
                    } else {
                        joe = false;
                    }
                }

                //9. if RESTAURANT
                if (ThemeParkGUI.selected_ge.equals(EGeneralEquipment.RESTAURANT)) {
                    double circleRadius_A = (double) segmentSize * 3 * 1.5;
                    double circleRadius_B = (double) segmentSize * 2 * 1.5;

                    if ((Math.pow(pointToCheckX + segmentSize * 3 / 2 - mouse_X, 2) + Math.pow(pointToCheckY + segmentSize * 2 / 2 - mouse_Y, 2)) <= (Math.pow(circleRadius_A, 2)) && (Math.pow(pointToCheckX + segmentSize * 3 / 2 - mouse_X, 2) + Math.pow(pointToCheckY + segmentSize * 2 / 2 - mouse_Y, 2)) <= (Math.pow(circleRadius_B, 2))) {
                        joe = true;
                        break;
                    } else {
                        joe = false;
                    }
                }
            }
        }
        return joe;
    }

    public boolean isCanBuildOn(int mouse_X, int mouse_Y) {
        boolean raepit = false;

        for (int i = 0; i < buildings.size(); i++) {
            if (buildings.get(i).getBuildingsImages().equals("ROAD")) {
                if (mouse_X - mouse_X % segmentSize == buildings.get(i).getLocation_X() && mouse_Y - mouse_Y % segmentSize == buildings.get(i).getLocation_Y()) {
                    raepit = false;
                    break;
                } else {
                    raepit = true;
                }
            }
        }

        return raepit;

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        paint(g);
    }

    @Override
    public void paint(Graphics g) {
        g.clearRect(0, 0, WIDTH, HEIGHT);
        g.setColor(clr1);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        budget = 10000;

        /*
         * Entrance
         */
        try {
            img = ImageIO.read(new File("data\\images\\entrance.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(img, segmentSize, 0, segmentSize * 5, segmentSize * 6, null);

        /*
         * Redraw images
         * Buildings
         * Manage budget
         */
        for (int i = 0; i < buildings.size(); i++) {
            try {
                img = ImageIO.read(new File("data\\images\\" + buildings.get(i).getBuildingsImages() + ".png"));
                Graphics2D g3d = (Graphics2D) g;
                g3d.drawImage(img, buildings.get(i).getLocation_X(), buildings.get(i).getLocation_Y(), buildings.get(i).getBuildingsSizesA(), buildings.get(i).getBuildingsSizesB(), null);
                budget -= buildings.get(i).getBuildPrice();
                g.setColor(Color.BLACK);
                g.fillRect(buildings.get(i).getClosestPoint_X(), buildings.get(i).getClosestPoint_Y(), segmentSize, segmentSize);
            } catch (IOException f) {
                System.out.println("error");
                f.printStackTrace();
            }
        }
        /*
         * Redraw images
         * Persons
         */
        for (int i = 0; i < guests.size(); i++) {
            try {
                if (guests.get(i).getMood() <= 5) {
                    guests.get(i).setPersonImages("guest_angeri");
                } else {
                    guests.get(i).setPersonImages("guest");
                }
                img = ImageIO.read(new File("data\\images\\" + guests.get(i).getPersonImages() + ".png"));
                Graphics2D g3d = (Graphics2D) g;
                g3d.drawImage(img, guests.get(i).getLocation_X(), guests.get(i).getLocation_Y(), guests.get(i).getBuildingsSizesA(), guests.get(i).getBuildingsSizesB(), null);
            } catch (IOException f) {
                System.out.println("error");
                f.printStackTrace();
            }
        }

        /*
         * Redraw images
         * Cleaners
         */
        for (int i = 0; i < workers.size(); i++) {
            try {
                img = ImageIO.read(new File("data\\images\\" + workers.get(i).getPersonImages() + ".png"));
                Graphics2D g3d = (Graphics2D) g;
                g3d.drawImage(img, workers.get(i).getLocation_X(), workers.get(i).getLocation_Y(), workers.get(i).getBuildingsSizesA(), workers.get(i).getBuildingsSizesB(), null);
                budget -= workers.get(i).getSalary();
            } catch (IOException f) {
                System.out.println("error");
                f.printStackTrace();
            }
        }

    }

    /**
     * Mouselistener for everything the user can do with mouse
     *
     * @param e
     */
    @Override
    public void mousePressed(MouseEvent e) {

        /*
         * Game deletion
         */
        x = e.getX();
        y = e.getY();

        int j = 0;
        canBuildOn = true;
        while (j < buildings.size()) {
            if (!buildings.get(j).getBuildingsImages().equals("ROAD") && (buildings.get(j).getLocation_X() < x && x < (buildings.get(j).sumXA()) && (buildings.get(j).getLocation_Y() < y && y < (buildings.get(j).sumYB())))) {
                ThemeParkGUI.selected_ge = EGeneralEquipment.NOTHING;
                int reply = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete " + buildings.get(j).getBuildingsImages() + " ?", "Confirm", JOptionPane.YES_NO_OPTION);
                if (reply == JOptionPane.YES_OPTION) {
                    buildings.remove(j);
                }
            }
            j++;
        }
        repaint();

        /*
         * If selected game not nothing (empty variable) not cleaner or not maintenance, check what kind of GeneralEquipment is selected.
         */
        if (!ThemeParkGUI.selected_ge.equals(EGeneralEquipment.NOTHING)
                && !ThemeParkGUI.selected_ge.equals(EGeneralEquipment.CLEANER)
                && !ThemeParkGUI.selected_ge.equals(EGeneralEquipment.MAINTENANCE)) {
            if (ThemeParkGUI.selected_ge.equals(EGeneralEquipment.ROAD)) {  // If the selected GeneralEquipment is road
                // builds road
                x = e.getX();
                y = e.getY();

                System.out.println(x + " " + y);
                canBuild = false;

                /*
                 * Road can build built only next to another road
                 */
                for (int i = 0; i < buildings.size(); ++i) {
                    if (!canBuild) {
                        if (buildings.get(i).getBuildingsImages().equals("ROAD")) {
                            if ((x + segmentSize) - ((x + segmentSize) % segmentSize) == buildings.get(i).getLocation_X()  //jobbra
                                    && (y - (y % segmentSize)) == buildings.get(i).getLocation_Y()
                                    || x - (x % segmentSize) == buildings.get(i).getLocation_X()                            //lefele
                                    && ((y + segmentSize) - ((y + segmentSize) % segmentSize)) == buildings.get(i).getLocation_Y()
                                    || (x - segmentSize) - ((x - segmentSize) % segmentSize) == buildings.get(i).getLocation_X()        //balra
                                    && (y - (y % segmentSize)) == buildings.get(i).getLocation_Y()
                                    || x - (x % segmentSize) == buildings.get(i).getLocation_X()            //felfele
                                    && ((y - segmentSize) - ((y - segmentSize) % segmentSize)) == buildings.get(i).getLocation_Y()) {
                                canBuild = true;
                            }
                        }
                    }
                }

                /*
                  Can't build road on each other
                 */
                int i = 0;
                canBuildOn = true;
                while (i < buildings.size()) {
                    if ((buildings.get(i).getLocation_X() < x && x < (buildings.get(i).sumXA()) && (buildings.get(i).getLocation_Y() < y && y < (buildings.get(i).sumYB())))) {
                        canBuildOn = false;
                    }
                    i++;
                }

                /*
                  Can be built only if the user got enough money
                 */
                if (canBuild && canBuildOn && budget - 10 >= 0) {
                    System.out.println("UT EPULT");
                    System.out.println(x + "," + y);//these co-ords are relative to the component
                    buildings.add(new Building("ROAD", 0.0, 10, x - (x % segmentSize), y - (y % segmentSize), segmentSize, segmentSize));
                    roads.add(new Building("ROAD", 0.0, 10, x - (x % segmentSize), y - (y % segmentSize), segmentSize, segmentSize));

                    putRoad = true;
                    roadNumber++;
                } else if (budget - 10 < 0) {
                    JOptionPane.showMessageDialog(frame, "There's no enough money for ROAD");
                }
                repaint();
            }

            if (ThemeParkGUI.selected_ge.equals(EGeneralEquipment.BUSH)) {  // If the selected GeneralEquipment is bush
                x = e.getX();
                y = e.getY();

                System.out.println("BOKOR EPULT");

                /*
                 * Can be built only if the user got enough money
                 */


                if (budget - 10 >= 0 && PointIsWithinCircle(e.getX(), e.getY()) && isCanBuildOn(e.getX(), e.getY())) {
                    buildings.add(new Building("BUSH", 0.0, 10, x - (x % segmentSize), y - (y % segmentSize), segmentSize, segmentSize));
                    System.out.println(x + "," + y);//these co-ords are relative to the component

                    repaint();
                } else if (budget - 10 < 0) {
                    JOptionPane.showMessageDialog(frame, "There's no enough money for BUSH");
                } else if (!PointIsWithinCircle(e.getX(), e.getY())) {
                    JOptionPane.showMessageDialog(frame, "You can't build away from the road.");
                } else if (!isCanBuildOn(e.getX(), e.getY())) {
                    JOptionPane.showMessageDialog(frame, "You can't build on the road.");
                }
            }

            if (ThemeParkGUI.selected_ge.equals(EGeneralEquipment.BIN)) {   // If the selected GeneralEquipment is bin
                // builds bin
                x = e.getX();
                y = e.getY();

                canBuild = false;

                /*
                 * Bin can be built only next to a road
                 */
                for (int i = 0; i < buildings.size(); ++i) {
                    if (!canBuild) {
                        if (buildings.get(i).getBuildingsImages().equals("ROAD")) {
                            if ((x + segmentSize) - ((x + segmentSize) % segmentSize) == buildings.get(i).getLocation_X()
                                    && (y - (y % segmentSize)) == buildings.get(i).getLocation_Y()
                                    || x - (x % segmentSize) == buildings.get(i).getLocation_X()
                                    && ((y + segmentSize) - ((y + segmentSize) % segmentSize)) == buildings.get(i).getLocation_Y()
                                    || (x - segmentSize) - ((x - segmentSize) % segmentSize) == buildings.get(i).getLocation_X()
                                    && (y - (y % segmentSize)) == buildings.get(i).getLocation_Y()
                                    || x - (x % segmentSize) == buildings.get(i).getLocation_X()
                                    && ((y - segmentSize) - ((y - segmentSize) % segmentSize)) == buildings.get(i).getLocation_Y()) {
                                canBuild = true;
                            }
                        }
                    }
                }

                /*
                 * Can be built only if the user got enough money
                 */
                if (canBuild && (budget - 10 >= 0 && PointIsWithinCircle(e.getX(), e.getY()) && isCanBuildOn(e.getX(), e.getY()))) {
                    System.out.println("KUKA EPULT");
                    System.out.println(x + "," + y);//these co-ords are relative to the component
                    buildings.add(new Building("BIN", 0.0, 10, x - (x % segmentSize), y - (y % segmentSize), segmentSize, segmentSize));

                    repaint();
                } else if (budget - 10 < 0) {
                    JOptionPane.showMessageDialog(frame, "There's no enough money for BIN");
                } else if (!PointIsWithinCircle(e.getX(), e.getY())) {
                    JOptionPane.showMessageDialog(frame, "You can't build away from the road.");
                } else if (!isCanBuildOn(e.getX(), e.getY())) {
                    JOptionPane.showMessageDialog(frame, "You can't build on the road.");
                }

            }


            if (ThemeParkGUI.selected_ge.equals(EGeneralEquipment.TREE)) {  // If the selected GeneralEquipment is tree
                // builds tree
                x = e.getX();
                y = e.getY();

                /*
                 * Can be built only if the user got enough money
                 */
                if (budget - 10 >= 0 && PointIsWithinCircle(e.getX(), e.getY()) && isCanBuildOn(e.getX(), e.getY())) {
                    System.out.println("FA EPULT");
                    System.out.println(x + "," + y);//these co-ords are relative to the component
                    buildings.add(new Building("TREE", 0.0, 10, x - (x % segmentSize), y - (y % segmentSize), segmentSize * 2, segmentSize * 2));
                    repaint();
                } else if (budget - 10 < 0) {
                    JOptionPane.showMessageDialog(frame, "There's no enough money for TREE");
                } else if (!PointIsWithinCircle(e.getX(), e.getY())) {
                    JOptionPane.showMessageDialog(frame, "You can't build away from the road.");
                } else if (!isCanBuildOn(e.getX(), e.getY())) {
                    JOptionPane.showMessageDialog(frame, "You can't build on the road.");
                }
            }


            if (ThemeParkGUI.selected_ge.equals(EGeneralEquipment.ROLLERCOASTER)) {
                x = e.getX();
                y = e.getY();

                /*
                 * Can be built only if the user got enough money
                 */
                if (budget - 1000 >= 0 && PointIsWithinCircle(e.getX(), e.getY()) && isCanBuildOn(e.getX(), e.getY())) {
                    System.out.println("RC EPULT");
                    System.out.println(x + "," + y);//these co-ords are relative to the component
                    buildings.add(new Game("rollercoaster_underconstruction", 5.0, 1000, x - (x % segmentSize) - 3 * segmentSize, y - (y % segmentSize) - 2 * segmentSize, segmentSize * 6, segmentSize * 4, 15));
                    repaint();
                } else if (budget - 1000 < 0) {
                    JOptionPane.showMessageDialog(frame, "There's no enough money for ROLLERCOASTER");
                } else if (!PointIsWithinCircle(e.getX(), e.getY())) {
                    JOptionPane.showMessageDialog(frame, "You can't build away from the road.");
                } else if (!isCanBuildOn(e.getX(), e.getY())) {
                    JOptionPane.showMessageDialog(frame, "You can't build on the road.");
                }
            }

            if (ThemeParkGUI.selected_ge.equals(EGeneralEquipment.TRAIN)) { // If the selected GeneralEquipment is train
                // builds train
                x = e.getX();
                y = e.getY();

                /*
                 * Can be built only if the user got enough money
                 */
                if (budget - 800 >= 0 && PointIsWithinCircle(e.getX(), e.getY()) && isCanBuildOn(e.getX(), e.getY())) {
                    System.out.println("TRAIN EPULT");
                    System.out.println(x + "," + y);//these co-ords are relative to the component
                    buildings.add(new Game("train_underconstruction", 5.0, 800, x - (x % segmentSize) - 2 * segmentSize, y - (y % segmentSize) - segmentSize, segmentSize * 4, segmentSize * 4, 15));
                    repaint();
                } else if (budget - 800 < 0) {
                    JOptionPane.showMessageDialog(frame, "There's no enough money for TRAIN");
                } else if (!PointIsWithinCircle(e.getX(), e.getY())) {
                    JOptionPane.showMessageDialog(frame, "You can't build away from the road.");
                } else if (!isCanBuildOn(e.getX(), e.getY())) {
                    JOptionPane.showMessageDialog(frame, "You can't build on the road.");
                }
            }

            if (ThemeParkGUI.selected_ge.equals(EGeneralEquipment.WATERPARK)) { // If the selected GeneralEquipment is waterpark
                // builds waterpark
                x = e.getX();
                y = e.getY();

                /*
                 * Can be built only if the user got enough money
                 */
                if (budget - 1000 >= 0 && PointIsWithinCircle(e.getX(), e.getY()) && isCanBuildOn(e.getX(), e.getY())) {
                    System.out.println("WP EPULT");
                    System.out.println(x + "," + y);//these co-ords are relative to the component
                    buildings.add(new Game("waterpark_underconstruction", 5.0, 1000, x - (x % segmentSize) - 2 * segmentSize, y - (y % segmentSize) - 2 * segmentSize, segmentSize * 6, segmentSize * 4, 15));
                    repaint();
                } else if (budget - 1000 < 0) {
                    JOptionPane.showMessageDialog(frame, "There's no enough money for WATERPARK");
                } else if (!PointIsWithinCircle(e.getX(), e.getY())) {
                    JOptionPane.showMessageDialog(frame, "You can't build away from the road.");
                } else if (!isCanBuildOn(e.getX(), e.getY())) {
                    JOptionPane.showMessageDialog(frame, "You can't build on the road.");
                }
            }

            if (ThemeParkGUI.selected_ge.equals(EGeneralEquipment.WHEEL)) { // If the selected GeneralEquipment is wheel
                // builds wheel
                x = e.getX();
                y = e.getY();

                /*
                 * Can be built only if the user got enough money
                 */
                if (budget - 1500 >= 0 && canBuildOn && PointIsWithinCircle(e.getX(), e.getY()) && isCanBuildOn(e.getX(), e.getY())) {
                    System.out.println("WHEEL EPULT");
                    System.out.println(x + "," + y);//these co-ords are relative to the component
                    buildings.add(new Game("wheel_underconstruction", 5.0, 1500, x - (x % segmentSize) - 2 * segmentSize, y - (y % segmentSize) - 2 * segmentSize, segmentSize * 6, segmentSize * 6, 15));

                    repaint();
                } else if (budget - 1500 < 0) {
                    JOptionPane.showMessageDialog(frame, "There's no enough money for WHEEL");
                } else if (!PointIsWithinCircle(e.getX(), e.getY())) {
                    JOptionPane.showMessageDialog(frame, "You can't build away from the road.");
                } else if (!isCanBuildOn(e.getX(), e.getY())) {
                    JOptionPane.showMessageDialog(frame, "You can't build on the road.");
                }
            }

            if (ThemeParkGUI.selected_ge.equals(EGeneralEquipment.SLIDE)) { // If the selected GeneralEquipment is slide
                // builds slide
                x = e.getX();
                y = e.getY();

                /*
                 * Can be built only if the user got enough money
                 */
                if (budget - 800 >= 0 && PointIsWithinCircle(e.getX(), e.getY()) && isCanBuildOn(e.getX(), e.getY())) {
                    System.out.println("SLIDE EPULT");
                    System.out.println(x + "," + y);//these co-ords are relative to the component
                    buildings.add(new Game("slide_underconstruction", 5.0, 800, x - (x % segmentSize) - segmentSize, y - (y % segmentSize) - segmentSize, segmentSize * 4, segmentSize * 4, 15));
                    repaint();
                } else if (budget - 800 < 0) {
                    JOptionPane.showMessageDialog(frame, "There's no enough money for SLIDE");
                } else if (!PointIsWithinCircle(e.getX(), e.getY())) {
                    JOptionPane.showMessageDialog(frame, "You can't build away from the road.");
                } else if (!isCanBuildOn(e.getX(), e.getY())) {
                    JOptionPane.showMessageDialog(frame, "You can't build on the road.");
                }
            }

            if (ThemeParkGUI.selected_ge.equals(EGeneralEquipment.RESTAURANT)) { // If the selected GeneralEquipment is restaurant
                // builds restaurant
                x = e.getX();
                y = e.getY();

                /*
                 * Can be built only if the user got enough money
                 */
                if (budget - 600 >= 0 && PointIsWithinCircle(e.getX(), e.getY()) && isCanBuildOn(e.getX(), e.getY())) {
                    System.out.println("RESTAURANT EPULT");
                    System.out.println(x + "," + y);//these co-ords are relative to the component
                    buildings.add(new Restaurant("RESTAURANT", 0.0, 600, x - (x % segmentSize), y - (y % segmentSize), segmentSize * 3, segmentSize * 2, 15));

                    repaint();
                } else if (budget - 600 < 0) {
                    JOptionPane.showMessageDialog(frame, "There's no enough money for RESTAURANT");
                } else if (!PointIsWithinCircle(e.getX(), e.getY())) {
                    JOptionPane.showMessageDialog(frame, "You can't build away from the road.");
                } else if (!isCanBuildOn(e.getX(), e.getY())) {
                    JOptionPane.showMessageDialog(frame, "You can't build on the road.");
                }
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }


    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public int getBudget() {
        return budget;
    }


}