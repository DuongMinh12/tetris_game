package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.Random;

import detail.Block;
import detail.Block_Obstacle;
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
    int originalDropInterval = 60;
    boolean gameOver;

    boolean effectCounterOn;
    int effectCounter;
    ArrayList<Integer> effectY = new ArrayList<>();

    int level = 1;
    int score;
    int line;

    Debuff_Manager debuffManager;

    public PlayManager() {
        left_x = (GamePanel.width / 2) - (width / 2);
        right_x = left_x + width;
        top_y = 50;
        bottom_y = top_y + height;
        brick_start_x = left_x + (width / 2) - Block.size;
        brick_start_y = top_y + Block.size;

        nextBrick_x = right_x + 175;
        nextBrick_y = top_y + 500;
        nextBrick = pickBrick();
        nextBrick.setXY(nextBrick_x, nextBrick_y);

        currentBrick = pickBrick();
        currentBrick.setXY(brick_start_x, brick_start_y);

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
    }

    // public void draw(Graphics2D g2) {
    // g2.setColor(Color.white);
    // g2.setStroke(new BasicStroke(4f));
    // g2.drawRect(left_x - 4, top_y - 4, width + 8, height + 8);

    // int x = right_x + 100;
    // int y = bottom_y - 200;
    // g2.drawRect(x, y, 200, 200);
    // g2.setFont(new Font("Arial", Font.PLAIN, 30));
    // g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
    // RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    // g2.drawString("NEXT", x + 60, y + 35);

    // g2.drawRect(x, top_y, 250, 250);
    // x += 30;
    // y = top_y + 65;
    // g2.drawString("LEVEL: " + level, x, y);
    // g2.drawString("SCORE: " + score, x, y + 70);
    // g2.drawString("LINE: " + line, x, y + 140);

    // if (currentBrick != null) {
    // currentBrick.draw(g2);
    // }

    // nextBrick.draw(g2);
    // for (int i = 0; i < staticBlocks.size(); i++) {
    // staticBlocks.get(i).draw(g2);
    // }

    // if (effectCounterOn) {
    // effectCounter++;
    // g2.setColor(Color.red);
    // for (int i = 0; i < effectY.size(); i++) {
    // g2.fillRect(left_x, effectY.get(i), width, Block.size);
    // }
    // if (effectCounter == 10) {
    // effectCounterOn = false;
    // effectCounter = 0;
    // effectY.clear();
    // }
    // }
    // // Draw obstacle blocks
    // for (Block_Obstacle obstacle : debuffManager.obstacleBlocks) {
    // obstacle.draw(g2);
    // }

    // g2.setColor(Color.yellow);
    // g2.setFont(g2.getFont().deriveFont(50f));

    // if (gameOver) {
    // x = left_x + 25;
    // y = top_y + 320;
    // g2.drawString("GAME OVER", x, y);
    // } else if (KeyHandle.pausePressed) {
    // x = left_x + 70;
    // y = top_y + 320;
    // g2.drawString("PAUSED", x, y);
    // }

    // x = 35;
    // y = top_y + 320;
    // g2.setColor(Color.white);
    // g2.setFont(new Font("Time New Roman", Font.PLAIN, 60));
    // g2.drawString("Simple Tetris", x + 20, y);
    // }

    public void draw(Graphics2D g2) {
        // Set rendering hints for better text quality
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // Draw background
        g2.setColor(COLOR_WINDOW);
        g2.fillRect(0, 0, GamePanel.width, GamePanel.height);

        // Draw playfield border
        g2.setColor(COLOR_BORDER);
        g2.setStroke(new BasicStroke(4f));
        g2.drawRect(left_x - 4, top_y - 4, width + 8, height + 8);

        // Draw playfield background
        // g2.setColor(COLOR_BACKGROUND);
        g2.fillRect(left_x, top_y, width, height);

        // Draw next brick frame
        int x = right_x + 100;
        int y = bottom_y - 200;
        g2.setColor(COLOR_BORDER);
        g2.drawRect(x, y, 200, 200);
        // Draw title background
        g2.setColor(COLOR_BORDER);
        g2.fillRect(x, y, 200, 50);
        // Draw the divider
        g2.setColor(COLOR_BORDER);
        g2.drawLine(x, y + 50, x + 200, y + 50);
        // Draw the "NEXT" title
        g2.setFont(font2);
        g2.setColor(Color.WHITE);
        g2.drawString("NEXT", x + 65, y + 35);

        // Draw score frame
        g2.setColor(COLOR_BORDER);
        g2.drawRect(right_x + 100, top_y, 250, 250);
        x += 30;
        y = top_y + 65;
        // Draw title background
        g2.setColor(COLOR_BORDER);
        g2.fillRect(right_x + 100, top_y, 250, 50);
        // Draw the divider
        g2.setColor(COLOR_BORDER);
        g2.drawLine(right_x + 100, top_y + 50, right_x + 350, top_y + 50);
        // Draw the "SCORE" title
        g2.setFont(font2);
        g2.setColor(Color.WHITE);
        g2.drawString("SCORE", right_x + 180, y - 30);
        // Draw the content
        g2.setColor(COLOR_DARK);
        g2.drawString("LEVEL: " + level, x, y + 50);
        g2.drawString("SCORE: " + score, x, y + 100);
        g2.drawString("LINE: " + line, x, y + 150);

        // Draw obstacle blocks
        for (Block_Obstacle obstacle : debuffManager.obstacleBlocks) {
            obstacle.draw(g2);
        }

        // Draw current brick
        if (currentBrick != null) {
            currentBrick.draw(g2);
        }

        // Draw next brick
        nextBrick.draw(g2);

        // Draw static blocks
        for (int i = 0; i < staticBlocks.size(); i++) {
            staticBlocks.get(i).draw(g2);
        }

        // Draw effects
        if (effectCounterOn) {
            effectCounter++;
            g2.setColor(Color.red);
            for (int i = 0; i < effectY.size(); i++) {
                g2.fillRect(left_x, effectY.get(i), width, Block.size);
            }
            if (effectCounter == 10) {
                effectCounterOn = false;
                effectCounter = 0;
                effectY.clear();
            }
        }

        // Draw game over or paused message
        g2.setFont(font3);
        g2.setColor(Color.yellow);

        if (gameOver) {
            // Draw game over
            g2.setFont(fontPause); // Use larger font
            String gameOverText = "GAME OVER";
            int gameOverTextWidth = g2.getFontMetrics().stringWidth(gameOverText);
            int gameOverTextX = left_x + (width - gameOverTextWidth) / 2;
            int gameOverTextY = top_y + height / 2;
            g2.setColor(Color.BLACK);
            g2.drawString(gameOverText, gameOverTextX, gameOverTextY);
        } else if (KeyHandle.pausePressed) {
            // Draw paused
            g2.setFont(fontPause); // Use larger font
            String pausedText = "PAUSED";
            int pausedTextWidth = g2.getFontMetrics().stringWidth(pausedText);
            int pausedTextX = left_x + (width - pausedTextWidth) / 2;
            int pausedTextY = top_y + height / 2;
            g2.drawString(pausedText, pausedTextX, pausedTextY);
        }

        // Draw instructions
        x = -5;
        y = top_y + 320;
        g2.setColor(COLOR_DARK);
        g2.setFont(new Font("Times New Roman", Font.PLAIN, 60));
        g2.drawString("Instructions", x + 20, y);

        g2.setFont(fontInstructions);
        y += 50;
        g2.drawString("- Press A / D, Left / Right arrow: Move left / right.", x + 20, y);
        y += 30;
        g2.drawString("- Press W / Up arrow: Rotate the tetromino.", x + 20, y);
        y += 30;
        g2.drawString("- Spacebar: Pause the game.", x + 20, y);
        y += 30;
        g2.drawString("- Down arrow: Increase fall speed.", x + 20, y);
    }

}