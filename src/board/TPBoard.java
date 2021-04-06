package board;

import ThemePark.Building;
import ThemePark.EGames;
import ThemePark.EGeneralEquipment;
import ThemePark.GeneralEquipment;
import gui.ThemeParkGUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * The type Tp board.
 */
public class TPBoard extends JPanel implements MouseListener{
    public  int budget = 10000;
    private boolean isGameOver = false ;
    Color clr1 = new Color(0, 153, 0);
    private int WIDTH = 600; //width of the park
    private int HEIGHT = 600; //height of the park
    private int segmentSize = 20; //size of one grid
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

    public boolean spaceEmpty;

    Image img = null;
    ArrayList<Building> buildings = new ArrayList<Building>();

    ArrayList<Integer> roadX = new ArrayList<Integer>();
    ArrayList<Integer> roadY = new ArrayList<Integer>();


    public TPBoard() throws IOException {
        this.addMouseListener(this);
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.startGame();
    }

   public void startGame(){
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        paint(g);
    }

    @Override
    public void paint(Graphics g){
        g.clearRect(0, 0, WIDTH, HEIGHT);
        g.setColor(clr1);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        try {
            img = ImageIO.read(new File("data\\images\\rollercoaster.png"));
        } catch (IOException f) {
            System.out.println("error");
            f.printStackTrace();
        }

        System.out.println(type);

        /**
         * Starting road
         */
        roadX.add(60);
        roadY.add(60);
        g.setColor(Color.GRAY);
        g.fillRect(60, 60, segmentSize, segmentSize);

        //g.drawImage(img, x, y, a, b, this);

        for(int i = 1; i < roadX.size(); i++) {
            g.setColor(Color.GRAY);
            g.fillRect(roadX.get(i), roadY.get(i), a, b);
        }

        System.out.println("sikerült belépnem a paint osztalyba");
    }

        @Override
        public void mousePressed (MouseEvent e){

            if (!ThemeParkGUI.selected_ge.equals(EGeneralEquipment.NOTHING)) {
                if (ThemeParkGUI.selected_ge.equals(EGeneralEquipment.ROAD)) {

                        x = e.getX();
                        y = e.getY();
                        spaceEmpty = true;

                        for(int i = 0; i < roadX.size(); i++){
                            if(roadX.get(i) == x && roadY.get(i) == y){
                                spaceEmpty = false;
                                System.out.println("soz bro");
                            }
                        }

                        if(spaceEmpty){
                            a = segmentSize ;
                            b = segmentSize ;
                            inConstruction = false;
                            constructionTime = 0;

                            roadX.add(x - (x%segmentSize));
                            roadY.add(y - (y%segmentSize));

                            //updateMoneyLabel();

                            System.out.println("UT EPULT");
                            System.out.println(x + "," + y);//these co-ords are relative to the component
                                    //buildings.add(new GeneralEquipment(EGeneralEquipment.ROAD, false, 0, 1, x, y, segmentSize, segmentSize));
                            repaint();
                        }
                }

                if (ThemeParkGUI.selected_ge.equals(EGeneralEquipment.BUSH)) {
                    x = e.getX();
                    y = e.getY();
                    type = "BUSH";

                    System.out.println("BOKOR EPULT");
                    System.out.println(x + "," + y);//these co-ords are relative to the component
                    buildings.add(new GeneralEquipment(EGeneralEquipment.BUSH, true, 1, 1, x, y, 1, 1));
                    repaint();
                }

                if (ThemeParkGUI.selected_ge.equals(EGeneralEquipment.BIN)) {
                    x = e.getX();
                    y = e.getY();
                    type = "BIN";

                    System.out.println("KUKA EPULT");
                    System.out.println(x + "," + y);//these co-ords are relative to the component
                    repaint();
                }

                if (ThemeParkGUI.selected_ge.equals(EGeneralEquipment.TREE)) {
                    x = e.getX();
                    y = e.getY();
                    type = "TREE";

                    System.out.println("FA EPULT");
                    System.out.println(x + "," + y);//these co-ords are relative to the component
                    repaint();
                }
            }


            if (!ThemeParkGUI.selected_game.equals(EGeneralEquipment.NOTHING)) {
                if (ThemeParkGUI.selected_game.equals(EGames.ROLLERCOASTER)) {
                    x = e.getX();
                    y = e.getY();
                    type = "rollercoaster";

                    System.out.println("RC EPULT");
                    System.out.println(x + "," + y);//these co-ords are relative to the component
                    repaint();
                }

                if (ThemeParkGUI.selected_game.equals(EGames.TRAIN)) {
                    x = e.getX();
                    y = e.getY();
                    type = "TRAIN";

                    System.out.println("TRAIN EPULT");
                    System.out.println(x + "," + y);//these co-ords are relative to the component
                    repaint();
                }

                if (ThemeParkGUI.selected_game.equals(EGames.WATERPARK)) {
                    x = e.getX();
                    y = e.getY();
                    type = "WATERPARK";

                    System.out.println("WP EPULT");
                    System.out.println(x + "," + y);//these co-ords are relative to the component
                    repaint();
                }

                if (ThemeParkGUI.selected_game.equals(EGames.WHEEL)) {
                    x = e.getX();
                    y = e.getY();
                    type = "WHEEL";

                    System.out.println("WHEEL EPULT");
                    System.out.println(x + "," + y);//these co-ords are relative to the component
                    repaint();
                }

                if (ThemeParkGUI.selected_game.equals(EGames.SLIDE)) {
                    x = e.getX();
                    y = e.getY();
                    type = "SLIDE";

                    System.out.println("SLIDE EPULT");
                    System.out.println(x + "," + y);//these co-ords are relative to the component
                    repaint();
                }

                if (ThemeParkGUI.selected_game.equals(EGames.RESTAURANT)) {
                    x = e.getX();
                    y = e.getY();
                    type = "RESTAURANT";

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
