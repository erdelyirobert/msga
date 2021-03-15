package gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ThemeParkGUI{
    private JFrame frame = new JFrame("Theme park");

    public ThemeParkGUI(String title){
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.revalidate();
        frame.repaint();

        /*
         * Menu bar
         */

        JMenuBar menuBar = new JMenuBar(); // creation of menubar
        frame.setJMenuBar(menuBar); //adding the menubar to the frame

        JMenu gameMenu = new JMenu("Game"); //creation of the "Game" dropdown on the menubar
        menuBar.add(gameMenu); //adding it to the menubar

        JMenu saveMenuItem = new JMenu("Save"); //save option in the dropdown beneath the "Game"
        gameMenu.add(saveMenuItem); //adding it beneath the "Game" to the gamemenu

        JMenu exitMenuItem = new JMenu("Exit"); //exit option in the dropdown beneath the "Game"
        gameMenu.add(exitMenuItem); //adding it beneath the "Game" to the gamemenu

        /**
         * ActionListener
         * Upon clicking on the "Exit" option the game will stop running and will close
         */
        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                System.exit(0);
            }
        });

        /**
         * Sets the preferred size of the window so the contents are in the
         * window
         */
        frame.setVisible(true);

        /**
         * Components are visible on the screen
         */
        frame.pack();
    }
}