package detail;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Block extends Rectangle {
    public int x, y;
    public static final int size = 30;
    public Color c;
    int margin = 2;

    public Block(Color c) {
        this.c = c;
    }

    public void draw(Graphics2D g2) {
        g2.setColor(c);
        g2.fillRect(x + margin, y + margin, size - (margin * 2), size - (margin * 2));
    }
}
