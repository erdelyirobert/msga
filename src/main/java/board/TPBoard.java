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
    private final int WIDTH = 700; //width of the park
    private final int HEIGHT = 700; //height of the park
    public int budget = 10000;
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
    public ArrayList<Building> roads = new ArrayList<Building>();
    public ArrayList<Trash> trashes = new ArrayList<Trash>();
    public ArrayList<Building> graphPeaks = new ArrayList<>();
    public ArrayList<Integer> shortestRoadSteps = new ArrayList<>();
    public ArrayList<Building> gamePeaks = new ArrayList<>();
    public ArrayList<Building> games = new ArrayList<>();

    public Timer timer;
    public int TD = 200;
    Random random = new Random();
    Color clr1 = new Color(0, 153, 0);
    Image img = null;
    JFrame frame = new JFrame();
    private int maxGuests = 3;
    private int randomTimer;
    private int guestNumber = 0;
    private int segmentSize = 20; //size of one grid
    private int stepsPerSegment = 2;
    public boolean shortTimerActive = false;
    public int shortTimerCounter = 5;
    private int salaryTimer = 25;
    private int targetGameTimer = 25;
    private int generateGuestTimer = 50;
    private int wrongTimer = 130;
    public int numberOfWrongGames = 0;
    public int indexOfWrongGame;

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

        timer = new Timer(TD, (ActionEvent e) -> {
            addTargetGameToGuest();
            changeMoodByGeneralEquipment();
            //wrongTimer();
            generateGuest();
            generateWorker();
            moveCleaner();
            moveGuest();
            reduceConstructionTime();
            beenToRestaurant();
            leaveTrash();
            removeLastTrash();
            cleanTrash();
            playGuest();
            workerSalary();
            moveMaintenance();
            putBackToGuest();


            for (int i = 0; i < workers.size(); i++) {
                if (workers.get(i).getPersonImages().equals("maintenance") && workers.get(i).getWorkingTimer() == 1) {
                    fixGame();
                }
            }
            repaint();
        });
        timer.start();
    }

    public void goWrong() {
        if (numberOfWrongGames == 0) {
            int i;
            if (games.size() > 0) {

                do {
                    Random random = new Random();
                    i = random.nextInt(buildings.size());
                    indexOfWrongGame = i;
                } while (buildings.get(i).getBuildingsImages().equals("ROAD"));

                if (buildings.get(i).getBuildingsImages().equals("rollercoaster")) {
                    buildings.get(i).setBuildingsImages("rollercoaster_outoforder");
                    numberOfWrongGames++;
                }
                if (buildings.get(i).getBuildingsImages().equals("SLIDE")) {
                    buildings.get(i).setBuildingsImages("slide_outoforder");
                    numberOfWrongGames++;
                }
                if (buildings.get(i).getBuildingsImages().equals("TRAIN")) {
                    buildings.get(i).setBuildingsImages("train_outoforder");
                    numberOfWrongGames++;
                }
                if (buildings.get(i).getBuildingsImages().equals("WATERPARK")) {
                    buildings.get(i).setBuildingsImages("waterpark_outoforder");
                    numberOfWrongGames++;
                }
                if (buildings.get(i).getBuildingsImages().equals("WHEEL")) {
                    buildings.get(i).setBuildingsImages("wheel_outoforder");
                    numberOfWrongGames++;
                }
            }
        }
    }

    public void wrongTimer() {
        wrongTimer--;
        if (wrongTimer == 0) {
            wrongTimer = 130;
            goWrong();
        }
    }

    public void fixGame() {
        for (int i = 0; i < graphPeaks.size(); i++) {
            for (int j = 0; j < buildings.size(); j++) {
                if (buildings.get(j).getClosestPoint_X() == graphPeaks.get(i).getLocation_X()
                        && buildings.get(j).getClosestPoint_Y() == graphPeaks.get(i).getLocation_Y()) {
                    if (buildings.get(j).getBuildingsImages().equals("rollercoaster_outoforder")) {
                        buildings.get(j).setBuildingsImages("rollercoaster");
                        gamePeaks.clear();
                        numberOfWrongGames = 0;
                    }
                    if (buildings.get(j).getBuildingsImages().equals("train_outoforder")) {
                        buildings.get(j).setBuildingsImages("TRAIN");
                        gamePeaks.clear();
                        numberOfWrongGames = 0;
                    }
                    if (buildings.get(j).getBuildingsImages().equals("slide_outoforder")) {
                        buildings.get(j).setBuildingsImages("SLIDE");
                        gamePeaks.clear();
                        numberOfWrongGames = 0;
                    }
                    if (buildings.get(j).getBuildingsImages().equals("waterpark_outoforder")) {
                        buildings.get(j).setBuildingsImages("WATERPARK");
                        gamePeaks.clear();
                        numberOfWrongGames = 0;
                    }
                    if (buildings.get(j).getBuildingsImages().equals("wheel_outoforder")) {
                        buildings.get(j).setBuildingsImages("WHEEL");
                        gamePeaks.clear();
                        numberOfWrongGames = 0;
                    }
                }
            }
        }
    }

    public void moveMaintenance() {
        for (int i = 0; i < workers.size(); i++) {
            if (workers.get(i).getPersonImages().equals("maintenance") && !workers.get(i).isWorking()) {
                switch (workers.get(i).getStepForwardOnPathDirection().get(workers.get(i).getStepNo())) {
                    case 0:
                        workers.get(i).setLocation_X(workers.get(i).getLocation_X() + segmentSize); // jobbra
                        break;

                    case 1:
                        workers.get(i).setLocation_Y(workers.get(i).getLocation_Y() + segmentSize); //lefele
                        break;

                    case 2:
                        workers.get(i).setLocation_X(workers.get(i).getLocation_X() - segmentSize); //balra
                        break;

                    default:
                        workers.get(i).setLocation_Y(workers.get(i).getLocation_Y() - segmentSize); //felfele
                        break;
                }
                workers.get(i).setRemainSteps(workers.get(i).getRemainSteps() - 1);
                if (workers.get(i).getRemainSteps() == 0) {
                    workers.get(i).setStepNo(workers.get(i).getStepNo() + 1);
                    if (workers.get(i).getStepNo() >= workers.get(i).getStepForwardOnPathStep().size()) {
                        if (workers.get(i).getLocation_X() == buildings.get(0).getLocation_X() && workers.get(i).getLocation_Y() == buildings.get(0).getLocation_Y()) {
                            workers.remove(i);

                        } else {
                            workers.get(i).setInMaintenance(true);
                            shortestRoadSteps = workers.get(i).reverseArrayList();
                            convertIndexesToSteps(i);
                        }
                    } else {
                        workers.get(i).setRemainSteps(workers.get(i).getStepForwardOnPathStep().get(workers.get(i).getStepNo()));
                    }
                }
            }
        }
    }

    public void convertIndexesToSteps(int index) {
        int direction, steps;
        ArrayList<Integer> Directions = new ArrayList<>();
        ArrayList<Integer> Steps = new ArrayList<>();

        for (int i = 0; i < shortestRoadSteps.size() - 1; i++) {
            int x = graphPeaks.get(shortestRoadSteps.get(i)).getLocation_X();
            int y = graphPeaks.get(shortestRoadSteps.get(i)).getLocation_Y();
            int x1 = graphPeaks.get(shortestRoadSteps.get(i + 1)).getLocation_X();
            int y1 = graphPeaks.get(shortestRoadSteps.get(i + 1)).getLocation_Y();
            if (x < x1 && y == y1) {      //jobbra
                direction = 0;
                steps = Math.abs(x1 - x) / segmentSize;
            } else if (x > x1 && y == y1) {     //balra
                direction = 2;
                steps = Math.abs(x1 - x) / segmentSize;
            } else if (x == x1 && y < y1) {        //lefele
                direction = 1;
                steps = Math.abs(y1 - y) / segmentSize;
            } else {                               //felfele
                direction = 3;
                steps = Math.abs(y1 - y) / segmentSize;
            }
            Directions.add(direction);
            Steps.add(steps);
        }
        workers.get(index).setStepForwardOnPathDirection(Directions);
        workers.get(index).setStepForwardOnPathStep(Steps);
        workers.get(index).setRemainSteps(Steps.get(0));
    }

    public int getDistance(int i, int j) {
        int x1 = graphPeaks.get(i).getLocation_X();
        int y1 = graphPeaks.get(i).getLocation_Y();
        int x2 = graphPeaks.get(j).getLocation_X();
        int y2 = graphPeaks.get(j).getLocation_Y();
        int distance = 0;

        if (x1 != x2 && y1 != y2 || x1 == x2 && y1 == y2) {
            return 0;
        }

        if (x1 == x2 && y2 > y1) {
            for (int y = y1 + segmentSize; y <= y2; y += segmentSize) {
                if (isItRoad(x1, y)) {
                    distance++;
                } else {
                    return 0;
                }
            }
        } else if (x1 == x2 && y2 < y1) {
            for (int y = y1 - segmentSize; y >= y2; y -= segmentSize) {
                if (isItRoad(x1, y)) {
                    distance++;
                } else {
                    return 0;
                }
            }

        } else if (y1 == y2 && x2 > x1) {
            for (int x = x1 + segmentSize; x <= x2; x += segmentSize) {
                if (isItRoad(x, y1)) {
                    distance++;
                } else {
                    return 0;
                }
            }

        } else if (y1 == y2 && x2 < x1) {
            for (int x = x1 - segmentSize; x >= x2; x -= segmentSize) {
                if (isItRoad(x, y1)) {
                    distance++;
                } else {
                    return 0;
                }
            }
        }
        return distance;
    }

    public void givePeaksToGraph(int graph[][]) {
        for (int i = 0; i < graphPeaks.size(); i++) {
            System.out.println(i + " x: " + graphPeaks.get(i).getLocation_X() + " y: " + graphPeaks.get(i).getLocation_Y());
        }
        for (int i = 0; i < graphPeaks.size(); i++) {
            for (int j = 0; j < graphPeaks.size(); j++) {
                graph[i][j] = getDistance(i, j);
                System.out.print(graph[i][j] + " ");
            }
            System.out.println("");
        }
    }

    public void createGraphPeaks() {                        //csúcsok hozzáadása
        graphPeaks.clear();
        for (int i = 0; i < buildings.size(); i++) {
            if (buildings.get(i).getBuildingsImages().equals("ROAD")) {
                if (isItRoad(buildings.get(i).getLocation_X() + segmentSize, buildings.get(i).getLocation_Y()) //jobbra és lefele
                        && isItRoad(buildings.get(i).getLocation_X(), buildings.get(i).getLocation_Y() + segmentSize)

                        || isItRoad(buildings.get(i).getLocation_X() + segmentSize, buildings.get(i).getLocation_Y()) //jobbra és felfele
                        && isItRoad(buildings.get(i).getLocation_X(), buildings.get(i).getLocation_Y() - segmentSize)

                        || isItRoad(buildings.get(i).getLocation_X() - segmentSize, buildings.get(i).getLocation_Y()) //balra és lefele
                        && isItRoad(buildings.get(i).getLocation_X(), buildings.get(i).getLocation_Y() - segmentSize)

                        || isItRoad(buildings.get(i).getLocation_X() - segmentSize, buildings.get(i).getLocation_Y()) //balra és felfele
                        && isItRoad(buildings.get(i).getLocation_X(), buildings.get(i).getLocation_Y() + segmentSize)

                        || onlyOneNeighbourRoad(buildings.get(i).getLocation_X(), buildings.get(i).getLocation_Y())) {

                    graphPeaks.add(buildings.get(i));
                }
            }
        }

        String word = "outoforder";

        if (buildings.get(indexOfWrongGame).getBuildingsImages().contains(word)) {
            gamePeaks.add(buildings.get(indexOfWrongGame));
        }

        if (gamePeaks.size() > 0) {
            Building temp = new Building(gamePeaks.get(0).getBuildingsImages(),
                    gamePeaks.get(0).getConstructionTime(),
                    gamePeaks.get(0).getBuildPrice(),
                    gamePeaks.get(0).getLocation_X(),
                    gamePeaks.get(0).getLocation_Y(),
                    gamePeaks.get(0).getBuildingsSizesA(),
                    gamePeaks.get(0).getBuildingsSizesB());

            temp.setLocation_X(gamePeaks.get(0).getClosestPoint_X());
            temp.setLocation_Y(gamePeaks.get(0).getClosestPoint_Y());
            graphPeaks.add(temp);
        }
    }

    public boolean onlyOneNeighbourRoad(int x, int y) {
        int db = 0;
        if (isItRoad(x + segmentSize, y)) { //jobbra
            db++;
        }

        if (isItRoad(x - segmentSize, y)) { //balra
            db++;
        }

        if (isItRoad(x, y + segmentSize)) { //lefele
            db++;
        }

        if (isItRoad(x, y - segmentSize)) { //felfele
            db++;
        }
        return db == 1;
    }

    public boolean isItRoad(int x, int y) {                 //amig nem egész szám a segment size, addig nem értünk a szélére, tehát léptetjük
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

    public void workerSalary() {
        salaryTimer--;

        if (salaryTimer == 0) {
            budget -= workers.size() * 10;
            salaryTimer = 25;
        }
    }

    public void raiseMoney() {
        for (int i = 0; i < guests.size(); i++) {
            for (int j = 0; j < buildings.size(); j++) {
                if (guests.get(i).getInGame()) {
                    budget += buildings.get(j).getUsagePrice();
                }
            }
        }
    }

    public void playGuest() {
        for (int i = 0; i < guests.size(); i++) {
            for (int j = 0; j < buildings.size(); j++) {
                if (!buildings.get(j).getBuildingsImages().equals("ROAD") || !buildings.get(j).getBuildingsImages().contains("outoforder")) {
                    if (guests.get(i).getLocation_X() == buildings.get(j).getClosestPoint_X()
                            && guests.get(i).getLocation_Y() == buildings.get(j).getClosestPoint_Y()
                            && buildings.get(j).getBuildingsImages().equals(guests.get(i).getTargetGame())
                            && !guests.get(i).getInGame()) {
                        guests.get(i).setMood(guests.get(i).getMood() + 5);
                        raiseMoney();
                        if(buildings.get(j).getMaxCapacity() > buildings.get(j).players.size()){        //van elég hely - játékra ül egyenesen
                            if(buildings.get(j).queue.isEmpty()){
                                guests.get(i).setInGame(true);
                                guestToGame(guests.get(i),j);
                            }
                            else {
                                guests.get(i).setInGame(true);
                                queueToPlayers(buildings.get(j).queue.get(0),j);
                            }
                        }
                        else {   //nincs hely a játékon - taka a sorba
                            guestToQueue(guests.get(i),j);
                            System.out.println("sorba alltam");
                        }
                    }
                }
            }
        }
    }

    public void putBackToGuest(){
        for (int i = 0; i < buildings.size(); i++) {
                for (int j = 0; j < buildings.get(i).players.size(); j++) {
                    if(!buildings.get(i).players.get(j).isInGame()){
                        guests.add(buildings.get(i).players.get(j));
                        buildings.get(i).players.remove(j);
                        System.out.println("vegeztem");
                    }
                }
        }
    }

    public void guestToGame(Guest guest, int indexOfBuilding){ //van hely, egyből felül, ha a sor üres
            buildings.get(indexOfBuilding).players.add(guest);
            guests.remove(guest);
    }


    public void queueToPlayers(Guest guest, int indexOfBuilding){ //ha van hely és áll valaki a sorban, akkor a sorból menjen fel a játékra a guest
            buildings.get(indexOfBuilding).players.add(guest);
            buildings.get(indexOfBuilding).queue.remove(guest);
    }

    public void guestToQueue(Guest guest, int indexOfBuilding){ //players.size() = az éppen használók száma ha nincs hely, akkor a sorba megy és nem a játékra
            buildings.get(indexOfBuilding).queue.add(guest);
            guests.remove(guest);
    }

    public void addTargetGameToGuest() {
        targetGameTimer--;
        if (targetGameTimer == 0) {
            targetGameTimer = 25;
            ArrayList<Integer> gameIndexes = new ArrayList<Integer>();
            for (int i = 0; i < buildings.size(); i++) {
                if (buildings.get(i).getBuildingsImages().equals("SLIDE")
                        || buildings.get(i).getBuildingsImages().equals("rollercoaster")
                        || buildings.get(i).getBuildingsImages().equals("WATERPARK")
                        || buildings.get(i).getBuildingsImages().equals("TRAIN")
                        || buildings.get(i).getBuildingsImages().equals("WHEEL")
                        || buildings.get(i).getBuildingsImages().equals("RESTAURANT")) {
                    gameIndexes.add(i);
                }
            }
            if (gameIndexes.size() > 0) {
                for (int i = 0; i < guests.size(); i++) {
                    Random random = new Random();
                    int r = random.nextInt(gameIndexes.size());
                    if (guests.get(i).getTargetGame().equals("")) {
                        guests.get(i).setTargetGame(buildings.get(gameIndexes.get(r)).getBuildingsImages());
                        System.out.println(guests.get(i).getTargetGame());
                    }
                }
            }
        }
    }

    public void cleanTrash() {               //ha a takarító koordinátája megegyezik a a szemét koordinátájával, akkor a szemetet eltávolítjuk
        for (int i = 0; i < workers.size(); i++) {
            if (workers.get(i).getPersonImages().equals("cleaner")) {
                for (int j = 0; j < trashes.size(); j++) {
                    if (workers.get(i).getLocation_X() == trashes.get(j).getLocation_X()
                            && workers.get(i).getLocation_Y() == trashes.get(j).getLocation_Y()) {
                        trashes.remove(j);
                    }
                }
            }
        }
    }

    public void beenToRestaurant() {         //ha áthaladt restauranton
        for (int i = 0; i < guests.size(); i++) {
            for (int j = 0; j < buildings.size(); j++) {
                if (guests.get(i).getLocation_X() == buildings.get(j).getClosestPoint_X()
                        && guests.get(i).getLocation_Y() == buildings.get(j).getClosestPoint_Y()
                        && buildings.get(j).getBuildingsImages().equals("RESTAURANT")) {
                    guests.get(i).setActivateTimer(true);
                }
            }
        }
    }

    public void leaveTrash() {                   //belerakja a szemetet a szemét arraylistbe - timerenként meghívódik
        for (int i = 0; i < guests.size(); i++) {
            boolean onBin = checkBin(guests.get(i).getLocation_X() - (guests.get(i).getLocation_X() % segmentSize), guests.get(i).getLocation_Y() - (guests.get(i).getLocation_Y() % segmentSize));
            if (guests.get(i).canLeaveTrash(onBin)) {
                trashes.add(new Trash(guests.get(i).getLocation_X() - (guests.get(i).getLocation_X() % segmentSize), guests.get(i).getLocation_Y() - (guests.get(i).getLocation_Y() % segmentSize)));

                if (onBin) {
                    shortTimerActive = true;
                }
            }
        }
    }

    public boolean shortTimer() {  // ha aktiv a timer
        if (shortTimerActive) {
            shortTimerCounter--;
            if (shortTimerCounter == 0) {
                shortTimerCounter = 5;
                shortTimerActive = false;
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public void removeLastTrash() {
        if (shortTimer()) {
            trashes.remove(trashes.size() - 1);
        }
    }

    public boolean checkBin(int x, int y) {
        for (int i = 0; i < buildings.size(); i++) {
            if (buildings.get(i).getBuildingsImages().equals("BIN")
                    && buildings.get(i).getClosestPoint_X() == x
                    && buildings.get(i).getClosestPoint_Y() == y) {
                return true;
            }
        }
        return false;
    }

    public void reduceConstructionTime() {
        for (int i = 0; i < buildings.size(); i++) {
            if (buildings.get(i).getConstructionTime() >= 0) {
                buildings.get(i).setConstructionTime((buildings.get(i).getConstructionTime() - 0.1));
            }
            if (buildings.get(i).getConstructionTime() <= 0) {
                if (buildings.get(i).getBuildingsImages().equals("slide_underconstruction")) {
                    buildings.get(i).setBuildingsImages("SLIDE");
                    addTargetGameToGuest();
                }
                if (buildings.get(i).getBuildingsImages().equals("waterpark_underconstruction")) {
                    buildings.get(i).setBuildingsImages("WATERPARK");
                    addTargetGameToGuest();
                }
                if (buildings.get(i).getBuildingsImages().equals("rollercoaster_underconstruction")) {
                    buildings.get(i).setBuildingsImages("rollercoaster");
                    addTargetGameToGuest();
                }
                if (buildings.get(i).getBuildingsImages().equals("wheel_underconstruction")) {
                    buildings.get(i).setBuildingsImages("WHEEL");
                    addTargetGameToGuest();
                }
                if (buildings.get(i).getBuildingsImages().equals("train_underconstruction")) {
                    buildings.get(i).setBuildingsImages("TRAIN");
                    addTargetGameToGuest();
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
            //if (!guests.get(i).isInGame()) {
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
           // }
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
        generateGuestTimer--;
        if (guestNumber < maxGuests) {
            if (generateGuestTimer == 0) {
                guests.add(new Guest("guest", 60, 80, segmentSize, segmentSize));
                guestNumber++;
                generateGuestTimer = 50;
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
            createGraphPeaks();
            int graph[][] = new int[graphPeaks.size()][graphPeaks.size()];
            givePeaksToGraph(graph);
            ShortestPath t = new ShortestPath();
            shortestRoadSteps = t.dijkstra(graph, 0, graphPeaks.size());
            convertIndexesToSteps(workers.size() - 1);
            workers.get(workers.size() - 1).setShortestPath(shortestRoadSteps);
        }
        removeCleaner();
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
                        if (min > coordinateDistance(buildings.get(i).getLocation_X() + buildings.get(i).getBuildingsSizesA() / 2, buildings.get(j).getLocation_X(), buildings.get(i).getLocation_Y() + buildings.get(i).getBuildingsSizesB() / 2, buildings.get(j).getLocation_Y())) {
                            {
                                min = coordinateDistance(buildings.get(i).getLocation_X() + buildings.get(i).getBuildingsSizesA() / 2, buildings.get(j).getLocation_X(), buildings.get(i).getLocation_Y() + buildings.get(i).getBuildingsSizesB() / 2, buildings.get(j).getLocation_Y());
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
                        guests.get(i).setMood(guests.get(i).getMood() - 3);
                    }
                }
            }
            for (int k = 0; k < trashes.size(); k++) {
                if (guests.get(i).getLocation_X() == trashes.get(k).getLocation_X()
                        && guests.get(i).getLocation_Y() == trashes.get(k).getLocation_Y()) {
                    guests.get(i).setMood(guests.get(i).getMood() - 1);
                }
            }
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

                //g.setColor(Color.GRAY);
                //g.fillRect(buildings.get(i).getClosestPoint_X(), buildings.get(i).getClosestPoint_Y(), segmentSize, segmentSize);
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

            } catch (IOException f) {
                System.out.println("error");
                f.printStackTrace();
            }
        }

        /*
         * Redraw images
         * trashes
         */
        for (int i = 0; i < trashes.size(); i++) {
            try {
                img = ImageIO.read(new File("data\\images\\TRASH.png"));
                Graphics2D g3d = (Graphics2D) g;
                g3d.drawImage(img, trashes.get(i).getLocation_X() + segmentSize / 2, trashes.get(i).getLocation_Y() + segmentSize / 2, segmentSize / 2, segmentSize / 2, null);
            } catch (IOException f) {
                System.out.println("error");
                f.printStackTrace();
            }
        }

        /*
         * Redraw images
         * queues for games
         */
        for (int i = 0; i < buildings.size(); i++) {
            for (int j = 0; j < buildings.get(i).queue.size(); j++) {
                try {
                    if (buildings.get(i).queue.get(j).getMood() <= 5) {
                        buildings.get(i).queue.get(j).setPersonImages("guest_angeri");
                    } else {
                        buildings.get(i).queue.get(j).setPersonImages("guest");
                    }
                    img = ImageIO.read(new File("data\\images\\" + buildings.get(i).queue.get(j).getPersonImages() + ".png"));
                    Graphics2D g3d = (Graphics2D) g;
                    g3d.drawImage(img, buildings.get(i).queue.get(j).getLocation_X(), (int) (buildings.get(i).queue.get(j).getLocation_Y() + j * (0.5 * segmentSize)), buildings.get(i).queue.get(j).getBuildingsSizesA(), buildings.get(i).queue.get(j).getBuildingsSizesB(), null);
                } catch (IOException f) {
                    System.out.println("error");
                    f.printStackTrace();
                }
            }
        }
    }

    public void editUsagePrice(int x, int y) {
        for (int j = 0; j < buildings.size(); j++) {
            if (!buildings.get(j).getBuildingsImages().equals("ROAD") && (buildings.get(j).getLocation_X() < x && x < (buildings.get(j).sumXA()) && (buildings.get(j).getLocation_Y() < y && y < (buildings.get(j).sumYB())))
                    || !buildings.get(j).getBuildingsImages().equals("BUSH") && (buildings.get(j).getLocation_X() < x && x < (buildings.get(j).sumXA()) && (buildings.get(j).getLocation_Y() < y && y < (buildings.get(j).sumYB())))
                    || !buildings.get(j).getBuildingsImages().equals("TREE") && (buildings.get(j).getLocation_X() < x && x < (buildings.get(j).sumXA()) && (buildings.get(j).getLocation_Y() < y && y < (buildings.get(j).sumYB())))
                    || !buildings.get(j).getBuildingsImages().equals("BIN") && (buildings.get(j).getLocation_X() < x && x < (buildings.get(j).sumXA()) && (buildings.get(j).getLocation_Y() < y && y < (buildings.get(j).sumYB())))) {
                ThemeParkGUI.selected_ge = EGeneralEquipment.NOTHING;

                Object[] options1 = {"Delete", "Change usage price", "Cancel"};

                JPanel panel = new JPanel();
                panel.add(new JLabel("What would you like to do?"));

                int result = JOptionPane.showOptionDialog(null, panel, "Manage building",
                        JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,
                        null, options1, null);
                if (result == JOptionPane.YES_OPTION) {
                    if (!buildings.get(j).getBuildingsImages().contains("outoforder")) {
                        buildings.remove(j);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Your can not remove out of order games.");
                    }


                } else if (result == JOptionPane.NO_OPTION) {
                    JFrame f = new JFrame();
                    String s = JOptionPane.showInputDialog(f, "Change usage price");
                    int newUsagePrice;

                    newUsagePrice = Integer.parseInt(s);

                    buildings.get(j).setUsagePrice(newUsagePrice);
                }
            }
        }
    }

    public void canNotBuildOnBuilding(int x, int y) {
        for (int j = 0; j < buildings.size(); j++) {
            if (buildings.get(j).getBuildingsImages().equals("ROAD") && (buildings.get(j).getLocation_X() == x && (buildings.get(j).getLocation_Y() == y))
                    || buildings.get(j).getBuildingsImages().equals("BUSH") && (buildings.get(j).getLocation_X() == x && (buildings.get(j).getLocation_Y() == y))
                    || buildings.get(j).getBuildingsImages().equals("TREE") && (buildings.get(j).getLocation_X() == x && (buildings.get(j).getLocation_Y() == y))
                    || buildings.get(j).getBuildingsImages().equals("BIN") && (buildings.get(j).getLocation_X() == x && (buildings.get(j).getLocation_Y() == y))) {
                if (ThemeParkGUI.selected_ge != EGeneralEquipment.ROAD) {
                    JOptionPane.showMessageDialog(null, "You can not build " + ThemeParkGUI.selected_ge + " on " + buildings.get(j).getBuildingsImages() + "!");
                    ThemeParkGUI.selected_ge = EGeneralEquipment.NOTHING;
                }
            }
        }
    }

    public void removeCleaner() {
        if (ThemeParkGUI.selected_ge.equals(EGeneralEquipment.KICKCLEANER)) {
            for (int i = 0; i < workers.size(); i++) {
                System.out.println(workers.get(i).getPersonImages());
                if (workers.get(i).getPersonImages().equals("cleaner")) {
                    workers.remove(i);
                    ThemeParkGUI.selected_ge = EGeneralEquipment.NOTHING;
                    break;
                }
            }
        }
    }

    public void addElementsToGameArrayList() {
        games.clear();
        for (int i = 0; i < buildings.size(); i++) {
            if (!buildings.get(i).getBuildingsImages().equals("ROAD")
                    && !buildings.get(i).getBuildingsImages().equals("BUSH")
                    && !buildings.get(i).getBuildingsImages().equals("TREE")
                    && !buildings.get(i).getBuildingsImages().equals("BIN")
                    && !buildings.get(i).getBuildingsImages().equals("RESTAURANT")) {
                games.add(buildings.get(i));
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

        canBuildOn = true;

        editUsagePrice(x - (x % segmentSize), y - (y % segmentSize));
        canNotBuildOnBuilding(x - (x % segmentSize), y - (y % segmentSize));

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
                Can't build roads on each other
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
                    buildings.add(new Building("ROAD", 0.0, 10, x - (x % segmentSize), y - (y % segmentSize), segmentSize, segmentSize));
                    roads.add(new Building("ROAD", 0.0, 10, x - (x % segmentSize), y - (y % segmentSize), segmentSize, segmentSize));
                    setClosestPointsToGames();
                    budget -= 10;
                } else if (budget - 10 < 0) {
                    JOptionPane.showMessageDialog(frame, "There's no enough money for ROAD");
                }
                repaint();
            }

            if (ThemeParkGUI.selected_ge.equals(EGeneralEquipment.BUSH)) {  // If the selected GeneralEquipment is bush
                x = e.getX();
                y = e.getY();

                /*
                 * Can be built only if the user got enough money
                 */

                if (budget - 10 >= 0 && PointIsWithinCircle(e.getX(), e.getY()) && isCanBuildOn(e.getX(), e.getY())) {
                    buildings.add(new Building("BUSH", 0.0, 10, x - (x % segmentSize), y - (y % segmentSize), segmentSize, segmentSize));
                    budget -= 10;
                    setClosestPointsToGames();
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
                    buildings.add(new Building("BIN", 0.0, 10, x - (x % segmentSize), y - (y % segmentSize), segmentSize, segmentSize));
                    setClosestPointsToGames();
                    budget -= 10;
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
                    buildings.add(new Building("TREE", 0.0, 10, x - (x % segmentSize), y - (y % segmentSize), segmentSize, segmentSize));
                    setClosestPointsToGames();
                    budget -= 10;
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
                    buildings.add(new Building("rollercoaster_underconstruction", 5.0, 1000, x - (x % segmentSize) - 3 * segmentSize, y - (y % segmentSize) - 2 * segmentSize, segmentSize * 6, segmentSize * 4));
                    addElementsToGameArrayList();
                    setClosestPointsToGames();
                    buildings.get(buildings.size() - 1).setUsagePrice(455);
                    budget -= 1000;
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
                    buildings.add(new Building("train_underconstruction", 5.0, 800, x - (x % segmentSize) - 2 * segmentSize, y - (y % segmentSize) - segmentSize, segmentSize * 4, segmentSize * 4));
                    addElementsToGameArrayList();
                    buildings.get(buildings.size() - 1).setUsagePrice(355);
                    budget -= 800;
                    setClosestPointsToGames();
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
                    buildings.add(new Building("waterpark_underconstruction", 5.0, 1000, x - (x % segmentSize) - 2 * segmentSize, y - (y % segmentSize) - 2 * segmentSize, segmentSize * 6, segmentSize * 4));
                    addElementsToGameArrayList();
                    buildings.get(buildings.size() - 1).setUsagePrice(465);
                    setClosestPointsToGames();
                    budget -= 1000;
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

                    buildings.add(new Building("wheel_underconstruction", 5.0, 1500, x - (x % segmentSize) - 2 * segmentSize, y - (y % segmentSize) - 2 * segmentSize, segmentSize * 6, segmentSize * 6));
                    addElementsToGameArrayList();
                    buildings.get(buildings.size() - 1).setUsagePrice(468);
                    setClosestPointsToGames();
                    budget -= 1500;
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

                    buildings.add(new Building("slide_underconstruction", 5.0, 800, x - (x % segmentSize) - segmentSize, y - (y % segmentSize) - segmentSize, segmentSize * 4, segmentSize * 4));
                    addElementsToGameArrayList();
                    buildings.get(buildings.size() - 1).setUsagePrice(366);
                    setClosestPointsToGames();
                    budget -= 800;
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

                    buildings.add(new Building("RESTAURANT", 0.0, 600, x - (x % segmentSize), y - (y % segmentSize), segmentSize * 3, segmentSize * 2));
                    buildings.get(buildings.size() - 1).setUsagePrice(55);
                    setClosestPointsToGames();
                    budget -= 600;
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

    public int getSegmentSize() {
        return segmentSize;
    }
}