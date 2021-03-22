package board;

import ThemePark.FieldValue;
import gui.ThemeParkGUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLOutput;
import javax.imageio.ImageIO;
import javax.swing.*;


public class TPBoard {
    private final JButton[][] buttons;
    private Board board;
    private final JPanel boardPanel;
    Color clr1 = new Color(0,153,0);

    public TPBoard(int x, int y) {
        boardPanel = new JPanel();
        board = new Board(x+2, y+2);
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
                buttons[i][j].setPreferredSize(new Dimension(20, 20));
                buttons[i][j].setBackground(clr1);
                buttons[i][j].setName("EMPTY");
                boardPanel.add(buttons[i][j]);
            }
        }
        buttons[3][1].setBackground(Color.gray);
        buttons[3][1].setName("NOT_EMPTY");
    }
    public JPanel getBoardPanel() {
        return boardPanel;
    }

    class ButtonListener implements ActionListener {

        private int x;
        private int y;

        public ButtonListener(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            if (ThemeParkGUI.selected_game.equals("road")) {
                if(buttons[x][y].getName().equals("EMPTY")){
                    if (buttons[x - 1][y].getBackground() == Color.gray
                            || buttons[x][y - 1].getBackground() == Color.gray
                            || buttons[x + 1][y].getBackground() == Color.gray
                            || buttons[x][y + 1].getBackground() == Color.gray
                            || buttons[-1][y].getBackground() == Color.gray){
                        buttons[x][y].setBackground(Color.gray);
                        buttons[x][y].setName("NOT_EMPTY");

                        //buttons[x][y].setBorder(BorderFactory.createLineBorder(Color.gray));
                        //buttons[x][y].setBorder(BorderFactory.createBevelBorder(0, Color.gray, Color.gray, Color.gray, Color.gray)); //Four Colors Ou
                    }
                }
            }
            if(ThemeParkGUI.selected_game.equals("bin")){
                if(buttons[x][y].getName().equals("EMPTY")){
                    if(buttons[x-1][y].getBackground().equals(Color.gray)
                        || buttons[x+1][y].getBackground().equals(Color.gray)
                        || buttons[x][y+1].getBackground().equals(Color.gray)
                        || buttons[x][y-1].getBackground().equals(Color.gray))
                    {
                        buttons[x][y].setText("BIN");
                        buttons[x][y].setName("NOT_EMPTY");
                        /*try {
                            Image img = ImageIO.read(getClass().getResource(""));
                            buttons[x][y].setIcon(new ImageIcon(img));
                        } catch (Exception ex) {
                            System.out.println(ex);
                        }*/
                    }
                }
            }
            if(ThemeParkGUI.selected_game.equals("bush")){
                if(buttons[x][y].getName().equals("EMPTY")){
                    buttons[x][y].setText("BUSH");
                    buttons[x][y].setName("NOT_EMPTY");
                }
            }
            if(ThemeParkGUI.selected_game.equals("tree")){
                if(buttons[x][y].getName().equals("EMPTY")){
                    buttons[x][y].setText("TREE");
                    buttons[x][y].setName("NOT_EMPTY");
                }
            }
            if(ThemeParkGUI.selected_game.equals("restaurant")){
                if(buttons[x][y].getName().equals("EMPTY")){
                    if(buttons[x-1][y].getBackground().equals(Color.gray)
                            || buttons[x+1][y].getBackground().equals(Color.gray)
                            || buttons[x][y+1].getBackground().equals(Color.gray)
                            || buttons[x][y-1].getBackground().equals(Color.gray))
                    {
                        buttons[x][y].setText("RESTAURANT");
                        buttons[x][y].setName("NOT_EMPTY");
                    }
                }
            }

            if(ThemeParkGUI.selected_game.equals("wheel")) {
                if (buttons[x][y].getName().equals("EMPTY")) {
                    if (buttons[x - 1][y].getBackground().equals(Color.gray)
                            || buttons[x + 1][y].getBackground().equals(Color.gray)
                            || buttons[x][y + 1].getBackground().equals(Color.gray)
                            || buttons[x][y - 1].getBackground().equals(Color.gray))
                    {
                                buttons[x][y].setText("WHEEL");
                                buttons[x][y].setName("NOT_EMPTY");
                    }
                }
            }
            if(ThemeParkGUI.selected_game.equals("train")){
                if(buttons[x][y].getName().equals("EMPTY")){
                    if(buttons[x-1][y].getBackground().equals(Color.gray)
                            || buttons[x+1][y].getBackground().equals(Color.gray)
                            || buttons[x][y+1].getBackground().equals(Color.gray)
                            || buttons[x][y-1].getBackground().equals(Color.gray))
                    {
                        buttons[x][y].setText("TRAIN");
                        buttons[x][y].setName("NOT_EMPTY");
                    }
                }
            }
            if(ThemeParkGUI.selected_game.equals("rollercoaster")){
                if(buttons[x][y].getName().equals("EMPTY")){
                    if(buttons[x-1][y].getBackground().equals(Color.gray)
                            || buttons[x+1][y].getBackground().equals(Color.gray)
                            || buttons[x][y+1].getBackground().equals(Color.gray)
                            || buttons[x][y-1].getBackground().equals(Color.gray))
                    {
                        buttons[x][y].setText("ROLLERCOASTER");
                        buttons[x][y].setName("NOT_EMPTY");
                    }
                }
            }
            if(ThemeParkGUI.selected_game.equals("waterpark")){
                if(buttons[x][y].getName().equals("EMPTY")){
                    if(buttons[x-1][y].getBackground().equals(Color.gray)
                            || buttons[x+1][y].getBackground().equals(Color.gray)
                            || buttons[x][y+1].getBackground().equals(Color.gray)
                            || buttons[x][y-1].getBackground().equals(Color.gray))
                    {
                        buttons[x][y].setText("WATERPARK");
                        buttons[x][y].setName("NOT_EMPTY");
                    }
                }
            }
            if(ThemeParkGUI.selected_game.equals("slide")){
                if(buttons[x][y].getName().equals("EMPTY")){
                    if(buttons[x-1][y].getBackground().equals(Color.gray)
                            || buttons[x+1][y].getBackground().equals(Color.gray)
                            || buttons[x][y+1].getBackground().equals(Color.gray)
                            || buttons[x][y-1].getBackground().equals(Color.gray))
                    {
                        buttons[x][y].setText("SLIDE");
                        buttons[x][y].setName("NOT_EMPTY");
                    }
                }
            }
            }//end of actionperform
        }


    public JButton[][] getButtons() {
        return buttons;
    }
}
