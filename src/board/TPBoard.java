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




/**
 * The type Tp board.
 */
public class TPBoard extends JPanel implements MouseListener{
    public  int budget = 10000;
    private boolean isGameOver = false ;
    Color clr1 = new Color(0, 153, 0);
    private int WIDTH = 600; //width of the park
    private int HEIGHT = 600; //height of the park
    private int segmentSize = 10; //size of one grid
    public int x;
    public int y;
    Image img;
    ArrayList<Building> buildings = new ArrayList<Building>();


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

        System.out.println("sikerült belépnem a paint osztalyba");


    }

        @Override
        public void mousePressed (MouseEvent e){

            if (!ThemeParkGUI.selected_ge.equals(EGeneralEquipment.NOTHING)) {
                if (ThemeParkGUI.selected_ge.equals(EGeneralEquipment.ROAD)) {
                    x = e.getX();
                    y = e.getY();

                    System.out.println("UT EPULT");
                    System.out.println(x + "," + y);//these co-ords are relative to the component
                    buildings.add(new GeneralEquipment(EGeneralEquipment.ROAD, false, 0, 1, x, y, 1, 1));
                    repaint();
                }

                if (ThemeParkGUI.selected_ge.equals(EGeneralEquipment.BUSH)) {
                    x = e.getX();
                    y = e.getY();

                    System.out.println("BOKOR EPULT");
                    System.out.println(x + "," + y);//these co-ords are relative to the component
                    buildings.add(new GeneralEquipment(EGeneralEquipment.BUSH, true, 1, 1, x, y, 1, 1));
                    repaint();
                }

                if (ThemeParkGUI.selected_ge.equals(EGeneralEquipment.BIN)) {
                    x = e.getX();
                    y = e.getY();

                    System.out.println("KUKA EPULT");
                    System.out.println(x + "," + y);//these co-ords are relative to the component
                    repaint();
                }

                if (ThemeParkGUI.selected_ge.equals(EGeneralEquipment.TREE)) {
                    x = e.getX();
                    y = e.getY();

                    System.out.println("FA EPULT");
                    System.out.println(x + "," + y);//these co-ords are relative to the component
                    repaint();
                }
            }


            if (!ThemeParkGUI.selected_game.equals(EGeneralEquipment.NOTHING)) {
                if (ThemeParkGUI.selected_game.equals(EGames.ROLLERCOASTER)) {
                    x = e.getX();
                    y = e.getY();

                    System.out.println("RC EPULT");
                    System.out.println(x + "," + y);//these co-ords are relative to the component
                    repaint();
                }

                if (ThemeParkGUI.selected_game.equals(EGames.TRAIN)) {
                    x = e.getX();
                    y = e.getY();

                    System.out.println("TRAIN EPULT");
                    System.out.println(x + "," + y);//these co-ords are relative to the component
                    repaint();
                }

                if (ThemeParkGUI.selected_game.equals(EGames.WATERPARK)) {
                    x = e.getX();
                    y = e.getY();

                    System.out.println("WP EPULT");
                    System.out.println(x + "," + y);//these co-ords are relative to the component
                    repaint();
                }

                if (ThemeParkGUI.selected_game.equals(EGames.WHEEL)) {
                    x = e.getX();
                    y = e.getY();

                    System.out.println("WHEEL EPULT");
                    System.out.println(x + "," + y);//these co-ords are relative to the component
                    repaint();
                }

                if (ThemeParkGUI.selected_game.equals(EGames.SLIDE)) {
                    x = e.getX();
                    y = e.getY();

                    System.out.println("SLIDE EPULT");
                    System.out.println(x + "," + y);//these co-ords are relative to the component
                    repaint();
                }

                if (ThemeParkGUI.selected_game.equals(EGames.RESTAURANT)) {
                    x = e.getX();
                    y = e.getY();

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
