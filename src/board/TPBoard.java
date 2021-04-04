package board;

import ThemePark.*;
import gui.ThemeParkGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;


/**
 * The type Tp board.
 */
public class TPBoard {
    private final JPanel boardPanel;
    Color clr1 = new Color(0, 153, 0);
    ArrayList<Building> buildings = new ArrayList<Building>();
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

                if(!ThemeParkGUI.selected_ge.equals(EGeneralEquipment.NOTHING)){
                    if(ThemeParkGUI.selected_ge.equals(EGeneralEquipment.ROAD)){
                        x = e.getX();
                        y = e.getY();

                        System.out.println("UT EPULT");
                        System.out.println(x+","+y);//these co-ords are relative to the component
                    }

                    if(ThemeParkGUI.selected_ge.equals(EGeneralEquipment.BUSH)){
                        x = e.getX();
                        y = e.getY();

                        System.out.println("BOKOR EPULT");
                        System.out.println(x+","+y);//these co-ords are relative to the component
                    }

                    if(ThemeParkGUI.selected_ge.equals(EGeneralEquipment.BIN)){
                        x = e.getX();
                        y = e.getY();

                        System.out.println("KUKA EPULT");
                        System.out.println(x+","+y);//these co-ords are relative to the component
                    }

                    if(ThemeParkGUI.selected_ge.equals(EGeneralEquipment.TREE)){
                        x = e.getX();
                        y = e.getY();

                        System.out.println("FA EPULT");
                        System.out.println(x+","+y);//these co-ords are relative to the component
                    }
                }


                if(!ThemeParkGUI.selected_game.equals(EGeneralEquipment.NOTHING)){
                    if(ThemeParkGUI.selected_game.equals(EGames.ROLLERCOASTER)){
                        x = e.getX();
                        y = e.getY();

                        System.out.println("RC EPULT");
                        System.out.println(x+","+y);//these co-ords are relative to the component
                    }

                    if(ThemeParkGUI.selected_game.equals(EGames.TRAIN)){
                        x = e.getX();
                        y = e.getY();

                        System.out.println("TRAIN EPULT");
                        System.out.println(x+","+y);//these co-ords are relative to the component
                    }

                    if(ThemeParkGUI.selected_game.equals(EGames.WATERPARK)){
                        x = e.getX();
                        y = e.getY();

                        System.out.println("WP EPULT");
                        System.out.println(x+","+y);//these co-ords are relative to the component
                    }

                    if(ThemeParkGUI.selected_game.equals(EGames.WHEEL)){
                        x = e.getX();
                        y = e.getY();

                        System.out.println("WHEEL EPULT");
                        System.out.println(x+","+y);//these co-ords are relative to the component
                    }

                    if(ThemeParkGUI.selected_game.equals(EGames.SLIDE)){
                        x = e.getX();
                        y = e.getY();

                        System.out.println("SLIDE EPULT");
                        System.out.println(x+","+y);//these co-ords are relative to the component
                    }

                    if(ThemeParkGUI.selected_game.equals(EGames.RESTAURANT)){
                        x = e.getX();
                        y = e.getY();

                        System.out.println("RESTAURANT EPULT");
                        System.out.println(x+","+y);//these co-ords are relative to the component
                    }
                }

            }
        });
    }

    public JPanel getBoardPanel() {
        return boardPanel;
    }

    public class clickListener extends JPanel implements MouseListener{

        private int x;
        private int y;


        @Override
        public void mouseClicked(MouseEvent e) {
            if(ThemeParkGUI.selected_game.equals("road")){
                x=e.getX();
                y=e.getY();
                System.out.println("azert irok");
                System.out.println(x+","+y);//these co-ords are relative to the component
            }
        }


        @Override
        public void mousePressed(MouseEvent e) {
            if(ThemeParkGUI.selected_game.equals("road")){
                x=e.getX();
                y=e.getY();
                System.out.println("azert irok");
                System.out.println(x+","+y);//these co-ords are relative to the component
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
    }
}
