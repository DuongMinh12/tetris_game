package detail;

import java.awt.Color;

public class Brick_Square extends Brick {
    public Brick_Square() {
        create(Color.PINK);
    }

    public void setXY(int x, int y) {
        // 0 0
        // 0 0
        //
        b[0].x = x;
        b[0].y = y;
        b[1].x = b[0].x;
        b[1].y = b[0].y + Block.size;
        b[2].x = b[0].x + Block.size;
        b[2].y = b[0].y;
        b[3].x = b[0].x + Block.size;
        b[3].y = b[0].y + Block.size;
    }

    public void getDirection_1() {
    }

    public void getDirection_2() {

    }

    public void getDirection_3() {
    }

    public void getDirection_4() {

    }

}
