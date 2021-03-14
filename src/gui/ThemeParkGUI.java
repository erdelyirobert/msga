package gui;

import javax.swing.*;

public class ThemeParkGUI extends JFrame{
    JFrame frame = new ThemeParkGUI("Theme park");

    public ThemeParkGUI(String title){
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.revalidate();
        frame.repaint();
    }
}