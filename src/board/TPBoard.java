package board;

import ThemePark.*;
import gui.ThemeParkGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;


/**
 * The type Tp board.
 */
public class TPBoard {
    private final JButton[][] buttons;
    private final JPanel boardPanel;
    /**
     * The Clr 1.
     */
    Color clr1 = new Color(0, 153, 0);
    /**
     * The Buildings.
     */
    ArrayList<Building> buildings = new ArrayList<Building>();
    private Board board;

    /**
     * Constuctor of /**
     * * The type Tp board.
     *
     * @param x the x
     * @param y the y
     * @throws IOException the io exception
     */
    public TPBoard(int x, int y) throws IOException {
        boardPanel = new JPanel();
        board = new Board(x + 2, y + 2);
        boardPanel.setLayout(new GridLayout(board.getSizeOfBoardX(), board.getSizeOfBoardY()));
        buttons = new JButton[board.getSizeOfBoardX()][board.getSizeOfBoardY()];
        for (int i = 0; i < board.getSizeOfBoardX(); i++) {
            for (int j = 0; j < board.getSizeOfBoardY(); j++) {
                buttons[i][j] = new JButton("");
                buttons[i][j].setBorder(null);
                //buttons[i][j].setOpaque(false);
                //buttons[i][j].setBorderPainted(false);
                //buttons[i][j].setContentAreaFilled(false);
                buttons[i][j].addActionListener(new ButtonListener(i, j));
                //buttons[i][j].setPreferredSize(new Dimension(20, 20));
                buttons[i][j].setBackground(clr1);
                buttons[i][j].setName("EMPTY");
                boardPanel.add(buttons[i][j]);
            }
        }

        try {
            //Image img = ImageIO.read(getClass().getResource("data\\images\\entrance.png"));
            //grid[i][j].setIcon(new ImageIcon(getClass().getResource("smallwhite.png")));
            buttons[2][1].setContentAreaFilled(false);
          //  buttons[2][1].setIcon(new ImageIcon(getClass().getResource("enterance.png")));
            buttons[2][1].setText("ENTERANCE KÉP HELYE");
            //   buttons[2][1].setName("NOT_EMPTY");
        } catch (Exception ex) {
            System.out.println("kep beolv hiba");
            System.out.println(ex);
        }

        buttons[3][1].setBackground(Color.gray);
        buttons[3][1].setName("NOT_EMPTY");

    }

    /**
     * Gets board panel.
     *
     * @return the board panel
     */
    public JPanel getBoardPanel() {
        return boardPanel;
    }

    /**
     * Get buttons j button [ ] [ ].
     *
     * @return the j button [ ] [ ]
     */
    public JButton[][] getButtons() {
        return buttons;
    }

    /**
     * The type Button listener.
     */
    class ButtonListener implements ActionListener {

        private int x;
        private int y;

