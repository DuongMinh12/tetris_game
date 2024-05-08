package detail;

import java.awt.Color;
import java.awt.Graphics2D;

public class Brick_L1 extends Brick {
    public Brick_L1() {
        create(Color.orange);
    }

    public void setXY(int x, int y) {
        // 0
        // 0
        // 0 0
        // block center
        b[0].x = x;
        b[0].y = y;
        // block top
        b[1].x = b[0].x;
        b[1].y = b[0].y - Block.size;
        // block bottom
        b[2].x = b[0].x;
        b[2].y = b[0].y + Block.size;
        // block right
        b[3].x = b[0].x + Block.size;
        b[3].y = b[0].y + Block.size;
    }

    // @Override
    // public void updateXY(int direction) {
    // // TODO Auto-generated method stub
    // throw new UnsupportedOperationException("Unimplemented method 'updateXY'");
    // }

    // @Override
    // public void update() {
    // // TODO Auto-generated method stub
    // throw new UnsupportedOperationException("Unimplemented method 'update'");
    // }

    // @Override
    // public void draw(Graphics2D g2) {
    // // TODO Auto-generated method stub
    // throw new UnsupportedOperationException("Unimplemented method 'draw'");
    // }
}
