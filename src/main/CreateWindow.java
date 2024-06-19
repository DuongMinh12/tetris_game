package main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateWindow implements ActionListener {
    JFrame window;
    JMenu menu, submenu;
    JMenuItem i1, i2, i3, i4, i5, i6;
    JMenuBar mb = new JMenuBar();

    CreateWindow() {
        window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);

        // add panel to the window
        GamePanel gp = new GamePanel();
        window.add(gp);
        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);
        gp.launchGame();

        // create menu
        JMenu menu, submenu, submenu2;
        JMenuItem i1, i2, i3, i4, i5, i6, i7, i8;
        JMenuBar mb = new JMenuBar();
        menu = new JMenu("Game");
        submenu = new JMenu("Select Difficulty");
        i1 = new JMenuItem("New Game");
        i2 = new JMenuItem("Save Game");
        i3 = new JMenuItem("Load Game");
        i4 = new JMenuItem("Easy");
        i5 = new JMenuItem("Medium");
        i6 = new JMenuItem("Hard");
        submenu2 = new JMenu("Music");
        i7 = new JMenuItem("Play");
        i8 = new JMenuItem("Pause");

        menu.add(i1);
        menu.add(i2);
        menu.add(i3);
        submenu.add(i4);
        submenu.add(i5);
        submenu.add(i6);
        submenu2.add(i7);
        submenu2.add(i8);

        menu.add(submenu);
        menu.add(submenu2);
        mb.add(menu);
        window.setJMenuBar(mb);
        window.setLayout(null);
        window.setVisible(true);

        i1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gp.restartGame();
            }
        });
        i2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        i3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        i4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gp.beginEasy();
            }
        });
        i5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gp.beginNormal();
            }
        });
        i6.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gp.beginHard();
            }
        });
        i7.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                GamePanel.music.stop();
                GamePanel.music.playMusic(0, true);
                GamePanel.music.loop();
            }
        });
        i8.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                GamePanel.music.stop();
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
