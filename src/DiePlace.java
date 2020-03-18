import java.awt.*;
import java.awt.image.BufferedImage;

public class DiePlace extends Rect implements Drawable, Updateable {
    BufferedImage image;
    long timeStart;
    double vx, vy;
    boolean isBlockedDown, isBlockedUp, isBlockedRight, isBlockedLeft;

    DiePlace(double _x, double _y) {
        timeStart = System.currentTimeMillis();
        image = Level.diePict;
        x = _x;
        y = _y;
        height = 10;
        width = 10;
        vx = (4.0 * Math.random()) - 2;
        vy = (12.0 * Math.random()) - 6;
        Level.addDrawable(this);
        Level.addUpdateable(this);
    }

    public void draw(Graphics2D g) {
        g.drawImage(image, (int) x, (int) y, 10, 10, null);
    }

    @Override
    public void update() {
        if (System.currentTimeMillis() - timeStart > 3000) {
            delete();
            return;
        }
        isBlockedRight = isBlockedDown = isBlockedLeft = isBlockedUp = false;
        for (Rect r : Level.rects) {
            if (r == this) {
                continue;
            }
            switch (this.hitTest(r)) {
                case BUMP_DOWN:
                    y = r.y - height + 0.1;
                    isBlockedDown = true;
                    break;
                case BUMP_LEFT:
                    x = r.x + r.width;
                    isBlockedLeft = true;
                    break;
                case BUMP_UP:
                    y = r.y + r.height;
                    isBlockedUp = true;
                    break;
                case BUMP_RIGHT:
                    x = r.x - width;
                    isBlockedRight = true;
                    break;
            }
        }
        if (isBlockedRight) {
            if (vx > 0) {
                vx = 0;
            }
        }
        if (isBlockedLeft) {
            if (vx < 0) {
                vx = 0;
            }
        }
        if (isBlockedUp) {
            if (vy < 0) {
                vy = 0;
            }
        }
        vy += GRAVITY_CONSTANT;
        if (isBlockedDown) {
            if (vy > 0) {
                vy = 0;
            }
        }
        x += vx;
        y += vy;
    }

    private void delete() {
        Level.deleteRect(this);
        Level.deleteDrawable(this);
        Level.deleteUpdateable(this);
    }
}