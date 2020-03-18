import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Background implements Drawable {
    BufferedImage img;

    Background(String path) {
        try {
            img = ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Level.addDrawable(this);
    }

    @Override
    public void draw(Graphics2D g) {
        g.drawImage(img, 0, 0, 1000, 600, null);
    }
}
