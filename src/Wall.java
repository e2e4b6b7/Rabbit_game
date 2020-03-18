import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Wall extends Rect implements Drawable {

    Wall(int _x, int _y, int _width, int _height, String path) {
        x = _x;
        y = _y;
        height = _height;
        width = _width;
        try {
            image = ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Level.addDrawable(this);
        Level.addRect(this);
    }

    public void draw(Graphics2D g) {
        g.drawImage(image, (int) x, (int) y, (int) width, (int) height, null);
    }
}