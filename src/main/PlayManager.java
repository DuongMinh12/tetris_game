package main;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;

// import javax.swing.JOptionPane;

import detail.Block;
import detail.Brick;
import detail.Brick_Bar;
import detail.Brick_L1;
import detail.Brick_L2;
import detail.Brick_Square;
import detail.Brick_T;
import detail.Brick_Z1;
import detail.Brick_Z2;

public class PlayManager {
    final int width = 360;
    final int height = 600;
    public static int left_x;
    public static int right_x;
    public static int top_y;
    public static int bottom_y;

    Brick currentBrick;
    final int brick_start_x;
    final int brick_start_y;
    Brick nextBrick;
    final int nextBrick_x;
    final int nextBrick_y;
    public static ArrayList<Block> staticBlocks = new ArrayList<>();

    public static int dropInterval = 60;
    static int gameDifficulty = 1;
    int originalDropInterval = 60;
    int easyDropInterval = 80;
    int hardDropInterval = 50;
    boolean gameOver;

    boolean effectCounterOn;
    int effectCounter;
    ArrayList<Integer> effectY = new ArrayList<>();

    int level = 1;
    int score;
    int line;

    Debuff_Manager debuffManager;
    Drawing drawing;

    public PlayManager() {
        left_x = (GamePanel.width / 2) - (width / 2);
        right_x = left_x + width;
        top_y = 50;
        bottom_y = top_y + height;
        brick_start_x = left_x + (width / 2) - Block.size;
        brick_start_y = top_y + Block.size;

        nextBrick_x = right_x + 175;
        nextBrick_y = top_y + 500;
        initializeGame();
        drawing = new Drawing(this);
    }

    public void initializeGame() {
        nextBrick = pickBrick();
        nextBrick.setXY(nextBrick_x, nextBrick_y);

        currentBrick = pickBrick();
        currentBrick.setXY(brick_start_x, brick_start_y);

        staticBlocks.clear();
        // dropInterval = originalDropInterval;

        if (gameDifficulty == 1)
            dropInterval = originalDropInterval;
        else if (gameDifficulty == 2)
            dropInterval = easyDropInterval;
        else if (gameDifficulty == 3)
            dropInterval = hardDropInterval;

        gameOver = false;
        effectCounterOn = false;
        effectCounter = 0;
        effectY.clear();
        level = 1;
        score = 0;
        line = 0;
        debuffManager = new Debuff_Manager(this);
    }

    private Brick pickBrick() {
        Brick brick = null;
        int i = new Random().nextInt(7);
        switch (i) {
            case 0:
                brick = new Brick_L1();
                break;
            case 1:
                brick = new Brick_L2();
                break;
            case 2:
                brick = new Brick_Square();
                break;
            case 3:
                brick = new Brick_Bar();
                break;
            case 4:
                brick = new Brick_T();
                break;
            case 5:
                brick = new Brick_Z1();
                break;
            case 6:
                brick = new Brick_Z2();
                break;
        }
        return brick;
    }

    private void checkDelete() {
        int x = left_x;
        int y = top_y;
        int blockCount = 0;
        int lineCount = 0;

        while (x < right_x && y < bottom_y) {
            for (int i = 0; i < staticBlocks.size(); i++) {
                if (staticBlocks.get(i).x == x && staticBlocks.get(i).y == y) {
                    blockCount++;
                }
            }

            x += Block.size;
            if (x == right_x) {
                if (blockCount == 12) {
                    effectCounterOn = true;
                    effectY.add(y);
                    for (int i = staticBlocks.size() - 1; i > -1; i--) {
                        if (staticBlocks.get(i).y == y) {
                            staticBlocks.remove(i);
                        }
                    }
                    lineCount++;
                    line++;
                    if (line % 10 == 0 && dropInterval > 1) {
                        level++;
                        if (dropInterval > 10) {
                            dropInterval -= 10;
                        } else {
                            dropInterval -= 1;
                        }
                    }
                    for (int i = 0; i < staticBlocks.size(); i++) {
                        if (staticBlocks.get(i).y < y) {
                            staticBlocks.get(i).y += Block.size;
                        }
                    }
                }
                blockCount = 0;
                x = left_x;
                y += Block.size;
            }
        }

        if (lineCount > 0) {
            GamePanel.se.playMusic(1, false);
            int singleLineScore = 10 * level;
            score += singleLineScore * lineCount;
        }
    }

    public void update() {
        if (!currentBrick.active) {
            staticBlocks.add(currentBrick.b[0]);
            staticBlocks.add(currentBrick.b[1]);
            staticBlocks.add(currentBrick.b[2]);
            staticBlocks.add(currentBrick.b[3]);

            if (currentBrick.b[0].x == brick_start_x && currentBrick.b[0].y == brick_start_y) {
                gameOver = true;
                GamePanel.music.stop();
                GamePanel.se.playMusic(2, false);
            }

            currentBrick.deactivating = false;

            currentBrick = nextBrick;
            currentBrick.setXY(brick_start_x, brick_start_y);
            nextBrick = pickBrick();
            nextBrick.setXY(nextBrick_x, nextBrick_y);

            // Reset dropInterval to original value when a new brick spawns
            dropInterval = originalDropInterval;

            checkDelete();
            debuffManager.handleObstacleCollision();
        } else {
            currentBrick.update();
            debuffManager.handleObstacleCollision();
        }
        if (KeyHandle.resetPressed) {
            KeyHandle.resetPressed = false; // Reset key press status
            // int response = JOptionPane.showConfirmDialog(null,
            // "If you reset, all information including your progress and all your scores
            // will be reset, do you agree to continue?",
            // "Are you sure reset?", JOptionPane.YES_NO_OPTION);

            // if (response == JOptionPane.YES_OPTION) {
            initializeGame();
            // }
        }
    }

    public void setGameDifficulty(int difficulty) {
        gameDifficulty = difficulty;
    }

    public void draw(Graphics2D g2) {
        drawing.draw(g2);
    }
}
