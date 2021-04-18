package board;

import ThemePark.*;
import gui.ThemeParkGUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
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
        timer = new Timer(TD, (ActionEvent e) -> {
            generateGuest();
            generateWorker();
            moveCleaner();
            moveGuest();
            repaint();
        });
        timer.start();
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

    /**
     * Checks if a road near if you try to make a building
     * return true if you can build
     * return false if no road near
     **/
    public boolean canBuildNearRoad(int mouse_X, int mouse_Y) {

        for (int i = 0; i < buildings.size(); i++) {
            if (buildings.get(i).getBuildingsImages().equals("ROAD")) {
                //felső oldal
                if (mouse_X <= buildings.get(i).getLocation_X() + (buildings.get(i).getBuildingsSizesA() / 2) && mouse_Y == buildings.get(i).getLocation_Y() + (buildings.get(i).getBuildingsSizesB() / 2)) {
                    return true;
                }
                //alsó oldal
                if (mouse_X >= buildings.get(i).getLocation_X() + (buildings.get(i).getBuildingsSizesA() / 2) && mouse_Y == buildings.get(i).getLocation_Y() + (buildings.get(i).getBuildingsSizesB() / 2)) {
                    return true;
                }
                //Bal oldal
                if (mouse_X == buildings.get(i).getLocation_X() + (buildings.get(i).getBuildingsSizesA() / 2) && mouse_Y >= buildings.get(i).getLocation_Y() + (buildings.get(i).getBuildingsSizesB() / 2)) {
                    return true;
                }
                //Jobb oldal
                if (mouse_X == buildings.get(i).getLocation_X() + (buildings.get(i).getBuildingsSizesA() / 2) && mouse_Y <= buildings.get(i).getLocation_Y() + (buildings.get(i).getBuildingsSizesB() / 2)) {
                    return true;
                }

                //Átló
                //Felső jobb
                if (mouse_X >= buildings.get(i).getLocation_X() + (buildings.get(i).getBuildingsSizesA() / 2) && mouse_Y <= buildings.get(i).getLocation_Y() + (buildings.get(i).getBuildingsSizesB() / 2)) {
                    return true;
                }
                //Felső bal
                if (mouse_X <= buildings.get(i).getLocation_X() + (buildings.get(i).getBuildingsSizesA() / 2) && mouse_Y <= buildings.get(i).getLocation_Y() + (buildings.get(i).getBuildingsSizesB() / 2)) {
                    return true;
                }
                //Alsó jobb
                if (mouse_X >= buildings.get(i).getLocation_X() + (buildings.get(i).getBuildingsSizesA() / 2) && mouse_Y >= buildings.get(i).getLocation_Y() + (buildings.get(i).getBuildingsSizesB() / 2)) {
                    return true;
                }
                //Alsó bal
                if (mouse_X <= buildings.get(i).getLocation_X() + (buildings.get(i).getBuildingsSizesA() / 2) && mouse_Y >= buildings.get(i).getLocation_Y() + (buildings.get(i).getBuildingsSizesB() / 2)) {
                    return true;
                }
            }
        }
        return false;
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
                    buildings.add(new Building("ROAD", 0, 10, x - (x % segmentSize), y - (y % segmentSize), segmentSize, segmentSize));

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
                if (budget - 10 >= 0) {
                    buildings.add(new Building("BUSH", 0, 10, x - (x % segmentSize), y - (y % segmentSize), segmentSize, segmentSize));
                    System.out.println(x + "," + y);//these co-ords are relative to the component

                    repaint();
                } else {
                    JOptionPane.showMessageDialog(frame, "There's no enough money for BUSH");
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
                if (canBuild && (budget - 10 >= 0)) {
                    System.out.println("KUKA EPULT");
                    System.out.println(x + "," + y);//these co-ords are relative to the component
                    buildings.add(new Building("BIN", 0, 10, x - (x % segmentSize), y - (y % segmentSize), segmentSize, segmentSize));

                    repaint();
                } else if (budget - 10 < 0) {
                    JOptionPane.showMessageDialog(frame, "There's no enough money for BIN");
                }

            }


            if (ThemeParkGUI.selected_ge.equals(EGeneralEquipment.TREE)) {  // If the selected GeneralEquipment is tree
                // builds tree
                x = e.getX();
                y = e.getY();

                /*
                 * Can be built only if the user got enough money
                 */
                if (budget - 10 >= 0) {
                    System.out.println("FA EPULT");
                    System.out.println(x + "," + y);//these co-ords are relative to the component
                    buildings.add(new Building("TREE", 0, 10, x - (x % segmentSize), y - (y % segmentSize), segmentSize * 2, segmentSize * 2));
                    repaint();
                } else {
                    JOptionPane.showMessageDialog(frame, "There's no enough money for TREE");
                }
            }


            if (ThemeParkGUI.selected_ge.equals(EGeneralEquipment.ROLLERCOASTER)) {
                x = e.getX();
                y = e.getY();

                /*
                 * Can be built only if the user got enough money
                 */
                if (budget - 1000 >= 0) {
                    System.out.println("RC EPULT");
                    System.out.println(x + "," + y);//these co-ords are relative to the component
                    buildings.add(new Game("rollercoaster", 0, 1000, x - (x % segmentSize) - 3 * segmentSize, y - (y % segmentSize) - 2 * segmentSize, segmentSize * 6, segmentSize * 4, 15));
                    repaint();
                } else {
                    JOptionPane.showMessageDialog(frame, "There's no enough money for ROLLERCOASTER");
                }
            }

            if (ThemeParkGUI.selected_ge.equals(EGeneralEquipment.TRAIN)) { // If the selected GeneralEquipment is train
                // builds train
                x = e.getX();
                y = e.getY();

                /*
                 * Can be built only if the user got enough money
                 */
                if (budget - 800 >= 0) {
                    System.out.println("TRAIN EPULT");
                    System.out.println(x + "," + y);//these co-ords are relative to the component
                    buildings.add(new Game("TRAIN", 0, 800, x - (x % segmentSize) - 2 * segmentSize, y - (y % segmentSize) - segmentSize, segmentSize * 4, segmentSize * 4, 15));
                    repaint();
                } else {
                    JOptionPane.showMessageDialog(frame, "There's no enough money for TRAIN");
                }
            }

            if (ThemeParkGUI.selected_ge.equals(EGeneralEquipment.WATERPARK)) { // If the selected GeneralEquipment is waterpark
                // builds waterpark
                x = e.getX();
                y = e.getY();

                /*
                 * Can be built only if the user got enough money
                 */
                if (budget - 1000 >= 0) {
                    System.out.println("WP EPULT");
                    System.out.println(x + "," + y);//these co-ords are relative to the component
                    buildings.add(new Game("WATERPARK", 0, 1000, x - (x % segmentSize) - 2 * segmentSize, y - (y % segmentSize) - 2 * segmentSize, segmentSize * 6, segmentSize * 4, 15));
                    repaint();
                } else {
                    JOptionPane.showMessageDialog(frame, "There's no enough money for WATERPARK");
                }
            }

            if (ThemeParkGUI.selected_ge.equals(EGeneralEquipment.WHEEL)) { // If the selected GeneralEquipment is wheel
                // builds wheel
                x = e.getX();
                y = e.getY();

                /*
                 * Can be built only if the user got enough money
                 */
                if (budget - 1500 >= 0 && canBuildOn) {
                    System.out.println("WHEEL EPULT");
                    System.out.println(x + "," + y);//these co-ords are relative to the component
                    buildings.add(new Game("WHEEL", 0, 1500, x - (x % segmentSize) - 2 * segmentSize, y - (y % segmentSize) - 2 * segmentSize, segmentSize * 6, segmentSize * 6, 15));

                    repaint();
                } else {
                    JOptionPane.showMessageDialog(frame, "There's no enough money for WHEEL");
                }
            }

            if (ThemeParkGUI.selected_ge.equals(EGeneralEquipment.SLIDE)) { // If the selected GeneralEquipment is slide
                // builds slide
                x = e.getX();
                y = e.getY();

                /*
                 * Can be built only if the user got enough money
                 */
                if (budget - 800 >= 0) {
                    System.out.println("SLIDE EPULT");
                    System.out.println(x + "," + y);//these co-ords are relative to the component
                    buildings.add(new Game("SLIDE", 0, 800, x - (x % segmentSize) - segmentSize, y - (y % segmentSize) - segmentSize, segmentSize * 4, segmentSize * 4, 15));
                    repaint();
                } else {
                    JOptionPane.showMessageDialog(frame, "There's no enough money for SLIDE");
                }
            }

            if (ThemeParkGUI.selected_ge.equals(EGeneralEquipment.RESTAURANT)) { // If the selected GeneralEquipment is restaurant
                // builds restaurant
                x = e.getX();
                y = e.getY();

                /*
                 * Can be built only if the user got enough money
                 */
                if (budget - 600 >= 0) {
                    System.out.println("RESTAURANT EPULT");
                    System.out.println(x + "," + y);//these co-ords are relative to the component
                    buildings.add(new Restaurant("RESTAURANT", 0, 600, x - (x % segmentSize), y - (y % segmentSize), segmentSize * 3, segmentSize * 2, 15));

                    repaint();
                } else {
                    JOptionPane.showMessageDialog(frame, "There's no enough money for RESTAURANT");
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