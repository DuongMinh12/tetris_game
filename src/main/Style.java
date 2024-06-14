package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;

public class Style {
    public static final Color[] COLOR_BLOC_LISTE = {
            Color.decode("0x3A86FF"), Color.decode("0x8338EC"), Color.decode("0xFF006E"),
            Color.decode("0xFB5607"), Color.decode("0xFFBE0B"), Color.decode("0x60E9FF"), Color.decode("0x00DD7E")
    };

    public static final Color COLOR_BACKGROUND = Color.decode("0xDBEDFF");
    public static final Color COLOR_WINDOW = Color.decode("0xD1E3F5");
    public static final Color COLOR_BORDER = Color.decode("0x8AA7C2");
    public static final Color COLOR_DARK = Color.decode("0x253F60");
    public static final Color COLOR_SHADOW = Color.decode("0x597598");

    public static Font font1 = new Font("Zorque", Font.PLAIN, 15);
    public static Font font2 = new Font("Zorque", Font.BOLD, 25);
    public static Font font3 = new Font("Zorque", Font.PLAIN, 26);
    public static Font fontPause = new Font("Zorque", Font.BOLD, 50);
    public static Font fontInstructions = new Font("Zorque", Font.PLAIN, 20);

    static {
        try {
            font1 = Font.createFont(Font.PLAIN, new File("Fonts\\zorque.regular.ttf")).deriveFont(15f);
            font2 = Font.createFont(Font.PLAIN, new File("Fonts\\zorque.regular.ttf")).deriveFont(25f);
            font3 = Font.createFont(Font.PLAIN, new File("Fonts\\zorque.regular.ttf")).deriveFont(26f);
            fontPause = Font.createFont(Font.PLAIN, new File("Fonts\\zorque.regular.ttf")).deriveFont(50f);
            fontInstructions = Font.createFont(Font.PLAIN, new File("Fonts\\zorque.regular.ttf")).deriveFont(20f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(font1);
            ge.registerFont(font2);
            ge.registerFont(font3);
            ge.registerFont(fontPause);
            ge.registerFont(fontInstructions);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
    }

}
