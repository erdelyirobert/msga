package gui;

import ThemePark.EGeneralEquipment;
import board.TPBoard;
import org.junit.Assert;
import org.junit.Test;

import javax.swing.*;
import java.awt.*;

public class ThemeParkGUITest  {
    @Test
    public void TestImage() {

        ImageIcon coinIcon = new ImageIcon(new ImageIcon("data\\images\\coin.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
        Assert.assertNotNull(coinIcon);

    }
}