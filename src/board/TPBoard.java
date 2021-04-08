package board;

import ThemePark.*;
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
    public int budget = 10000;
    /**
     * Building informations
     */
    public int x;
    public int y;
    public boolean canBuild;
    Color clr1 = new Color(0, 153, 0);
    Image img = null;

    ArrayList<Building> buildings = new ArrayList<Building>();
    private boolean isGameOver = false;
    private final int WIDTH = 600; //width of the park
    private final int HEIGHT = 600; //height of the park
    private int segmentSize = 20; //size of one grid


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
        g1d.drawImage(img, 60, 80,segmentSize,segmentSize, null);
        Building starterRoad = new Building("ROAD",0,0,60, 80, segmentSize, segmentSize);
        buildings.add(starterRoad);

        /*Entrance*/
        try {
            img = ImageIO.read(new File("data\\images\\entrance.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(img, segmentSize, 0,segmentSize*5,segmentSize*6, null);


        for (int i = 0; i < buildings.size(); i++) {

            try {
                img = ImageIO.read(new File("data\\images\\"+ buildings.get(i).getBuildingsImages() +".png"));
                Graphics2D g3d = (Graphics2D) g;
                g3d.drawImage(img, buildings.get(i).getLocation_X(), buildings.get(i).getLocation_Y(), buildings.get(i).getBuildingsSizesA(),buildings.get(i).getBuildingsSizesB(), null);
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
                int i = 0;
                while(i < buildings.size() && !canBuild){
                    if(buildings.get(i).getBuildingsImages().equals("ROAD")) {
                        if ((x + segmentSize) - ((x + segmentSize) % segmentSize) == buildings.get(i).getLocation_X()
                                && (y - (y % segmentSize)) == buildings.get(i).getLocation_Y()
                                || x - (x % segmentSize) == buildings.get(i).getLocation_X()
                                && ((y + segmentSize) - ((y + segmentSize) % segmentSize)) == buildings.get(i).getLocation_Y()
                                || (x - segmentSize) - ((x - segmentSize) % segmentSize) == buildings.get(i).getLocation_X()
                                && (y - (y % segmentSize)) == buildings.get(i).getLocation_Y()
                                || x - (x % segmentSize) == buildings.get(i).getLocation_X()
                                && ((y - segmentSize) - ((y - segmentSize) % segmentSize)) == buildings.get(i).getLocation_Y())
                                {
                                    canBuild = true;
                                }
                        i++;
                    }
                }

                if(canBuild){
                    System.out.println("UT EPULT");System.out.println(x + "," + y);//these co-ords are relative to the component
                    buildings.add(new Building("ROAD",0,10,x - (x % segmentSize), y - (y % segmentSize), segmentSize, segmentSize));
                    //System.out.println(x - (x % segmentSize) + " " + (y - (y % segmentSize)));
                    repaint();
                }


            }

            if (ThemeParkGUI.selected_ge.equals(EGeneralEquipment.BUSH)) {
                x = e.getX();
                y = e.getY();

                System.out.println("BOKOR EPULT");
                buildings.add(new Building("BUSH",0,10,x - (x % segmentSize), y - (y % segmentSize), segmentSize, segmentSize));
                System.out.println(x + "," + y);//these co-ords are relative to the component

                repaint();
            }

            if (ThemeParkGUI.selected_ge.equals(EGeneralEquipment.BIN)) {
                x = e.getX();
                y = e.getY();

                canBuild = false;
                int i = 0;
                while(i < buildings.size() && !canBuild){
                    if(buildings.get(i).getBuildingsImages().equals("ROAD")) {
                        if ((x + segmentSize) - ((x + segmentSize) % segmentSize) == buildings.get(i).getLocation_X()
                                && (y - (y % segmentSize)) == buildings.get(i).getLocation_Y()
                                || x - (x % segmentSize) == buildings.get(i).getLocation_X()
                                && ((y + segmentSize) - ((y + segmentSize) % segmentSize)) == buildings.get(i).getLocation_Y()
                                || (x - segmentSize) - ((x - segmentSize) % segmentSize) == buildings.get(i).getLocation_X()
                                && (y - (y % segmentSize)) == buildings.get(i).getLocation_Y()
                                || x - (x % segmentSize) == buildings.get(i).getLocation_X()
                                && ((y - segmentSize) - ((y - segmentSize) % segmentSize)) == buildings.get(i).getLocation_Y())
                        {
                            canBuild = true;
                        }
                        i++;
                    }
                }

                if(canBuild){
                    System.out.println("KUKA EPULT");
                    System.out.println(x + "," + y);//these co-ords are relative to the component
                    buildings.add(new Building("BIN",0,10,x - (x % segmentSize), y - (y % segmentSize), segmentSize, segmentSize));

                    repaint();
                }

            }

            if (ThemeParkGUI.selected_ge.equals(EGeneralEquipment.TREE)) {
                x = e.getX();
                y = e.getY();

                System.out.println("FA EPULT");
                System.out.println(x + "," + y);//these co-ords are relative to the component
                buildings.add(new Building("TREE",0,10,x - (x % segmentSize), y - (y % segmentSize), segmentSize*2, segmentSize*2));
                repaint();
            }
        }


        if (!ThemeParkGUI.selected_ge.equals(EGeneralEquipment.NOTHING)) {
            if (ThemeParkGUI.selected_ge.equals(EGeneralEquipment.ROLLERCOASTER)) {
                x = e.getX();
                y = e.getY();

                System.out.println("RC EPULT");
                System.out.println(x + "," + y);//these co-ords are relative to the component
                buildings.add(new Game("rollercoaster",0,1000,x - (x % segmentSize), y - (y % segmentSize), segmentSize*6, segmentSize*4));
                repaint();
            }

            if (ThemeParkGUI.selected_ge.equals(EGeneralEquipment.TRAIN)) {
                x = e.getX();
                y = e.getY();

                System.out.println("TRAIN EPULT");
                System.out.println(x + "," + y);//these co-ords are relative to the component
                buildings.add(new Game("TRAIN",0,800 ,x - (x % segmentSize), y - (y % segmentSize), segmentSize*4, segmentSize*4));
                repaint();
            }

            if (ThemeParkGUI.selected_ge.equals(EGeneralEquipment.WATERPARK)) {
                x = e.getX();
                y = e.getY();

                System.out.println("WP EPULT");
                System.out.println(x + "," + y);//these co-ords are relative to the component
                buildings.add(new Game("WATERPARK",0,1000 ,x - (x % segmentSize), y - (y % segmentSize), segmentSize*6, segmentSize*4));
                repaint();
            }

            if (ThemeParkGUI.selected_ge.equals(EGeneralEquipment.WHEEL)) {
                x = e.getX();
                y = e.getY();

                System.out.println("WHEEL EPULT");
                System.out.println(x + "," + y);//these co-ords are relative to the component
                buildings.add(new Game("WHEEL",0,1500 ,x - (x % segmentSize) , y - (y % segmentSize), segmentSize*6, segmentSize*6));


                repaint();
            }

            if (ThemeParkGUI.selected_ge.equals(EGeneralEquipment.SLIDE)) {
                x = e.getX();
                y = e.getY();

                System.out.println("SLIDE EPULT");
                System.out.println(x + "," + y);//these co-ords are relative to the component
                buildings.add(new Game("SLIDE",0,800 ,x - (x % segmentSize), y - (y % segmentSize), segmentSize*4, segmentSize*4));
                repaint();
            }

            if (ThemeParkGUI.selected_ge.equals(EGeneralEquipment.RESTAURANT)) {
                x = e.getX();
                y = e.getY();

                System.out.println("RESTAURANT EPULT");
                System.out.println(x + "," + y);//these co-ords are relative to the component
                buildings.add(new Restaurant("RESTAURANT",0,600 ,x - (x % segmentSize), y - (y % segmentSize), segmentSize*3, segmentSize*2));

                repaint();
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
