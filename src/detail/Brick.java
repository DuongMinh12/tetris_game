package detail;

import java.awt.Color;
import java.awt.Graphics2D;
import main.*;

public class Brick {
    public Block b[] = new Block[4];
    public Block tempB[] = new Block[4];
    int autoDropCounter = 0;
    public int direction = 1;
    boolean leftCollision, rightCollision, bottomCollison;
    public boolean active = true;
    public boolean deactivating;
    int deactivatingCounter = 0;

    public void create(Color c) {
        b[0] = new Block(c);
        b[1] = new Block(c);
        b[2] = new Block(c);
        b[3] = new Block(c);
        tempB[0] = new Block(c);
        tempB[1] = new Block(c);
        tempB[2] = new Block(c);
        tempB[3] = new Block(c);
    }

    public void setXY(int x, int y) {
    };

    public void updateXY(int direction) {
        checkRotationCollision();
        if (leftCollision == false && rightCollision == false && bottomCollison == false) {
            this.direction = direction;
            b[0].x = tempB[0].x;
            b[0].y = tempB[0].y;
            b[1].x = tempB[1].x;
            b[1].y = tempB[1].y;
            b[2].x = tempB[2].x;
            b[2].y = tempB[2].y;
            b[3].x = tempB[3].x;
            b[3].y = tempB[3].y;
        }

    };

    // direction
    public void getDirection_1() {
    }

    public void getDirection_2() {
    }

    public void getDirection_3() {
    }

    public void getDirection_4() {
    }

    // check collision
    public void checkMovementCollision() {
        leftCollision = false;
        rightCollision = false;
        bottomCollison = false;
        checkStaticBlockCollision();
        // check frame collision
        for (int i = 0; i < b.length; i++) {
            if (b[i].x == PlayManager.left_x) {
                leftCollision = true;
            }
        }
        for (int i = 0; i < b.length; i++) {
            if (b[i].x + Block.size == PlayManager.right_x) {
                rightCollision = true;
            }
        }
        for (int i = 0; i < b.length; i++) {
            if (b[i].y + Block.size == PlayManager.bottom_y) {
                bottomCollison = true;
            }
        }
    }

    public void checkRotationCollision() {
        leftCollision = false;
        rightCollision = false;
        bottomCollison = false;
        checkStaticBlockCollision();
        // check frame collision
        for (int i = 0; i < b.length; i++) {
            if (tempB[i].x < PlayManager.left_x) {
                leftCollision = true;
            }
        }
        for (int i = 0; i < b.length; i++) {
            if (tempB[i].x + Block.size > PlayManager.right_x) {
                rightCollision = true;
            }
        }
        for (int i = 0; i < b.length; i++) {
            if (tempB[i].y + Block.size > PlayManager.bottom_y) {
                bottomCollison = true;
            }
        }
    }

    private void checkStaticBlockCollision() {
        for (int i = 0; i < PlayManager.staticBlocks.size(); i++) {
            int target_x = PlayManager.staticBlocks.get(i).x;
            int target_y = PlayManager.staticBlocks.get(i).y;
            for (int j = 0; j < b.length; j++) {
                if (b[j].y + Block.size == target_y && b[j].x == target_x) {
                    bottomCollison = true;
                }
            }
            for (int j = 0; j < b.length; j++) {
                if (b[j].x - Block.size == target_x && b[j].y == target_y) {
                    leftCollision = true;
                }
            }
            for (int j = 0; j < b.length; j++) {
                if (b[j].x + Block.size == target_x && b[j].y == target_y) {
                    rightCollision = true;
                }
            }
        }
    }

    private void counterDeactivating() {
        deactivatingCounter++;
        if (deactivatingCounter == 45) {
            deactivatingCounter = 0;
            checkMovementCollision();
            if (bottomCollison) {
                active = false;
            }
        }
    }

    // update and draw
    public void update() {
        if (deactivating) {
            counterDeactivating();
        }
        if (KeyHandle.upPressed) {
            switch (direction) {
                case 1:
                    getDirection_2();
                    break;
                case 2:
                    getDirection_3();
                    break;
                case 3:
                    getDirection_4();
                    break;
                case 4:
                    getDirection_1();
                    break;
            }
            KeyHandle.upPressed = false;
            GamePanel.se.playMusic(3, false);
        }

        checkMovementCollision();

        if (KeyHandle.downPressed) {
            if (bottomCollison == false) {
                b[0].y += Block.size;
                b[1].y += Block.size;
                b[2].y += Block.size;
                b[3].y += Block.size;
                autoDropCounter = 0;

            }
            KeyHandle.downPressed = false;
        }
        if (KeyHandle.leftPressed) {
            if (leftCollision == false) {
                b[0].x -= Block.size;
                b[1].x -= Block.size;
                b[2].x -= Block.size;
                b[3].x -= Block.size;
            }
            KeyHandle.leftPressed = false;
        }
        if (KeyHandle.rightPressed) {
            if (rightCollision == false) {
                b[0].x += Block.size;
                b[1].x += Block.size;
                b[2].x += Block.size;
                b[3].x += Block.size;
            }
            KeyHandle.rightPressed = false;
        }
        if (bottomCollison) {
            if (deactivating == false) {
                GamePanel.se.playMusic(4, false);
            }
            deactivating = true;
        } else {
            autoDropCounter++;

            if (autoDropCounter == PlayManager.dropInterval) {
                b[0].y += Block.size;
                b[1].y += Block.size;
                b[2].y += Block.size;
                b[3].y += Block.size;
                autoDropCounter = 0;
            }
        }

    };

    public void draw(Graphics2D g2) {
        int margin = 2;
        g2.setColor(b[0].c);
        g2.fillRect(b[0].x + margin, b[0].y + margin, Block.size - (margin * 2), Block.size - (margin * 2));
        g2.fillRect(b[1].x + margin, b[1].y + margin, Block.size - (margin * 2), Block.size - (margin * 2));
        g2.fillRect(b[2].x + margin, b[2].y + margin, Block.size - (margin * 2), Block.size - (margin * 2));
        g2.fillRect(b[3].x + margin, b[3].y + margin, Block.size - (margin * 2), Block.size - (margin * 2));
    };
}