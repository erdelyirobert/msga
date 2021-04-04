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
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;




/**
 * The type Tp board.
 */
public class TPBoard {
    private final JPanel boardPanel;
    Color clr1 = new Color(0, 153, 0);
    ArrayList<Building> buildings = new ArrayList<Building>();
    Image img;
    private Board board;


    public TPBoard(int x, int y) throws IOException {
        boardPanel = new JPanel();
        board = new Board(x, y);
        boardPanel.setLayout(new GridLayout(board.getWIDTH(), board.getHEIGHT()));


        boardPanel.addMouseListener(new MouseAdapter() {// provides empty implementation of all
            // MouseListener`s methods, allowing us to
            // override only those which interests us
            @Override //I override only one method for presentation
            public void mousePressed(MouseEvent e) {
                int x;
                int y;

                if (!ThemeParkGUI.selected_ge.equals(EGeneralEquipment.NOTHING)) {
                    if (ThemeParkGUI.selected_ge.equals(EGeneralEquipment.ROAD)) {
                        x = e.getX();
                        y = e.getY();

                        System.out.println("UT EPULT");
                        System.out.println(x + "," + y);//these co-ords are relative to the component
                        buildings.add(new GeneralEquipment(EGeneralEquipment.ROAD, false, 0, 1, x, y, 1, 1));
                    }

                    if (ThemeParkGUI.selected_ge.equals(EGeneralEquipment.BUSH)) {
                        x = e.getX();
                        y = e.getY();

                        System.out.println("BOKOR EPULT");
                        System.out.println(x + "," + y);//these co-ords are relative to the component
                        buildings.add(new GeneralEquipment(EGeneralEquipment.BUSH, true, 1, 1, x, y, 1, 1));
                    }

                    if (ThemeParkGUI.selected_ge.equals(EGeneralEquipment.BIN)) {
                        x = e.getX();
                        y = e.getY();

                        System.out.println("KUKA EPULT");
                        System.out.println(x + "," + y);//these co-ords are relative to the component
                    }

                    if (ThemeParkGUI.selected_ge.equals(EGeneralEquipment.TREE)) {
                        x = e.getX();
                        y = e.getY();

                        System.out.println("FA EPULT");
                        System.out.println(x + "," + y);//these co-ords are relative to the component
                    }
                }


                if (!ThemeParkGUI.selected_game.equals(EGeneralEquipment.NOTHING)) {
                    if (ThemeParkGUI.selected_game.equals(EGames.ROLLERCOASTER)) {
                        x = e.getX();
                        y = e.getY();

                        System.out.println("RC EPULT");
                        System.out.println(x + "," + y);//these co-ords are relative to the component
                    }

                    if (ThemeParkGUI.selected_game.equals(EGames.TRAIN)) {
                        x = e.getX();
                        y = e.getY();

                        System.out.println("TRAIN EPULT");
                        System.out.println(x + "," + y);//these co-ords are relative to the component
                    }

                    if (ThemeParkGUI.selected_game.equals(EGames.WATERPARK)) {
                        x = e.getX();
                        y = e.getY();

                        System.out.println("WP EPULT");
                        System.out.println(x + "," + y);//these co-ords are relative to the component
                    }

                    if (ThemeParkGUI.selected_game.equals(EGames.WHEEL)) {
                        x = e.getX();
                        y = e.getY();

                        System.out.println("WHEEL EPULT");
                        System.out.println(x + "," + y);//these co-ords are relative to the component
                    }

                    if (ThemeParkGUI.selected_game.equals(EGames.SLIDE)) {
                        x = e.getX();
                        y = e.getY();

                        System.out.println("SLIDE EPULT");
                        System.out.println(x + "," + y);//these co-ords are relative to the component
                    }

                    if (ThemeParkGUI.selected_game.equals(EGames.RESTAURANT)) {
                        x = e.getX();
                        y = e.getY();

                        System.out.println("RESTAURANT EPULT");
                        System.out.println(x + "," + y);//these co-ords are relative to the component
                    }
                }

            }
        });
    }

    public JPanel getBoardPanel() {
        return boardPanel;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        //TODO: ROAD int x = buildings.getX(ROAD);
        //utolsó building adatait lekérjük és azt írjuk ki
        //Enum type,boolean inConstruction, int constructionTime, int buildPrice, int location_X, int location_Y, int range
        //   path + toString(ENUMÉRTÉK) + .jpg

        Enum e = buildings.get(buildings.size() - 1).getType();
        int x = buildings.get(buildings.size() - 1).getLocation_X();
        int y = buildings.get(buildings.size() - 1).getLocation_Y();
        int a = buildings.get(buildings.size() - 1).getA();
        int b = buildings.get(buildings.size() - 1).getB();
        /*
        BufferedImage myPicture = ImageIO.read(new File("path-to-file"));
        JLabel picLabel = new JLabel(new ImageIcon(myPicture));
        add(picLabel);
        MyEnum e = Enum.valueOf(Enum.e.toString());


        */
        try {
            img = ImageIO.read(new File("data\\images\\"+e.toString() + ".png"));
        } catch (IOException f) {
            System.out.println("error");
            f.printStackTrace();
        }
        //Image img = ImageIO.read(new File("data\\images\\"+e.toString() + ".png"));


        g.drawImage(img, x, y, a, b, this);

    }

    /*public class clickListener extends JPanel implements MouseListener {

        private int x;
        private int y;


        @Override
        public void mouseClicked(MouseEvent e) {
            if (ThemeParkGUI.selected_game.equals("road")) {
                x = e.getX();
                y = e.getY();
                System.out.println("azert irok");
                System.out.println(x + "," + y);//these co-ords are relative to the component
            }
        }


        @Override
        public void mousePressed(MouseEvent e) {
            if (ThemeParkGUI.selected_game.equals("road")) {
                x = e.getX();
                y = e.getY();
                System.out.println("azert irok");
                System.out.println(x + "," + y);//these co-ords are relative to the component
            }
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
    }*/


}
