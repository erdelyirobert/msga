package gui;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;


public class MainMenu {

    //Table
    final int LabelPosition_X = 10;
    final int LabelPosition_Y = 30;
    final int StartGameButtonPostion_X = 10;
    JFrame Menu = new JFrame("Main menu");
    ArrayList<String> mapList = new ArrayList<String>();
    JComboBox<String> lvlList;
    JButton Start = new JButton("Start game");
    JButton ScoreBoard = new JButton("Scoreboard");
    int menuWidth = 100; //A szélessége egy elemnek
    int menuHeight = 30;//A magassága egy elemnek
    int DOWNY = 30; //Mennyivel menjen lejebb egy elem
    final int TextFieldPosition_X = menuWidth + DOWNY;
    final int TextFieldPosition_Y = DOWNY;
    final int StartGameButtonPostion_Y = DOWNY + DOWNY + DOWNY + DOWNY + DOWNY;
    int WIDTH = 490; //Az egész menu szélessége
    int HEIGHT = 500; //Az egész menu magassása


    public MainMenu() {

        //Menu Variables
        Menu.setResizable(false);
        Menu.setSize(WIDTH, HEIGHT);
        Menu.setLayout(null);
        Menu.setLocationRelativeTo(null);
        Menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //JLabel

        Menu.setVisible(true);

        //PLayer name inputbox

        Menu.setVisible(true);

        ///Start Game Button
        Start.setSize(menuWidth + 50, menuHeight);
        Start.setLocation(StartGameButtonPostion_X, StartGameButtonPostion_Y);
        Menu.add(Start);
        Start.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                Menu.setVisible(false);
                ThemeParkGUI gui = new ThemeParkGUI("x");
            }

        });

        //Scoreboard

        ScoreBoard.setSize(menuWidth, menuHeight);
        ScoreBoard.setLocation(10, 10);
        Menu.add(ScoreBoard);

    }


}