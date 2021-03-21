package board;

import gui.ThemeParkGUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLOutput;
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
                boardPanel.add(buttons[i][j]);
            }
        }
        buttons[3][1].setBackground(Color.gray);
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
                if (buttons[x - 1][y].getBackground() == Color.gray
                        || buttons[x][y - 1].getBackground() == Color.gray
                        || buttons[x + 1][y].getBackground() == Color.gray
                        || buttons[x][y + 1].getBackground() == Color.gray
                        || buttons[-1][y].getBackground() == Color.gray)
                    if (ThemeParkGUI.selected_game.equals("road")) {
                        buttons[x][y].setBackground(Color.gray);
                        System.out.println(buttons[x][y]);
                        //buttons[x][y].setBorder(BorderFactory.createLineBorder(Color.gray));
                        //buttons[x][y].setBorder(BorderFactory.createBevelBorder(0, Color.gray, Color.gray, Color.gray, Color.gray)); //Four Colors Outer Bevel
                    }
            }
        }



    public JButton[][] getButtons() {
        return buttons;
    }






}
