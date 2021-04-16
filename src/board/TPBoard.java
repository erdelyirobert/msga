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
import java.sql.SQLOutput;
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
    private int maxGuests = 3;
    private int currentGuests = 0;
    private int randomTimer;

    Random random = new Random();

    Color clr1 = new Color(0, 153, 0);
    Image img = null;
    JFrame frame = new JFrame();
    private int segmentSize = 20; //size of one grid
    public Timer timer;
    public Timer timer2;
    public int TD = 300;
    public int memoryDirection;


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

        generateGuest();
        //generateGuest();
        timer = new Timer(TD, (ActionEvent e) -> {

            moveGuest();
            System.out.println("-------------");
            //System.out.println("irány: " + guests.get(0).getDirection() +" x: " + guests.get(0).getLocation_X() + " y: " + guests.get(0).getLocation_Y());
            repaint();
        });
        timer.start();
    }

    public boolean checkRoad(int x, int y){
        for (int i = 0; i < buildings.size(); i++) {
            if (buildings.get(i).getBuildingsImages().equals("ROAD")){
                if (x - (x%segmentSize) == buildings.get(i).getLocation_X()
                        && y-(y%segmentSize) == buildings.get(i).getLocation_Y()){
                    return true;
                }
            }
        }
        return false;
    }

    public void moveGuest() {       //jobbra -> lefel -> balra -> felfele -> jobbra

        for (int i = 0; i < guests.size(); i++) {
                if (guests.get(i).getDirection() == 0) {            //jobbra lép
                    if(checkRoad(guests.get(i).getLocation_X() + segmentSize, guests.get(i).getLocation_Y())){
                            guests.get(i).setLocation_X(guests.get(i).getLocation_X() + segmentSize/2);
                            System.out.println("jobbra léptem");
                            break;
                        } else {
                            Random random = new Random();
                            int r = random.nextInt(4);
                            guests.get(i).setDirection(r);
                        }
                }

                else if (guests.get(i).getDirection() == 1) {               //lefele lép
                    if (checkRoad(guests.get(i).getLocation_X(), guests.get(i).getLocation_Y() + segmentSize)){
                            guests.get(i).setLocation_Y(guests.get(i).getLocation_Y() + segmentSize/2);
                            System.out.println("lefele léptem");
                            break;
                        } else {
                            Random random = new Random();
                            int r = random.nextInt(4);
                            guests.get(i).setDirection(r);
                        }
                    }

                else if (guests.get(i).getDirection() == 2) {                       //balra lép
                    if (checkRoad(guests.get(i).getLocation_X() - segmentSize/2, guests.get(i).getLocation_Y())){
                            guests.get(i).setLocation_X(guests.get(i).getLocation_X() - segmentSize/2);
                            System.out.println("balra léptem");
                            break;
                        } else {
                            Random random = new Random();
                            int r = random.nextInt(4);
                            guests.get(i).setDirection(r);
                        }
                }

                 else {
                    if (checkRoad(guests.get(i).getLocation_X(), guests.get(i).getLocation_Y() - segmentSize/2)) {              //felfele lép
                            guests.get(i).setLocation_Y(guests.get(i).getLocation_Y() - segmentSize/2);
                            System.out.println("felfele léptem");
                            break;
                        } else {
                            Random random = new Random();
                            int r = random.nextInt(4);
                            guests.get(i).setDirection(r);
                        }
                    }
                }
    }

    public void generateGuest(){
        Random random = new Random();
        int r = random.nextInt(100);
        //if(r%5==0) {
            guests.add(new Guest("guest",60,80, segmentSize , segmentSize ));
        //}
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
         * Starting road
         */
        /*try {
            img = ImageIO.read(new File("data\\images\\ROAD.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Graphics2D g1d = (Graphics2D) g;
        g1d.drawImage(img, 60, 80, segmentSize, segmentSize, null);
*/
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
        
    }

    /**
     * Mouselistener for everything the user can do with mouse
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
        while(j < buildings.size()){
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
         * If selected game not nothing (empty variable), check what kind of GeneralEquipment is selected.
         */
        if (!ThemeParkGUI.selected_ge.equals(EGeneralEquipment.NOTHING)) {
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
                    //System.out.println(x - (x % segmentSize) + " " + (y - (y % segmentSize)));

                } else if (budget - 10 < 0) {
                    JOptionPane.showMessageDialog(frame, "There's no enough money for ROAD");
                }
                repaint();
            }

            if (ThemeParkGUI.selected_ge.equals(EGeneralEquipment.BUSH)) {  // If the selected GeneralEquipment is bush
                // builds bush
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
                    buildings.add(new Game("rollercoaster", 0, 1000, x - (x % segmentSize) - 3 * segmentSize, y - (y % segmentSize) - 2 * segmentSize, segmentSize * 6, segmentSize * 4,15));
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
                    buildings.add(new Game("TRAIN", 0, 800, x - (x % segmentSize) - 2 * segmentSize, y - (y % segmentSize) - segmentSize, segmentSize * 4, segmentSize * 4,15));
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
                    buildings.add(new Game("WATERPARK", 0, 1000, x - (x % segmentSize) - 2 * segmentSize, y - (y % segmentSize) - 2 * segmentSize, segmentSize * 6, segmentSize * 4,15));
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
                    buildings.add(new Game("WHEEL", 0, 1500, x - (x % segmentSize) - 2 * segmentSize, y - (y % segmentSize) - 2 * segmentSize, segmentSize * 6, segmentSize * 6,15));

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
                    buildings.add(new Game("SLIDE", 0, 800, x - (x % segmentSize) - segmentSize, y - (y % segmentSize) - segmentSize, segmentSize * 4, segmentSize * 4,15));
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
                    buildings.add(new Restaurant("RESTAURANT", 0, 600, x - (x % segmentSize), y - (y % segmentSize), segmentSize * 3, segmentSize * 2,15));

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