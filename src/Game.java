import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    static String[] levels;
    static int nowLevel;
    static Level now;
    static ArrayList<Rabbit> rabbits;
    static ScoreBoard scoreBoard;

    public static void main(String[] args) {
        rabbits = new ArrayList<>();
        scoreBoard = new ScoreBoard();
        try {
            readConfig();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        nowLevel = -1;
        nextLevel();
    }

    static void nextLevel() {
        if (++nowLevel < levels.length) {
            now = new Level(levels[nowLevel]);
        } else {
            System.exit(0);
        }
    }

    private static void readConfig() throws FileNotFoundException {
        Scanner sc = new Scanner(new File("settings/config.txt"));
        levels = new String[sc.nextInt()];
        for (int i = 0; i < levels.length; i++) {
            levels[i] = sc.next();
        }
        int numRabbit = sc.nextInt();
        for (int i = 0; i < numRabbit; i++) {
            String pathRabbit = sc.next();
            String name = sc.next();
            int left = sc.nextInt();
            int up = sc.nextInt();
            int right = sc.nextInt();
            rabbits.add(new Rabbit(name, pathRabbit, left, up, right));
        }
    }
}