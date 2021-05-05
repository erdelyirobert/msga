package gui;

import ThemePark.EGeneralEquipment;
import board.TPBoard;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class ThemeParkGUI extends JFrame{
    public TPBoard board;
    public static EGeneralEquipment selected_ge = EGeneralEquipment.NOTHING;
    public Container c = getContentPane();
    JLabel moneyLabel = new JLabel();
    JLabel workerLabel = new JLabel();

    /**
     * Instantiates a new Theme park gui.
     *
     * @param title the title
     * @throws IOException the io exception
     */
    public ThemeParkGUI(String title) throws IOException {
        super(title);

        ImageIcon coinIcon = new ImageIcon(new ImageIcon("data\\images\\coin.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
        ImageIcon cleanerIcon = new ImageIcon(new ImageIcon("data\\images\\cleaner.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));

        /**
         * Menu bar creation
         */
        JMenuBar menub = new JMenuBar();

        /**
         * Dropdowns on menubar creation
         */
        JMenu settingsMenu = new JMenu("Settings");
        JMenu buildGame = new JMenu("Game");
        JMenu buildGeneralEquipment = new JMenu("General Equipment");
        JMenu callStaff = new JMenu("Call Staff");
        JMenu kickStaff = new JMenu("Kick Staff");

        /**
         * Adding the menus to the menubar
         */
        menub.add(settingsMenu);
        menub.add(buildGame);
        menub.add(buildGeneralEquipment);
        menub.add(callStaff);
        menub.add(kickStaff);

        /**
         * Settings menuitems creation
         * Adding Save and exit option to Settings dropdown
         */
        JMenuItem saveMenuItem = new JMenuItem("Save");
        JMenuItem exitMenuItem = new JMenuItem("Exit");

        settingsMenu.add(saveMenuItem);
        settingsMenu.add(exitMenuItem);

        /**
         * Games menuitems creation
         * Adding games to the Games dropdown
         */
        JMenuItem wheelMenuItem = new JMenuItem("1500 | Wheel",coinIcon);
        JMenuItem trainMenuItem = new JMenuItem("  800 | Train",coinIcon);
        JMenuItem rollerCoasterMenuItem = new JMenuItem("1000 | Roller Coaster",coinIcon);
        JMenuItem waterParkMenuItem = new JMenuItem("1000 | Water Park",coinIcon);
        JMenuItem slideMenuItem = new JMenuItem("  800 | Slide",coinIcon);

        buildGame.add(wheelMenuItem);
        buildGame.add(trainMenuItem);
        buildGame.add(rollerCoasterMenuItem);
        buildGame.add(waterParkMenuItem);
        buildGame.add(slideMenuItem);

        /**
         * General equipment menuitems creation
         * Adding general equipments to General equipment dropdown
         */
        JMenuItem restaurantMenuItem = new JMenuItem("600 | Restaurant", coinIcon);
        JMenuItem treeMenuItem = new JMenuItem("  10 | Tree", coinIcon);
        JMenuItem bushMenuItem = new JMenuItem("  10 | Bush", coinIcon);
        JMenuItem roadMenuItem = new JMenuItem("  10 | Road", coinIcon);
        JMenuItem trashBinMenuItem = new JMenuItem("  10 | Bin",coinIcon);

        buildGeneralEquipment.add(restaurantMenuItem);
        buildGeneralEquipment.add(treeMenuItem);
        buildGeneralEquipment.add(bushMenuItem);
        buildGeneralEquipment.add(roadMenuItem);
        buildGeneralEquipment.add(trashBinMenuItem);

        /**
         * Call Staff menuitems creation
         * Adding staffs to Call staff dropdown
         */

        JMenuItem callCleaner = new JMenuItem("  10 / 5sec | Cleaner", coinIcon);
        JMenuItem callMaintenance = new JMenuItem("  10 / 5sec | Maintenance", coinIcon);

        callStaff.add(callCleaner);
        callStaff.add(callMaintenance);

        /**
         * Kick Staff menuitems creation
         * Kick staffs to Kick Staff dropdown
         */

        JMenuItem kickCleaner = new JMenuItem("  0 | Fire Cleaner", coinIcon);

        kickStaff.add(kickCleaner);

        /**
         * Button to stop building
         * And adding it to the menubar
         */
        JButton stopBuild = new JButton("STOP BUILD");
        menub.add(stopBuild);

        /**
         * Setting the menubar
         */
        setJMenuBar(menub);

        board = new TPBoard();

        /**
         * Moneylabel to show the current budget
         * Workerlabel to show the current number of workers
         */
        moneyLabel.setIcon(coinIcon);
        moneyLabel.setFont(new Font("Serif", Font.BOLD, 12));
        moneyLabel.setHorizontalTextPosition(JLabel.RIGHT);
        moneyLabel.setVerticalTextPosition(JLabel.CENTER);

        workerLabel.setIcon(cleanerIcon);
        workerLabel.setFont(new Font("Serif", Font.BOLD, 12));
        workerLabel.setHorizontalTextPosition(JLabel.RIGHT);
        workerLabel.setVerticalTextPosition(JLabel.CENTER);

        c.add(board);
        c.revalidate();
        c.repaint();
        board.requestFocusInWindow();

        /**
         * Timer that refreshes the value of the actual budget
         */
        Timer timer;
        timer = new Timer(200, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                moneyLabel.setText(" Money: " + String.valueOf(board.getBudget() + " "));
                workerLabel.setText(" Workers: " + String.valueOf(board.workers.size()));
            }
        });
        timer.start();

        /**
         * Adding the moneylabel and workerLabel to the menubar
         */
        menub.add(moneyLabel);
        menub.add(workerLabel);

        /**
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
            }
        });

        rollerCoasterMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                stopBuild.setEnabled(true);
                selected_ge = EGeneralEquipment.ROLLERCOASTER;
            }
        });

        waterParkMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                stopBuild.setEnabled(true);
                selected_ge = EGeneralEquipment.WATERPARK;
            }
        });

        slideMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                stopBuild.setEnabled(true);
                selected_ge = EGeneralEquipment.SLIDE;
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

        /**
         * ActionListeners
         *
         * Upon clicking on the Call Staff option the user can call 2 types of staffs
         */

        callCleaner.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                selected_ge = EGeneralEquipment.CLEANER;
            }
        });

        callMaintenance.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                selected_ge = EGeneralEquipment.MAINTENANCE;
            }
        });

        stopBuild.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopBuild.setEnabled(false);
                System.out.println("kikapcsolva");
                selected_ge = EGeneralEquipment.NOTHING;
            }
        });

        kickCleaner.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selected_ge = EGeneralEquipment.KICKCLEANER;
            }
        });
    }
}