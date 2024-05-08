package detail;

import java.awt.Color;

public class Brick {
    public MiniBlock b[] = new MiniBlock[4];
    public MiniBlock tempB[] = new MiniBlock[4];

    public void create(Color c) {
        b[0] = new MiniBlock(c);
        b[1] = new MiniBlock(c);
        b[2] = new MiniBlock(c);
        b[3] = new MiniBlock(c);
        tempB[0] = new MiniBlock(c);
        tempB[1] = new MiniBlock(c);
        tempB[2] = new MiniBlock(c);
        tempB[3] = new MiniBlock(c);
    }

    public void setXY(int x, int y) {
    }

    public void updateXY(int direction) {
    }

    public void update() {
    }
}