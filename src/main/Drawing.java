package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import detail.Block;
import detail.Block_Obstacle;

public class Drawing {
    private PlayManager pm;
    private Style style = new Style();

    public Drawing(PlayManager pm) {
        this.pm = pm;
    }

    public void draw(Graphics2D g2) {
        // Set rendering hints for better text quality
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // Draw background
        g2.setColor(style.COLOR_WINDOW);
        g2.fillRect(0, 0, GamePanel.width, GamePanel.height);

        // Draw playfield border
        g2.setColor(style.COLOR_BORDER);
        g2.setStroke(new BasicStroke(4f));
        g2.drawRect(pm.left_x - 4, pm.top_y - 4, pm.width + 8, pm.height + 8);

        // Draw playfield background
        // g2.setColor(COLOR_BACKGROUND);
        g2.fillRect(pm.left_x, pm.top_y, pm.width, pm.height);

        // Draw next brick frame
        int x = pm.right_x + 100;
        int y = pm.bottom_y - 200;
        g2.setColor(style.COLOR_BORDER);
        g2.drawRect(x, y, 200, 200);
        // Draw title background
        g2.setColor(style.COLOR_BORDER);
        g2.fillRect(x, y, 200, 50);
        // Draw the divider
        g2.setColor(style.COLOR_BORDER);
        g2.drawLine(x, y + 50, x + 200, y + 50);
        // Draw the "NEXT" title
        g2.setFont(style.font2);
        g2.setColor(Color.WHITE);
        g2.drawString("NEXT", x + 65, y + 35);

        // Draw score frame
        g2.setColor(style.COLOR_BORDER);
        g2.drawRect(pm.right_x + 100, pm.top_y, 250, 250);
        x += 30;
        y = pm.top_y + 65;
        // Draw title background
        g2.setColor(style.COLOR_BORDER);
        g2.fillRect(pm.right_x + 100, pm.top_y, 250, 50);
        // Draw the divider
        g2.setColor(style.COLOR_BORDER);
        g2.drawLine(pm.right_x + 100, pm.top_y + 50, pm.right_x + 350, pm.top_y + 50);
        // Draw the "SCORE" title
        g2.setFont(style.font2);
        g2.setColor(Color.WHITE);
        g2.drawString("SCORE", pm.right_x + 180, y - 30);
        // Draw the content
        g2.setColor(style.COLOR_DARK);
        g2.drawString("LEVEL: " + pm.level, x, y + 50);
        g2.drawString("SCORE: " + pm.score, x, y + 100);
        g2.drawString("LINE: " + pm.line, x, y + 150);

        // Draw obstacle blocks
        for (Block_Obstacle obstacle : pm.debuffManager.obstacleBlocks) {
            obstacle.draw(g2);
        }

        // Draw current brick
        if (pm.currentBrick != null) {
            pm.currentBrick.draw(g2);
        }

        // Draw next brick
        pm.nextBrick.draw(g2);

        // Draw static blocks
        for (int i = 0; i < pm.staticBlocks.size(); i++) {
            pm.staticBlocks.get(i).draw(g2);
        }

        // Draw effects
        if (pm.effectCounterOn) {
            pm.effectCounter++;
            g2.setColor(Color.red);
            for (int i = 0; i < pm.effectY.size(); i++) {
                g2.fillRect(pm.left_x, pm.effectY.get(i), pm.width, Block.size);
            }
            if (pm.effectCounter == 10) {
                pm.effectCounterOn = false;
                pm.effectCounter = 0;
                pm.effectY.clear();
            }
        }

        // Draw game over or paused message
        g2.setFont(style.font3);
        g2.setColor(Color.yellow);

        if (pm.gameOver) {
            // Draw game over
            g2.setFont(style.fontPause); // Use larger font
            String gameOverText = "GAME OVER";
            int gameOverTextWidth = g2.getFontMetrics().stringWidth(gameOverText);
            int gameOverTextX = pm.left_x + (pm.width - gameOverTextWidth) / 2;
            int gameOverTextY = pm.top_y + pm.height / 2;
            g2.setColor(Color.BLACK);
            g2.drawString(gameOverText, gameOverTextX, gameOverTextY);
        } else if (KeyHandle.pausePressed) {
            // Draw paused
            g2.setFont(style.fontPause); // Use larger font
            String pausedText = "PAUSED";
            int pausedTextWidth = g2.getFontMetrics().stringWidth(pausedText);
            int pausedTextX = pm.left_x + (pm.width - pausedTextWidth) / 2;
            int pausedTextY = pm.top_y + pm.height / 2;
            g2.drawString(pausedText, pausedTextX, pausedTextY);
        }

        // Draw instructions
        x = -5;
        y = pm.top_y + 320;
        g2.setColor(style.COLOR_DARK);
        g2.setFont(new Font("Times New Roman", Font.PLAIN, 60));
        g2.drawString("Instructions", x + 20, y);

        g2.setFont(style.fontInstructions);
        y += 50;
        g2.drawString("- Press A / D, Left / Right arrow: Move left / right.", x + 20, y);
        y += 30;
        g2.drawString("- Press W / Up arrow: Rotate the tetromino.", x + 20, y);
        y += 30;
        g2.drawString("- Press S / Down arrow: Increase fall speed.", x + 20, y);
        y += 30;
        g2.drawString("- Spacebar: Pause the game.", x + 20, y);
        y += 30;
        g2.drawString("- Press R: ReStart game.", x + 20, y);
    }
}
