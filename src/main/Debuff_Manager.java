package main;

import detail.Block_Obstacle;
import detail.Block;
import java.util.ArrayList;
import java.util.Random;

public class Debuff_Manager {
    private PlayManager playManager;
    public ArrayList<Block_Obstacle> obstacleBlocks = new ArrayList<>();

    public Debuff_Manager(PlayManager playManager) {
        this.playManager = playManager;
        generateObstacles();
    }

    public void generateObstacles() {
        int numObstacles = new Random().nextInt(5) + 1; // Random number of obstacles between 1 and 3
        for (int i = 0; i < numObstacles; i++) {
            Block_Obstacle obstacle = new Block_Obstacle();
            int x, y;
            boolean validPosition;

            do {
                x = new Random().nextInt(playManager.width / Block.size) * Block.size + playManager.left_x;
                y = new Random().nextInt(playManager.height / Block.size) * Block.size + playManager.top_y;

                validPosition = isValidObstaclePosition(x, y);

            } while (!validPosition);

            obstacle.x = x;
            obstacle.y = y;
            obstacleBlocks.add(obstacle);
        }
    }

    private boolean isValidObstaclePosition(int x, int y) {
        // Ensure obstacle does not appear at the top or bottom of the playing screen
        if (y <= playManager.top_y + 2 * Block.size || y >= playManager.bottom_y - 2 * Block.size) {
            return false;
        }

        // Ensure obstacle is not next to or too close to the tetromino
        for (Block b : playManager.currentBrick.b) {
            if (Math.abs(b.x - x) <= 2 * Block.size && Math.abs(b.y - y) <= 2 * Block.size) {
                return false;
            }
        }

        return true;
    }

    public void handleObstacleCollision() {
        for (int i = 0; i < obstacleBlocks.size(); i++) {
            Block_Obstacle obstacle = obstacleBlocks.get(i);
            for (Block b : playManager.currentBrick.b) {
                if (b.x == obstacle.x && b.y == obstacle.y) {
                    applySpeedBuff();
                    obstacleBlocks.remove(i);
                    generateObstaclesIfNeeded();
                    return;
                }
            }
        }
    }

    private void applySpeedBuff() {
        final int MIN_DROP_INTERVAL = 10;

        if (playManager.dropInterval > MIN_DROP_INTERVAL) {
            if (playManager.level <= 2) {
                playManager.dropInterval -= 20; // Decrease dropInterval by 20
            } else if (playManager.level >= 3 && playManager.level <= 5) {
                playManager.dropInterval -= 10; // Decrease dropInterval by 7
            } else if (playManager.level >= 6 && playManager.level <= 7) {
                playManager.dropInterval -= 7; // Decrease dropInterval by 5
            } else {
                playManager.dropInterval -= 2; // Decrease dropInterval by 2
            }

            // Ensure dropInterval does not fall below the minimum threshold
            if (playManager.dropInterval < MIN_DROP_INTERVAL) {
                playManager.dropInterval = MIN_DROP_INTERVAL;
            }
        } else {
            playManager.dropInterval = MIN_DROP_INTERVAL;
        }
    }

    private void generateObstaclesIfNeeded() {
        if (obstacleBlocks.size() < 1) {
            generateObstacles();
        }
    }
}