        /**
         * Instantiates a new Button listener.
         *
         * @param x the x
         * @param y the y
         */
        public ButtonListener(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            if (ThemeParkGUI.selected_game.equals("road")) {
                if (buttons[x][y].getName().equals("EMPTY")) {
                    if (buttons[x - 1][y].getBackground() == Color.gray
                            || buttons[x][y - 1].getBackground() == Color.gray
                            || buttons[x + 1][y].getBackground() == Color.gray
                            || buttons[x][y + 1].getBackground() == Color.gray
                            || buttons[-1][y].getBackground() == Color.gray) {
                        buttons[x][y].setBackground(Color.gray);
                        buttons[x][y].setName("NOT_EMPTY");

                        //buttons[x][y].setBorder(BorderFactory.createLineBorder(Color.gray));
                        //buttons[x][y].setBorder(BorderFactory.createBevelBorder(0, Color.gray, Color.gray, Color.gray, Color.gray)); //Four Colors Ou
                    }
                }
            }
            if (ThemeParkGUI.selected_game.equals("bin")) {
                if (buttons[x][y].getName().equals("EMPTY")) {
                    if (buttons[x - 1][y].getBackground().equals(Color.gray)
                            || buttons[x + 1][y].getBackground().equals(Color.gray)
                            || buttons[x][y + 1].getBackground().equals(Color.gray)
                            || buttons[x][y - 1].getBackground().equals(Color.gray)) {
                        buttons[x][y].setText("BIN");
                        buttons[x][y].setName("NOT_EMPTY");
                        buildings.add(new GeneralEquipment(EGeneralEquipment.BIN, true, 1, 1, 1, 1, 1));
                        /*try {
                            Image img = ImageIO.read(getClass().getResource(""));
                            buttons[x][y].setIcon(new ImageIcon(img));
                        } catch (Exception ex) {
                            System.out.println(ex);
                        }*/
                    }
                }
            }
            if (ThemeParkGUI.selected_game.equals("bush")) {
                if (buttons[x][y].getName().equals("EMPTY")) {
                    buttons[x][y].setText("BUSH");
                    buttons[x][y].setName("NOT_EMPTY");
                    buildings.add(new GeneralEquipment(EGeneralEquipment.BUSH, true, 1, 1, 1, 1, 1));
                }
            }
            if (ThemeParkGUI.selected_game.equals("tree")) {
                if (buttons[x][y].getName().equals("EMPTY")) {
                    buttons[x][y].setText("TREE");
                    buttons[x][y].setName("NOT_EMPTY");
                    buildings.add(new GeneralEquipment(EGeneralEquipment.TREE, true, 1, 1, 1, 1, 1));
                }
            }
            if (ThemeParkGUI.selected_game.equals("restaurant")) {
                if (buttons[x][y].getName().equals("EMPTY")) {
                    if (buttons[x - 1][y].getBackground().equals(Color.gray)
                            || buttons[x + 1][y].getBackground().equals(Color.gray)
                            || buttons[x][y + 1].getBackground().equals(Color.gray)
                            || buttons[x][y - 1].getBackground().equals(Color.gray)) {
                        buildings.add(new Restaurant(EGames.RESTAURANT, true, 1, 1, 1, 1, 1));
                        buttons[x][y].setText("RESTAURANT");
                        buttons[x][y].setName("NOT_EMPTY");
                    }
                }
            }

            if (ThemeParkGUI.selected_game.equals("wheel")) {
                if (buttons[x][y].getName().equals("EMPTY")) {
                    if (buttons[x - 1][y].getBackground().equals(Color.gray)
                            || buttons[x + 1][y].getBackground().equals(Color.gray)
                            || buttons[x][y + 1].getBackground().equals(Color.gray)
                            || buttons[x][y - 1].getBackground().equals(Color.gray)) {

                        buildings.add(new Game(EGames.WHEEL, true, 1, 1, 1, 1, 1));
                        buttons[x][y].setText("WHEEL");
                        //TODO: image instead of text
                        buttons[x][y].setName("NOT_EMPTY");

                    }
                }
            }
            if (ThemeParkGUI.selected_game.equals("train")) {
                if (buttons[x][y].getName().equals("EMPTY")) {
                    if (buttons[x - 1][y].getBackground().equals(Color.gray)
                            || buttons[x + 1][y].getBackground().equals(Color.gray)
                            || buttons[x][y + 1].getBackground().equals(Color.gray)
                            || buttons[x][y - 1].getBackground().equals(Color.gray)) {
                        buttons[x][y].setText("Train");
                        buildings.add(new Game(EGames.TRAIN, true, 1, 1, 1, 1, 1));
                        buttons[x][y].setName("NOT_EMPTY");
                    }
                }
            }
            if (ThemeParkGUI.selected_game.equals("rollercoaster")) {
                if (buttons[x][y].getName().equals("EMPTY")) {
                    if (buttons[x - 1][y].getBackground().equals(Color.gray)
                            || buttons[x + 1][y].getBackground().equals(Color.gray)
                            || buttons[x][y + 1].getBackground().equals(Color.gray)
                            || buttons[x][y - 1].getBackground().equals(Color.gray)) {
                        buttons[x][y].setText("ROLLERCOASTER");
                        buildings.add(new Game(EGames.ROLLERCOASTER, true, 1, 1, 1, 1, 1));
                        buttons[x][y].setName("NOT_EMPTY");
                    }
                }
            }
            if (ThemeParkGUI.selected_game.equals("waterpark")) {
                if (buttons[x][y].getName().equals("EMPTY")) {
                    if (buttons[x - 1][y].getBackground().equals(Color.gray)
                            || buttons[x + 1][y].getBackground().equals(Color.gray)
                            || buttons[x][y + 1].getBackground().equals(Color.gray)
                            || buttons[x][y - 1].getBackground().equals(Color.gray)) {
                        buildings.add(new Game(EGames.WATERPARK, true, 1, 1, 1, 1, 1));
                        buttons[x][y].setText("WATERPARK");
                        buttons[x][y].setName("NOT_EMPTY");
                    }
                }
            }
            if (ThemeParkGUI.selected_game.equals("slide")) {
                if (buttons[x][y].getName().equals("EMPTY")) {
                    if (buttons[x - 1][y].getBackground().equals(Color.gray)
                            || buttons[x + 1][y].getBackground().equals(Color.gray)
                            || buttons[x][y + 1].getBackground().equals(Color.gray)
                            || buttons[x][y - 1].getBackground().equals(Color.gray)) {
                        buildings.add(new Game(EGames.SLIDE, true, 1, 1, 1, 1, 1));
                        buttons[x][y].setText("SLIDE");
                        buttons[x][y].setName("NOT_EMPTY");
                    }
                }
            }
        }//end of actionperform
    }
}
