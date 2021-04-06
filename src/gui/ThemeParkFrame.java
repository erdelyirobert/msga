package gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
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

        BufferedImage img = null;

        try {
            img = ImageIO.read(new File("data\\images\\theme_park.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ImageIcon icon = new ImageIcon(img);
        frame.setIconImage(icon.getImage());
    }
}
