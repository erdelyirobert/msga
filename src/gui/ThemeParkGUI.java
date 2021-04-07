package gui;

import ThemePark.EGeneralEquipment;
import board.TPBoard;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class ThemeParkGUI extends JFrame{

    public TPBoard board;
    //JFrame frame = new JFrame("Theme park");
    Color clr1 = new Color(0, 153, 0);
    public static EGeneralEquipment selected_ge = EGeneralEquipment.NOTHING;
    public Container c = getContentPane();
    public int HEIGHT = 800;
    public int WIDTH = 600;
    JLabel moneyLabel = new JLabel();

    /**
     * Instantiates a new Theme park gui.
     *
     * @param title the title
     * @throws IOException the io exception
     */
    public ThemeParkGUI(String title) throws IOException {
        super(title);



        /**
         * Menu bar
         */


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

        ImageIcon coinIcon = new ImageIcon(new ImageIcon("data\\images\\coin.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
        JMenu buildGame = new JMenu("Game");
        JMenuItem wheelMenuItem = new JMenuItem("10 | Wheel",coinIcon);
        JMenuItem trainMenuItem = new JMenuItem("20 | Train",coinIcon);
        JMenuItem rollerCoasterMenuItem = new JMenuItem("30 | Roller Coaster",coinIcon);
        JMenuItem waterParkMenuItem = new JMenuItem("40 | Water Park",coinIcon);
        JMenuItem slideMenuItem = new JMenuItem("50 | Slide",coinIcon);


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
        JMenuItem restaurantMenuItem = new JMenuItem("100 | Restaurant", coinIcon);
        JMenuItem treeMenuItem = new JMenuItem("10 | Tree", coinIcon);
        JMenuItem bushMenuItem = new JMenuItem("10 | Bush", coinIcon);
        JMenuItem roadMenuItem = new JMenuItem("10 | Road", coinIcon);
        JMenuItem trashBinMenuItem = new JMenuItem("10 | Bin",coinIcon);


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
        setJMenuBar(menub);

        board = new TPBoard();

        /*
        * This label display the money of the player
        TODO  moneyLabel.setText("Money: " + player.getMoney());
         */



        moneyLabel.setIcon(coinIcon);

        moneyLabel.setFont(new Font("Serif", Font.BOLD, 16));
        moneyLabel.setText(String.valueOf(board.getBudget()));
        moneyLabel.setHorizontalTextPosition(JLabel.RIGHT);
        moneyLabel.setVerticalTextPosition(JLabel.CENTER);

        menub.add(moneyLabel);
        c.add(board);
        c.revalidate();
        c.repaint();
        board.requestFocusInWindow();

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
                //frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                System.exit(0);
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
                selected_ge = EGeneralEquipment.WHEEL;

            }
        });
        trainMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                stopBuild.setEnabled(true);
                selected_ge = EGeneralEquipment.TRAIN;
                updateMoneyLabel();
            }
        });
        rollerCoasterMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                stopBuild.setEnabled(true);
                selected_ge = EGeneralEquipment.ROLLERCOASTER;
                updateMoneyLabel();
            }
        });
        waterParkMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                stopBuild.setEnabled(true);
                selected_ge = EGeneralEquipment.WATERPARK;
                updateMoneyLabel();
            }
        });
        slideMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                stopBuild.setEnabled(true);
                selected_ge = EGeneralEquipment.SLIDE;
                updateMoneyLabel();
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
                selected_ge = EGeneralEquipment.RESTAURANT;
                updateMoneyLabel();
            }
        });
        bushMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                stopBuild.setEnabled(true);
                selected_ge = EGeneralEquipment.BUSH;
                updateMoneyLabel();
            }
        });
        treeMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                stopBuild.setEnabled(true);
                selected_ge = EGeneralEquipment.TREE;
                updateMoneyLabel();
            }
        });

        roadMenuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                stopBuild.setEnabled(true);
                selected_ge = EGeneralEquipment.ROAD;
                updateMoneyLabel();
            }
        });

        trashBinMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                stopBuild.setEnabled(true);
                selected_ge = EGeneralEquipment.BIN;
                updateMoneyLabel();
            }
        });

        stopBuild.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopBuild.setEnabled(false);
                System.out.println("kikapcsolva");
                selected_ge = EGeneralEquipment.NOTHING;
                selected_ge = EGeneralEquipment.NOTHING;
                updateMoneyLabel();
            }
        });


    }

    public void updateMoneyLabel() {
        moneyLabel.setText(String.valueOf(board.getBudget()));
    }


}