package gui;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class ThemeParkFrame {
    JFrame frame = new ThemeParkGUI("Theme Park");

    public ThemeParkFrame(String title) throws IOException {
        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.revalidate();
        frame.repaint();


    }
}
