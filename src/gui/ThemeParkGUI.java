package gui;

import board.TPBoard;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class ThemeParkGUI {
    private TPBoard board;
    JFrame frame = new JFrame("Theme park");
    private final int WIDTH = 1000;
    private final int HEIGHT = 800;
    Color clr1 = new Color(0,153,0);
    public static String selected_game = "";
    /**
     * Instantiates a new Theme park gui.
     *
     * @param title the title
     */
    public ThemeParkGUI(String title) throws IOException {
        frame.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.setLocationRelativeTo(null);
        frame.revalidate();
        frame.repaint();
        frame.getContentPane().setBackground(clr1);
        board = new TPBoard(20, 20);
        frame.getContentPane().add(board.getBoardPanel(), BorderLayout.CENTER);


        /**
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
        //////////////////////////////////////////////////////////////
        /*
         * Menu bar
         * (part of Game)
         */


        JMenu buildGame = new JMenu("Game");
        JMenuItem wheelMenuItem = new JMenuItem("Wheel");
        JMenuItem trainMenuItem = new JMenuItem("Train");
        JMenuItem rollerRoasterMenuItem = new JMenuItem("Roller Coaster");
        JMenuItem waterParkMenuItem = new JMenuItem("Water Park");
        JMenuItem slideMenuItem = new JMenuItem("Slide");



        menub.add(buildGame);
        menub.add(wheelMenuItem);
        menub.add(trainMenuItem);
        menub.add(rollerRoasterMenuItem);
        menub.add(waterParkMenuItem);
        menub.add(slideMenuItem);
        buildGame.add(wheelMenuItem);
        buildGame.add(trainMenuItem);
        buildGame.add(rollerRoasterMenuItem);
        buildGame.add(waterParkMenuItem);
        buildGame.add(slideMenuItem);
        /////////////////////////////////////////////////////////////////////////////////////////
        /*
         * Menu bar
         * (part of General Equipment)
         */



        JMenu buildGeneralEquipment = new JMenu("General Equipment");
        JMenuItem restaurantMenuItem = new JMenuItem("Restaurant");
        JMenuItem treeMenuItem = new JMenuItem("Tree");
        JMenuItem bushRoasterMenuItem = new JMenuItem("Bush");
        JMenuItem roadMenuItem = new JMenuItem("Road");
        JMenuItem trashBinMenuItem = new JMenuItem("Bin");




        menub.add(buildGeneralEquipment);
        menub.add(restaurantMenuItem);
        menub.add(treeMenuItem);
        menub.add(bushRoasterMenuItem);
        menub.add(roadMenuItem);
        buildGeneralEquipment.add(restaurantMenuItem);
        buildGeneralEquipment.add(treeMenuItem);
        buildGeneralEquipment.add(bushRoasterMenuItem);
        buildGeneralEquipment.add(roadMenuItem);
        buildGeneralEquipment.add(trashBinMenuItem);



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
         * ActionListeners
         *
         * Upon clicking on the game option the user can build 5 types of games
         */
        wheelMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                System.out.println("kattint");
                selected_game = "wheel";
            }
        });
        trainMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                System.out.println("kattint");
                selected_game = "train";
            }
        });
        rollerRoasterMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                System.out.println("kattint");
                selected_game = "rollercoaster";
            }
        });
        waterParkMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                System.out.println("kattint");
                selected_game = "waterpark";
            }
        });
        slideMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                System.out.println("kattint");
                selected_game = "slide";
            }
        });
        /**
         * ActionListeners
         *
         * Upon clicking on the General Equipment option the user can build 5 types of general equipments
         */
        restaurantMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                System.out.println("kattint");
                selected_game = "restaurant";
            }
        });
        bushRoasterMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                System.out.println("kattint");
                selected_game = "bush";
            }
        });
        treeMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                System.out.println("kattint");
                selected_game = "tree";
            }
        });
        roadMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                System.out.println("kattint");
                selected_game = "road";

            }
        });
        trashBinMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                System.out.println("kattint");
                selected_game = "bin";
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

    public String getSelected_game() {
        return selected_game;
    }
}