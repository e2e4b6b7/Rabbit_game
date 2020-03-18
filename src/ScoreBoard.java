import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;

public class ScoreBoard implements Drawable {
    static ArrayList<ScoreNamed> scores;

    ScoreBoard() {
        scores = new ArrayList<>();
    }

    public void draw(Graphics2D g) {
        scores.sort(Comparator.comparingInt(o -> -o.score));
        int num = 0;
        for (ScoreNamed score : scores) {
            g.drawString(score.name + ": " + score.score, 10, 20 + 10 * (num++));
        }
    }
}

class ScoreNamed {
    String name;
    int score;

    ScoreNamed(String _name) {
        name = _name;
        score = 0;
        ScoreBoard.scores.add(this);
    }

    void kill() {
        score++;
    }
}