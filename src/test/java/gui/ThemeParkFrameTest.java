package gui;

import org.junit.Assert;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ThemeParkFrameTest {

    @Test
    public void TestImg() throws IOException {
        BufferedImage img;
        img = ImageIO.read(new File("data\\images\\theme_park.png"));
        Assert.assertNotNull(img);
    }


}