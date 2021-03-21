package board;

import gui.ThemeParkGUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class TPBoard {
    private final JButton[][] buttons;
    private Board board;
    private final JPanel boardPanel;
    Color clr1 = new Color(0,153,0);

    public TPBoard(int x, int y) {
        boardPanel = new JPanel();
        board = new Board(x, y);
        boardPanel.setLayout(new GridLayout(board.getSizeOfBoardX(), board.getSizeOfBoardY()));
        buttons = new JButton[board.getSizeOfBoardX()][board.getSizeOfBoardY()];
        for (int i = 0; i < board.getSizeOfBoardX(); ++i) {
            for (int j = 0; j < board.getSizeOfBoardY(); ++j) {
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

                if(ThemeParkGUI.selected_game.equals("road")){
                    buttons[x][y].setBackground(Color.gray);
                }


        }



    }

    public JButton[][] getButtons() {
        return buttons;
    }






}
