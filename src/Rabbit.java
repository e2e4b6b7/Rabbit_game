import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Rabbit extends Rect implements Drawable, Updateable {
    String name;
    double vx, vy;
    final int LEFT, RIGHT, UP;
    final double HORIZONTAL_SPEED = 1.5, JUMP_START_SPEED = -5;
    boolean isBlockedDown, isBlockedUp, isBlockedRight, isBlockedLeft;
    ScoreNamed score;

    Rabbit(String _name, String _path, int _left, int _up, int _right) {
        x = 10;
        y = 10;
        vx = 0;
        vx = 0;
        height = 20;
        width = 20;
        try {
            image = ImageIO.read(new File(_path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        name = _name;
        score = new ScoreNamed(name);
        LEFT = _left;
        RIGHT = _right;
        UP = _up;
    }

    void setPosition(double _x, double _y) {
        x = _x;
        y = _y;
    }

    void add() {
        Level.addDrawable(this);
        Level.addUpdateable(this);
        Level.addRect(this);
    }

    public void draw(Graphics2D a) {
        /*AffineTransform tx = AffineTransform.getScaleInstance(-1,1);
        a.setTransform(tx);*/
        a.drawImage(image, (int) x, (int) y, (int) height, (int) width, null);
        //a.setTransform(new AffineTransform());
        a.drawString(name, (int) x, (int) (y - 5));
    }

    public void update() {
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
                    if (r.getClass() == Rabbit.class) {
                        die((Rabbit) r);
                    }
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

    private void die(Rabbit r) {
        r.score.kill();
        for (int i = 0; i < 30; i++) {
            new DiePlace(x, y);
        }
        int position = (int) (Level.generationPositions.length * Math.random());
        x = Level.generationPositions[position][0];
        y = Level.generationPositions[position][1];
    }

    public void pressed(int keyCode) {
        if (keyCode == LEFT) {
            vx = -HORIZONTAL_SPEED;
        } else if (keyCode == RIGHT) {
            vx = HORIZONTAL_SPEED;
        } else if (keyCode == UP && isBlockedDown) {
            vy = JUMP_START_SPEED;
        }
    }

    public void released(int keyCode) {
        if (keyCode == LEFT) {
            vx = 0;
        } else if (keyCode == RIGHT) {
            vx = 0;
        }
    }
}