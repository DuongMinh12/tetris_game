package detail;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class MiniBlock extends Rectangle {
    public int x, y;
    public static final int size = 30;
    public Color c;

    public MiniBlock(Color c) {
        this.c = c;
    }

    public void draw(Graphics2D g2) {
        g2.setColor(c);
        g2.fillRect(x, y, size, size);
    }
}
