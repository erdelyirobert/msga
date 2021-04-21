package gui;

import org.junit.Assert;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MainMenuTest {
    @Test
    public void TestImageRead() throws IOException {
        BufferedImage img = ImageIO.read(new File("data\\images\\theme_park.png"));
        Assert.assertNotNull(img);
    }
}