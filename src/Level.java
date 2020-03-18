import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Level {
    static ArrayList<Rect> rects, candidatesRects, deleteRects;
    static ArrayList<Updateable> updateable, candidatesUpdateable, deleteUpdateable;
    static MyPanelGa panel;
    static ArrayList<Drawable> drawables, candidatesDrawables, deleteDrawables;
    static int[][] generationPositions;
    static BufferedImage diePict;
    static long timeStart;
    static Timer repaint;
    static Timer timer;

    Level(String path) {
        initialization();
        try {
            readConfig(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        timeStart = System.currentTimeMillis();
        run();
    }

    static void run() {
        JFrame frame = new JFrame();
        frame.setSize(1000, 630);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        panel = new MyPanelGa();
        timer = new Timer(10, e -> {
            for (Updateable u : updateable) {
                u.update();
            }
            updateLists();
            if (System.currentTimeMillis() - timeStart > 15000) {
                nextLev();
            }
        });
        repaint = new Timer(25, e -> panel.repaint());
        frame.add(panel, BorderLayout.CENTER);
        panel.setFocusable(true);
        panel.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                for (Rabbit r : Game.rabbits) {
                    r.pressed(e.getKeyCode());
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                for (Rabbit r : Game.rabbits) {
                    r.released(e.getKeyCode());
                }
            }
        });
        timer.start();
        repaint.start();
        frame.setVisible(true);
    }

    private static void nextLev() {
        timer.stop();
        repaint.stop();
        Game.nextLevel();
    }

    private static void updateLists() {
        updateable.removeAll(deleteUpdateable);
        deleteUpdateable.clear();
        updateable.addAll(candidatesUpdateable);
        candidatesUpdateable.clear();
        rects.removeAll(deleteRects);
        deleteRects.clear();
        rects.addAll(candidatesRects);
        candidatesRects.clear();
        drawables.removeAll(deleteDrawables);
        deleteDrawables.clear();
        drawables.addAll(candidatesDrawables);
        candidatesDrawables.clear();
    }

    private static void initialization() {
        drawables = new ArrayList<>();
        deleteDrawables = new ArrayList<>();
        deleteUpdateable = new ArrayList<>();
        deleteRects = new ArrayList<>();
        candidatesDrawables = new ArrayList<>();
        candidatesUpdateable = new ArrayList<>();
        candidatesRects = new ArrayList<>();
        rects = new ArrayList<>();
        updateable = new ArrayList<>();
    }

    private static void readConfig(String path) throws FileNotFoundException {
        Scanner sc = new Scanner(new File(path));
        new Background(sc.nextLine());
        int numGenerationPositions = sc.nextInt();
        generationPositions = new int[numGenerationPositions][2];
        for (int i = 0; i < numGenerationPositions; i++) {
            generationPositions[i][0] = sc.nextInt();
            generationPositions[i][1] = sc.nextInt();
        }
        for (int i = 0; i < Game.rabbits.size(); i++) {
            Game.rabbits.get(i).setPosition(generationPositions[i][0], generationPositions[i][1]);
        }
        int numWalls = sc.nextInt();
        String pathWall = sc.next();
        for (int i = 0; i < numWalls; i++) {
            int x = sc.nextInt();
            int y = sc.nextInt();
            int width = sc.nextInt();
            int height = sc.nextInt();
            new Wall(x, y, width, height, pathWall);
        }
        for (Rabbit r : Game.rabbits) {
            r.add();
        }
        addDrawable(Game.scoreBoard);
        try {
            diePict = ImageIO.read(new File(sc.next()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void addUpdateable(Updateable a) {
        candidatesUpdateable.add(a);
    }

    static void addDrawable(Drawable a) {
        candidatesDrawables.add(a);
    }

    static void addRect(Rect a) {
        candidatesRects.add(a);
    }

    static void deleteRect(Rect a) {
        deleteRects.add(a);
    }

    static void deleteUpdateable(Updateable a) {
        deleteUpdateable.add(a);
    }

    static void deleteDrawable(Drawable a) {
        deleteDrawables.add(a);
    }


    static class MyPanelGa extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D a = (Graphics2D) g;
            for (Drawable d : drawables) {
                d.draw(a);
            }
        }
    }
}