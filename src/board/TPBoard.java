package board;

import ThemePark.Building;
import ThemePark.EGeneralEquipment;
import ThemePark.GeneralEquipment;
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
    public int a;
    public int b;
    public String type;
    public boolean inConstruction;
    public int buildPrice;
    public int constructionTime;
    public boolean canBuild;
    Color clr1 = new Color(0, 153, 0);
    Image img = null;
    ArrayList<Building> buildings = new ArrayList<Building>();
    ArrayList<Integer> roadX = new ArrayList<Integer>();
    ArrayList<Integer> roadY = new ArrayList<Integer>();
    ArrayList<Integer> imgX = new ArrayList<Integer>();
    ArrayList<Integer> imgY = new ArrayList<Integer>();
    ArrayList<String> buildingsImages = new ArrayList<String>();
    ArrayList<Integer> buildingsSizesA = new ArrayList<Integer>();
    ArrayList<Integer> buildingsSizesB = new ArrayList<Integer>();
    private boolean isGameOver = false;
    private final int WIDTH = 600; //width of the park
    private final int HEIGHT = 600; //height of the park
    private int segmentSize = 20; //size of one grid
    public Image startgame;

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

        System.out.println(type);

        /**
         * Starting road
         */
        roadX.add(60);
        roadY.add(80);

        /*g.setColor(Color.GRAY);
        g.fillRect(60, 80, segmentSize, segmentSize);*/

        try {
            img = ImageIO.read(new File("data\\images\\ROAD.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Graphics2D g1d = (Graphics2D) g;
        g1d.drawImage(img, 60, 80,segmentSize,segmentSize, null);


        /*Entrance*/
        try {
            img = ImageIO.read(new File("data\\images\\entrance.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(img, segmentSize, 0,segmentSize*5,segmentSize*6, null);


        for (int i = 0; i < imgX.size(); i++) {

            try {
                img = ImageIO.read(new File("data\\images\\"+ buildingsImages.get(i) +".png"));
                Graphics2D g3d = (Graphics2D) g;
                g3d.drawImage(img, imgX.get(i), imgY.get(i),buildingsSizesA.get(i),buildingsSizesB.get(i), null);

            } catch (IOException f) {
                System.out.println("error");
                f.printStackTrace();
            }
            //System.out.println(buildingsSizesA.get(i));
            //System.out.println(buildingsSizesB.get(i));
        }

        //System.out.println("sikerült belépnem a paint osztalyba");
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
                //System.out.println( canRoad);

                /*for(int i = 0; i < roadX.size(); i++){
                    System.out.println(roadX.get(i));
                }*/
                canBuild = false;
                int i = 0;
                while(i < roadX.size() && !canBuild){
                    if((x + segmentSize) - ((x + segmentSize) % segmentSize) == roadX.get(i)
                            && (y - (y % segmentSize)) == roadY.get(i)
                    || x - (x % segmentSize) == roadX.get(i)
                            && ((y + segmentSize) - ((y + segmentSize) % segmentSize)) == roadY.get(i)
                    ||(x - segmentSize) - ((x - segmentSize) % segmentSize) == roadX.get(i)
                            && (y - (y % segmentSize)) == roadY.get(i)
                    ||x - (x % segmentSize) == roadX.get(i)
                            && ((y - segmentSize) - ((y - segmentSize) % segmentSize)) == roadY.get(i)){
                        canBuild = true;
                    }
                    //System.out.println( canRoad);
                    i++;
                }

                if(canBuild){
                    budget = budget - 10;
                    a = segmentSize;
                    b = segmentSize;
                    inConstruction = false;
                    constructionTime = 0;
                    type = "ROAD";

                    buildingsImages.add("ROAD");

                    buildingsSizesA.add(a);
                    buildingsSizesB.add(b);

                    imgX.add(x - (x % segmentSize));
                    imgY.add(y - (y % segmentSize));

                    roadX.add(x - (x % segmentSize));
                    roadY.add(y - (y % segmentSize));
                    //updateMoneyLabel();

                    System.out.println("UT EPULT");System.out.println(x + "," + y);//these co-ords are relative to the component
                    //buildings.add(new GeneralEquipment(EGeneralEquipment.ROAD, false, 0, 1, x, y, segmentSize, segmentSize));
                    //System.out.println(x - (x % segmentSize) + " " + (y - (y % segmentSize)));
                    repaint();
                }


            }

            if (ThemeParkGUI.selected_ge.equals(EGeneralEquipment.BUSH)) {
                x = e.getX();
                y = e.getY();

                imgX.add(x - (x % segmentSize));
                imgY.add(y - (y % segmentSize));

                budget = budget - 10;
                a = segmentSize;
                b = segmentSize;
                inConstruction = false;
                constructionTime = 0;
                type = "BUSH";

                buildingsImages.add("BUSH");

                buildingsSizesA.add(a);
                buildingsSizesB.add(b);

                System.out.println("BOKOR EPULT");
                System.out.println(x + "," + y);//these co-ords are relative to the component
                buildings.add(new GeneralEquipment(EGeneralEquipment.BUSH, true, 1, 1, x, y, segmentSize, segmentSize));



                repaint();
            }

            if (ThemeParkGUI.selected_ge.equals(EGeneralEquipment.BIN)) {
                x = e.getX();
                y = e.getY();

                canBuild = false;
                int i = 0;
                while(i < roadX.size() && !canBuild){
                    if((x + segmentSize) - ((x + segmentSize) % segmentSize) == roadX.get(i)
                            && (y - (y % segmentSize)) == roadY.get(i)
                            || x - (x % segmentSize) == roadX.get(i)
                            && ((y + segmentSize) - ((y + segmentSize) % segmentSize)) == roadY.get(i)
                            ||(x - segmentSize) - ((x - segmentSize) % segmentSize) == roadX.get(i)
                            && (y - (y % segmentSize)) == roadY.get(i)
                            ||x - (x % segmentSize) == roadX.get(i)
                            && ((y - segmentSize) - ((y - segmentSize) % segmentSize)) == roadY.get(i)){
                        canBuild = true;
                    }
                    //System.out.println( canRoad);
                    i++;
                }

                if(canBuild){
                    imgX.add(x - (x % segmentSize));
                    imgY.add(y - (y % segmentSize));

                    budget = budget - 10;
                    a = segmentSize;
                    b = segmentSize;
                    inConstruction = false;
                    constructionTime = 0;
                    type = "BIN";

                    buildingsImages.add("BIN");

                    buildingsSizesA.add(a);
                    buildingsSizesB.add(b);

                    System.out.println("KUKA EPULT");
                    System.out.println(x + "," + y);//these co-ords are relative to the component


                    repaint();
                }

            }

            if (ThemeParkGUI.selected_ge.equals(EGeneralEquipment.TREE)) {
                x = e.getX();
                y = e.getY();

                imgX.add(x - (x % segmentSize));
                imgY.add(y - (y % segmentSize));

                budget = budget - 10;
                a = segmentSize*2;
                b = segmentSize*2;
                inConstruction = false;
                constructionTime = 0;
                type = "TREE";
                buildingsImages.add("TREE");
                buildingsSizesA.add(a);
                buildingsSizesB.add(b);


                System.out.println("FA EPULT");
                System.out.println(x + "," + y);//these co-ords are relative to the component
                repaint();
            }
        }


        if (!ThemeParkGUI.selected_ge.equals(EGeneralEquipment.NOTHING)) {
            if (ThemeParkGUI.selected_ge.equals(EGeneralEquipment.ROLLERCOASTER)) {
                x = e.getX();
                y = e.getY();

                imgX.add(x - (x % segmentSize));
                imgY.add(y - (y % segmentSize));

                budget = budget - 1000;
                a = segmentSize*6;
                b = segmentSize*4;
                inConstruction = false;
                constructionTime = 0;
                type = "rollercoaster";
                buildingsImages.add("rollercoaster");
                buildingsSizesA.add(a);
                buildingsSizesB.add(b);

                System.out.println("RC EPULT");
                System.out.println(x + "," + y);//these co-ords are relative to the component



                repaint();
            }

            if (ThemeParkGUI.selected_ge.equals(EGeneralEquipment.TRAIN)) {
                x = e.getX();
                y = e.getY();

                imgX.add(x - (x % segmentSize));
                imgY.add(y - (y % segmentSize));

                budget = budget - 800;
                a = segmentSize*4;
                b = segmentSize*4;
                inConstruction = false;
                constructionTime = 0;
                type = "TRAIN";
                buildingsImages.add("TRAIN");
                buildingsSizesA.add(a);
                buildingsSizesB.add(b);

                System.out.println("TRAIN EPULT");
                System.out.println(x + "," + y);//these co-ords are relative to the component

                repaint();
            }

            if (ThemeParkGUI.selected_ge.equals(EGeneralEquipment.WATERPARK)) {
                x = e.getX();
                y = e.getY();

                imgX.add(x - (x % segmentSize));
                imgY.add(y - (y % segmentSize));

                budget = budget - 1000;
                a = segmentSize*6;
                b = segmentSize*4;
                inConstruction = false;
                constructionTime = 0;
                type = "WATERPARK";
                buildingsImages.add("WATERPARK");
                buildingsSizesA.add(a);
                buildingsSizesB.add(b);

                System.out.println("WP EPULT");
                System.out.println(x + "," + y);//these co-ords are relative to the component



                repaint();
            }

            if (ThemeParkGUI.selected_ge.equals(EGeneralEquipment.WHEEL)) {
                x = e.getX();
                y = e.getY();

                imgX.add(x - (x % segmentSize));
                imgY.add(y - (y % segmentSize));

                budget = budget - 1500;
                a = segmentSize*6;
                b = segmentSize*6;
                inConstruction = false;
                constructionTime = 0;
                type = "WHEEL";
                buildingsImages.add("WHEEL");
                buildingsSizesA.add(a);
                buildingsSizesB.add(b);

                System.out.println("WHEEL EPULT");
                System.out.println(x + "," + y);//these co-ords are relative to the component


                repaint();
            }

            if (ThemeParkGUI.selected_ge.equals(EGeneralEquipment.SLIDE)) {
                x = e.getX();
                y = e.getY();

                imgX.add(x - (x % segmentSize));
                imgY.add(y - (y % segmentSize));

                budget = budget - 800;
                a = segmentSize*4;
                b = segmentSize*4;
                inConstruction = false;
                constructionTime = 0;
                type = "SLIDE";
                buildingsImages.add("SLIDE");
                buildingsSizesA.add(a);
                buildingsSizesB.add(b);

                System.out.println("SLIDE EPULT");
                System.out.println(x + "," + y);//these co-ords are relative to the component


                repaint();
            }

            if (ThemeParkGUI.selected_ge.equals(EGeneralEquipment.RESTAURANT)) {
                x = e.getX();
                y = e.getY();

                imgX.add(x - (x % segmentSize));
                imgY.add(y - (y % segmentSize));

                budget = budget - 600;
                a = segmentSize*3;
                b = segmentSize*2;
                inConstruction = false;
                constructionTime = 0;
                type = "RESTAURANT";
                buildingsImages.add("RESTAURANT");
                buildingsSizesA.add(a);
                buildingsSizesB.add(b);

                System.out.println("RESTAURANT EPULT");
                System.out.println(x + "," + y);//these co-ords are relative to the component

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
