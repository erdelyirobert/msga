package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class ThemeParkGUI {
    JFrame frame = new JFrame("Theme park");

    /**
     * Instantiates a new Theme park gui.
     *
     * @param title the title
     */
    public ThemeParkGUI(String title) {
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.setLocationRelativeTo(null);
        frame.revalidate();
        frame.repaint();
        frame.getContentPane().setBackground(Color.green);

        /*
         * Menu bar
         */
        JMenuBar menuBar = new JMenuBar(); // creation of menubar
        frame.setJMenuBar(menuBar); //adding the menubar to the frame

        JMenuBar menub = new JMenuBar();
        JMenu settingsMenu = new JMenu("Settings");
        JMenuItem saveMenuItem = new JMenuItem("Save");
        JMenuItem exitMenuItem = new JMenuItem("Exit");

        menub.add(settingsMenu);
        menub.add(exitMenuItem);
        settingsMenu.add(saveMenuItem);
        settingsMenu.add(exitMenuItem);

        this.frame.setJMenuBar(menub);

        /*
         * ActionListeners
         * Upon clicking on the "Save" option the game will save
         */
        saveMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                //todo
                throw new UnsupportedOperationException("Not defined");
            }
        });

        /**
         * ActionListeners
         *
         * Upon clicking on the "Exit" option the game will stop running and will close
         */
        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
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