package gui;

import ThemePark.EGames;
import ThemePark.EGeneralEquipment;
import board.TPBoard;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.awt.Graphics;

public class ThemeParkGUI {
    private TPBoard board;
    JFrame frame = new JFrame("Theme park");
    private final int WIDTH = 1000;
    private final int HEIGHT = 800;
    Color clr1 = new Color(0, 153, 0);
    public static EGames selected_game = EGames.NOTHING;
    public static EGeneralEquipment selected_ge = EGeneralEquipment.NOTHING;

    /**
     * Instantiates a new Theme park gui.
     *
     * @param title the title
     * @throws IOException the io exception
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
        frame.setResizable(false);


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
        JMenuItem rollerCoasterMenuItem = new JMenuItem("Roller Coaster");
        JMenuItem waterParkMenuItem = new JMenuItem("Water Park");
        JMenuItem slideMenuItem = new JMenuItem("Slide");



        menub.add(buildGame);
        menub.add(wheelMenuItem);
        menub.add(trainMenuItem);
        menub.add(rollerCoasterMenuItem);
        menub.add(waterParkMenuItem);
        menub.add(slideMenuItem);
        buildGame.add(wheelMenuItem);
        buildGame.add(trainMenuItem);
        buildGame.add(rollerCoasterMenuItem);
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
        JMenuItem bushMenuItem = new JMenuItem("Bush");
        JMenuItem roadMenuItem = new JMenuItem("Road");
        JMenuItem trashBinMenuItem = new JMenuItem("Bin");


        menub.add(buildGeneralEquipment);
        menub.add(restaurantMenuItem);
        menub.add(treeMenuItem);
        menub.add(bushMenuItem);
        menub.add(roadMenuItem);
        buildGeneralEquipment.add(restaurantMenuItem);
        buildGeneralEquipment.add(treeMenuItem);
        buildGeneralEquipment.add(bushMenuItem);
        buildGeneralEquipment.add(roadMenuItem);
        buildGeneralEquipment.add(trashBinMenuItem);

        JButton stopBuild = new JButton("STOP BUILD");
        menub.add(stopBuild);


        /*
        * This label display the money of the player
        TODO  moneyLabel.setText("Money: " + player.getMoney());
         */
        JLabel moneyLabel = new JLabel(" Money: ");
        menub.add(moneyLabel);

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
                stopBuild.setEnabled(true);
                selected_game = EGames.WHEEL;
            }
        });
        trainMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                stopBuild.setEnabled(true);
                selected_game = EGames.TRAIN;
            }
        });
        rollerCoasterMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                stopBuild.setEnabled(true);
                selected_game = EGames.ROLLERCOASTER;
            }
        });
        waterParkMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                stopBuild.setEnabled(true);
                selected_game = EGames.WATERPARK;
            }
        });
        slideMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                stopBuild.setEnabled(true);
                selected_game = EGames.SLIDE;
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
                stopBuild.setEnabled(true);
                selected_game = EGames.RESTAURANT;
            }
        });
        bushMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                stopBuild.setEnabled(true);
                selected_ge = EGeneralEquipment.BUSH;
            }
        });
        treeMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                stopBuild.setEnabled(true);
                selected_ge = EGeneralEquipment.TREE;
            }
        });

        roadMenuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                stopBuild.setEnabled(true);
                selected_ge = EGeneralEquipment.ROAD;

            }
        });

        trashBinMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                stopBuild.setEnabled(true);
                selected_ge = EGeneralEquipment.BIN;
            }
        });

        stopBuild.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopBuild.setEnabled(false);
                System.out.println("kikapcsolva");
                selected_ge = EGeneralEquipment.NOTHING;
                selected_game = EGames.NOTHING;
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