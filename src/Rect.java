import java.awt.image.BufferedImage;

public class Rect {
    double x, y;
    double height, width;
    final int BUMP_NONE = 0;
    final int BUMP_UP = 1;
    final int BUMP_DOWN = 2;
    final int BUMP_LEFT = 3;
    final int BUMP_RIGHT = 4;
    final double GRAVITY_CONSTANT = 0.098;
    BufferedImage image;

    public int hitTest(Rect o) {
        int verticalReturn = BUMP_NONE, horizontalReturn = BUMP_NONE;
        int verticalValue = 0, horizontalValue = 0;
        if (x + width > o.x && x < o.x + o.width) {
            if (y + height / 2 > o.y + o.height / 2) {
                if (y < o.y + o.height) {
                    verticalReturn = BUMP_UP;
                    verticalValue = (int) (o.y + o.height - y);
                }
            } else if (y + height > o.y) {
                verticalReturn = BUMP_DOWN;
                verticalValue = (int) (y + height - o.y);
            }
        }
        if (y + height > o.y && y < o.y + o.height) {
            if (x + width / 2 > o.x + o.width / 2) {
                if (x < o.x + o.width) {
                    horizontalReturn = BUMP_LEFT;
                    horizontalValue = (int) (o.x + o.width - x);
                }
            } else if (x + width > o.x) {
                horizontalReturn = BUMP_RIGHT;
                horizontalValue = (int) (x + width - o.x);
            }
        }
        if (horizontalValue < verticalValue) {
            return horizontalReturn;
        } else {
            return verticalReturn;
        }
    }
}