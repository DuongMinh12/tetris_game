package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.Random;

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

    // brick
    Brick currentBrick;
    final int brick_start_x;
    final int brick_start_y;
    Brick nextBrick;
    final int nextBrick_x;
    final int nextBrick_y;
    public static ArrayList<Block> staticBlocks = new ArrayList<>();

    public static int dropInterval = 60;

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

        // set the starting brick
        currentBrick = pickBrick();
        currentBrick.setXY(brick_start_x, brick_start_y);
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
        while (x < right_x && y < bottom_y) {
            for (int i = 0; i < staticBlocks.size(); i++) {
                if (staticBlocks.get(i).x == x && staticBlocks.get(i).y == y) {
                    blockCount++;
                }
            }

            x += Block.size;
            if (x == right_x) {
                if (blockCount == 12) {
                    for (int i = staticBlocks.size() - 1; i > -1; i--) {
                        if (staticBlocks.get(i).y == y) {
                            staticBlocks.remove(i);
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
    }

    public void update() {
        if (currentBrick.active == false) {
            staticBlocks.add(currentBrick.b[0]);
            staticBlocks.add(currentBrick.b[1]);
            staticBlocks.add(currentBrick.b[2]);
            staticBlocks.add(currentBrick.b[3]);

            currentBrick.deactivating = false;

            currentBrick = nextBrick;
            currentBrick.setXY(brick_start_x, brick_start_y);
            nextBrick = pickBrick();
            nextBrick.setXY(nextBrick_x, nextBrick_y);

            checkDelete();
        } else {
            currentBrick.update();
        }

    }

    public void draw(Graphics2D g2) {
        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(4f));
        g2.drawRect(left_x - 4, top_y - 4, width + 8, height + 8);

        // mini frame
        int x = right_x + 100;
        int y = bottom_y - 200;
        g2.drawRect(x, y, 200, 200);
        g2.setFont(new Font("Arial", Font.PLAIN, 30));
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.drawString("NEXT", x + 60, y + 35);

        if (currentBrick != null) {
            currentBrick.draw(g2);
        }

        nextBrick.draw(g2);
        for (int i = 0; i < staticBlocks.size(); i++) {
            staticBlocks.get(i).draw(g2);
        }

        g2.setColor(Color.yellow);
        g2.setFont(g2.getFont().deriveFont(50f));
        if (KeyHandle.pausePressed) {
            x = left_x + 70;
            y = top_y + 320;
            g2.drawString("PAUSED", x, y);
        }
    }
}
