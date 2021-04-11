package board;

import ThemePark.Building;
import ThemePark.EGeneralEquipment;
import ThemePark.Game;
import ThemePark.Restaurant;
import gui.ThemeParkGUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


/**
 * The type Tp board.
 */
public class TPBoard extends JPanel implements MouseListener {
    private final int WIDTH = 600; //width of the park
    private final int HEIGHT = 600; //height of the park
    public int budget;
    /**
     * Building informations
     */
    public int x;
    public int y;
    public boolean canBuild; // Can we build next to the road
    public boolean canBuildOn; //Not to build an object to antoher object
    public ArrayList<Building> buildings = new ArrayList<Building>();
    Color clr1 = new Color(0, 153, 0);
    Image img = null;
    JFrame frame = new JFrame();
    private boolean isGameOver = false;
    private int segmentSize = 20; //size of one grid
    private int buildPrice_;

    public TPBoard() throws IOException {
        this.addMouseListener(this);
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.startGame();
    }

    public void startGame() {
        repaint();
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

        /**
         * Starting road
         */

        /*Starter road*/
        try {
            img = ImageIO.read(new File("data\\images\\ROAD.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Graphics2D g1d = (Graphics2D) g;
        g1d.drawImage(img, 60, 80, segmentSize, segmentSize, null);
        Building starterRoad = new Building("ROAD", 0, 0, 60, 80, segmentSize, segmentSize);
        buildings.add(starterRoad);

        /*Entrance*/
        try {
            img = ImageIO.read(new File("data\\images\\entrance.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(img, segmentSize, 0, segmentSize * 5, segmentSize * 6, null);


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
    }

    @Override
    public void mousePressed(MouseEvent e) {
        /*
            If selected game not nothing (empty variable), check what kind of GeneralEquipment is selected.
         */
        if (!ThemeParkGUI.selected_ge.equals(EGeneralEquipment.NOTHING)) {
            if (ThemeParkGUI.selected_ge.equals(EGeneralEquipment.ROAD)) {
                x = e.getX();
                y = e.getY();

                System.out.println(x + " " + y);
                canBuild = false;

                /*
                    Road can be built only near another road
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
                canBuildOn = true;
                for (int i = 0; i < buildings.size(); i++) {
                    if ((x - (x % segmentSize)) < x && x < (buildings.get(i).sumXA()) && (y - (y % segmentSize)) < y && y < (buildings.get(i).sumYB())) {
                        canBuildOn = false;

                    }
                }
                if (canBuild && budget - 10 >= 0 && canBuildOn) {
                    System.out.println("UT EPULT");
                    System.out.println(x + "," + y);//these co-ords are relative to the component
                    buildings.add(new Building("ROAD", 0, 10, x - (x % segmentSize), y - (y % segmentSize), segmentSize, segmentSize));
                    //System.out.println(x - (x % segmentSize) + " " + (y - (y % segmentSize)));

                } else if (budget - 10 < 0) {
                    JOptionPane.showMessageDialog(frame, "There's no enough money for ROAD");
                }
                repaint();
            }

            if (ThemeParkGUI.selected_ge.equals(EGeneralEquipment.BUSH)) {
                x = e.getX();
                y = e.getY();

                System.out.println("BOKOR EPULT");

                canBuildOn = true;
                for (int i = 0; i < buildings.size(); i++) {
                    if ((x - (x % segmentSize)) < x && x < (buildings.get(i).sumXA()) && (y - (y % segmentSize)) < y && y < (buildings.get(i).sumYB())) {
                        canBuildOn = false;
                    }
                }
                if (budget - 10 >= 0 && canBuildOn) {
                    buildings.add(new Building("BUSH", 0, 10, x - (x % segmentSize), y - (y % segmentSize), segmentSize, segmentSize));
                    System.out.println(x + "," + y);//these co-ords are relative to the component

                    repaint();
                } else if (budget - 10 < 0) {
                    JOptionPane.showMessageDialog(frame, "There's no enough money for BUSH");
                }
            }

            if (ThemeParkGUI.selected_ge.equals(EGeneralEquipment.BIN)) {
                x = e.getX();
                y = e.getY();

                canBuild = false;


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
                canBuildOn = true;
                for (int i = 0; i < buildings.size(); i++) {
                    if ((x - (x % segmentSize)) < x && x < (buildings.get(i).sumXA()) && (y - (y % segmentSize)) < y && y < (buildings.get(i).sumYB())) {
                        canBuildOn = false;
                    }
                }
                if (canBuild && (budget - 10 >= 0) && canBuildOn) {
                    System.out.println("KUKA EPULT");
                    System.out.println(x + "," + y);//these co-ords are relative to the component
                    buildings.add(new Building("BIN", 0, 10, x - (x % segmentSize), y - (y % segmentSize), segmentSize, segmentSize));

                    repaint();
                } else if (budget - 10 < 0) {
                    JOptionPane.showMessageDialog(frame, "There's no enough money for BIN");
                }

            }


            if (ThemeParkGUI.selected_ge.equals(EGeneralEquipment.TREE)) {
                canBuild = true;
                x = e.getX();
                y = e.getY();

                for (int i = 0; i < buildings.size(); i++) {
                    System.out.println("sumXA: " + buildings.get(i).sumXA() + " " + buildings.get(i).sumYB());
                    if ((x - (x % segmentSize)) < x && x < (buildings.get(i).sumXA()) && (y - (y % segmentSize)) < y && y < (buildings.get(i).sumYB())) {
                        canBuild = false;
                    }
                }

                canBuildOn = true;
                for (int i = 0; i < buildings.size(); i++) {
                    if ((x - (x % segmentSize)) < x && x < (buildings.get(i).sumXA()) && (y - (y % segmentSize)) < y && y < (buildings.get(i).sumYB())) {
                        canBuildOn = false;
                    }
                }
                if (budget - 10 >= 0 && canBuild && canBuildOn) {
                    System.out.println("FA EPULT");
                    System.out.println(x + "," + y);//these co-ords are relative to the component
                    buildings.add(new Building("TREE", 0, 10, x - (x % segmentSize), y - (y % segmentSize), segmentSize * 2, segmentSize * 2));
                    repaint();
                } else if (budget - 10 < 0) {
                    JOptionPane.showMessageDialog(frame, "There's no enough money for TREE");
                }
            }
        }


        if (!ThemeParkGUI.selected_ge.equals(EGeneralEquipment.NOTHING)) {
            if (ThemeParkGUI.selected_ge.equals(EGeneralEquipment.ROLLERCOASTER)) {
                x = e.getX();
                y = e.getY();

                canBuildOn = true;
                for (int i = 0; i < buildings.size(); i++) {
                    if ((x - (x % segmentSize)) < x && x < (buildings.get(i).sumXA()) && (y - (y % segmentSize)) < y && y < (buildings.get(i).sumYB())) {
                        canBuildOn = false;
                    }
                }
                if (budget - 1000 >= 0 && canBuildOn) {
                    System.out.println("RC EPULT");
                    System.out.println(x + "," + y);//these co-ords are relative to the component
                    buildings.add(new Game("rollercoaster", 0, 1000, x - (x % segmentSize), y - (y % segmentSize), segmentSize * 6, segmentSize * 4));
                    repaint();
                } else if (budget - 1000 < 0) {
                    JOptionPane.showMessageDialog(frame, "There's no enough money for ROLLERCOASTER");
                }
            }

            if (ThemeParkGUI.selected_ge.equals(EGeneralEquipment.TRAIN)) {
                x = e.getX();
                y = e.getY();

                canBuildOn = true;
                for (int i = 0; i < buildings.size(); i++) {
                    if ((x - (x % segmentSize)) < x && x < (buildings.get(i).sumXA()) && (y - (y % segmentSize)) < y && y < (buildings.get(i).sumYB())) {
                        canBuildOn = false;
                    }
                }

                if (budget - 800 >= 0 && canBuildOn) {
                    System.out.println("TRAIN EPULT");
                    System.out.println(x + "," + y);//these co-ords are relative to the component
                    buildings.add(new Game("TRAIN", 0, 800, x - (x % segmentSize), y - (y % segmentSize), segmentSize * 4, segmentSize * 4));
                    repaint();
                } else if (budget - 800 < 0) {
                    JOptionPane.showMessageDialog(frame, "There's no enough money for TRAIN");
                }
            }

            if (ThemeParkGUI.selected_ge.equals(EGeneralEquipment.WATERPARK)) {
                x = e.getX();
                y = e.getY();

                canBuildOn = true;
                for (int i = 0; i < buildings.size(); i++) {
                    if ((x - (x % segmentSize)) < x && x < (buildings.get(i).sumXA()) && (y - (y % segmentSize)) < y && y < (buildings.get(i).sumYB())) {
                        canBuildOn = false;
                    }
                }
                if (budget - 1000 >= 0 && canBuildOn) {
                    System.out.println("WP EPULT");
                    System.out.println(x + "," + y);//these co-ords are relative to the component
                    buildings.add(new Game("WATERPARK", 0, 1000, x - (x % segmentSize), y - (y % segmentSize), segmentSize * 6, segmentSize * 4));
                    repaint();
                } else if (budget - 1000 < 0) {
                    JOptionPane.showMessageDialog(frame, "There's no enough money for WATERPARK");
                }
            }

            if (ThemeParkGUI.selected_ge.equals(EGeneralEquipment.WHEEL)) {
                x = e.getX();
                y = e.getY();

                canBuildOn = true;
                for (int i = 0; i < buildings.size(); i++) {
                    if ((x - (x % segmentSize)) < x && x < (buildings.get(i).sumXA()) && (y - (y % segmentSize)) < y && y < (buildings.get(i).sumYB())) {
                        canBuildOn = false;
                    }
                }

                if (budget - 1500 >= 0 && canBuildOn) {
                    System.out.println("WHEEL EPULT");
                    System.out.println(x + "," + y);//these co-ords are relative to the component
                    buildings.add(new Game("WHEEL", 0, 1500, x - (x % segmentSize), y - (y % segmentSize), segmentSize * 6, segmentSize * 6));

                    repaint();
                } else if (budget - 1500 < 0) {
                    JOptionPane.showMessageDialog(frame, "There's no enough money for WHEEL");
                }
            }

            if (ThemeParkGUI.selected_ge.equals(EGeneralEquipment.SLIDE)) {
                x = e.getX();
                y = e.getY();

                canBuildOn = true;
                for (int i = 0; i < buildings.size(); i++) {
                    if ((x - (x % segmentSize)) < x && x < (buildings.get(i).sumXA()) && (y - (y % segmentSize)) < y && y < (buildings.get(i).sumYB())) {
                        canBuildOn = false;
                    }
                }

                if (budget - 800 >= 0 && canBuildOn) {
                    System.out.println("SLIDE EPULT");
                    System.out.println(x + "," + y);//these co-ords are relative to the component
                    buildings.add(new Game("SLIDE", 0, 800, x - (x % segmentSize), y - (y % segmentSize), segmentSize * 4, segmentSize * 4));
                    repaint();
                } else if (budget - 800 < 0) {
                    JOptionPane.showMessageDialog(frame, "There's no enough money for SLIDE");
                }
            }

            if (ThemeParkGUI.selected_ge.equals(EGeneralEquipment.RESTAURANT)) {
                x = e.getX();
                y = e.getY();

                canBuildOn = true;
                for (int i = 0; i < buildings.size(); i++) {
                    if ((x - (x % segmentSize)) < x && x < (buildings.get(i).sumXA()) && (y - (y % segmentSize)) < y && y < (buildings.get(i).sumYB())) {
                        canBuildOn = false;
                    }
                }

                if (budget - 600 >= 0 && canBuildOn) {
                    System.out.println("RESTAURANT EPULT");
                    System.out.println(x + "," + y);//these co-ords are relative to the component
                    buildings.add(new Restaurant("RESTAURANT", 0, 600, x - (x % segmentSize), y - (y % segmentSize), segmentSize * 3, segmentSize * 2));

                    repaint();
                } else if (budget - 600 < 0) {
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
