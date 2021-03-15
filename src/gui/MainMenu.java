package gui;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * The type Main menu.
 */
public class MainMenu {


    //Sizes
    private final int ButtonWidth = 250;
    private final int ButtonHeight = 50;
    private final int WIDTH = 490; //Az egész menu szélessége
    private final int HEIGHT = 500; //Az egész menu magassása

    //Positions
    private final int StartGameButtonPostion_Y = 10;
    private final int ScoreButtonPosition_Y = StartGameButtonPostion_Y + ButtonHeight + 10;
    private final int StartGameButtonPostion_X = (int) (WIDTH / 2.0) - (int) (ButtonWidth / 2.0);
    private final int ScoreButtonPosition_X = (int) (WIDTH / 2.0) - (int) (ButtonWidth / 2.0);

    /**
     * The Menu Frame
     */
    JFrame Menu = new JFrame("Main menu");
    JButton Start = new JButton("Start game");
    JButton ScoreBoard = new JButton("Scoreboard");
    ImageIcon picture = new ImageIcon("data\\images\\theme_park.png");
    JLabel imageLabel = new JLabel(picture);


    /**
     * Instantiates a new Main menu.
     */
    public MainMenu() {


        Menu.setResizable(true);
        Menu.setSize(WIDTH, HEIGHT);
        Menu.setLayout(null);
        Menu.setLocationRelativeTo(null);
        Menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Menu.setVisible(true);

        Component add = Menu.add(new JLabel(new ImageIcon("theme_entrance.jpg")));
        Menu.getContentPane().setBackground(Color.green);


        //Start button
        Start.setSize(ButtonWidth, ButtonHeight);
        Start.setLocation(StartGameButtonPostion_X, StartGameButtonPostion_Y);
        Start.setFont(new Font("Arial", Font.PLAIN, 25));
        Start.setBackground(Color.GREEN);
        Start.setForeground(Color.blue);
        Start.setPreferredSize(new Dimension(10, 10));
        Menu.add(Start);
        /*
         *Action listener for button
         *
         */
        Start.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                Menu.setVisible(false);
                ThemeParkGUI gui = new ThemeParkGUI("x");
            }

        });
        ScoreBoard.setSize(ButtonWidth, ButtonHeight);
        ScoreBoard.setLocation(ScoreButtonPosition_X, ScoreButtonPosition_Y);
        ScoreBoard.setFont(new Font("Arial", Font.PLAIN, 15));
        ScoreBoard.setBackground(Color.GREEN);
        ScoreBoard.setForeground(Color.blue);
        //ScoreBoard.setBorder(border);

        Menu.add(ScoreBoard);

        ScoreBoard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // todo: Scoreboard
                throw new UnsupportedOperationException("Not yet");
            }
        });

        imageLabel.setBounds((10) / 2, ScoreButtonPosition_Y+ButtonHeight+10, 1103, 828);
        imageLabel.setVisible(true);
        Menu.add(imageLabel);

    }
}
