package main;

import java.net.URL;
import javax.sound.sampled.*;
import javax.sound.sampled.LineEvent.Type;

public class Sound {
    Clip musicClip;

    URL url[] = new URL[10];

    public Sound() {
        url[0] = getClass().getResource("/resources/tetris-theme-korobeiniki-rearranged-arr-for-music-box.wav");
        url[1] = getClass().getResource("/resources/delete_line.wav");
        url[2] = getClass().getResource("/resources/gameover.wav");
        url[3] = getClass().getResource("/resources/rotation.wav");
        url[4] = getClass().getResource("/resources/touch_floor.wav");
        url[5] = getClass()
                .getResource("/resources/machiavellian-nightmare-electronic-dystopia-ai-robot-machine-139385.wav");

    }

    public void playMusic(int i, boolean music) {
        try {
            // System.out.println("Erro:" + url[0]);
            AudioInputStream ais = AudioSystem.getAudioInputStream(url[i]);
            Clip clip = AudioSystem.getClip();
            clip.open(ais);
            clip.addLineListener(new LineListener() {

                @Override
                public void update(LineEvent event) {
                    if (event.getType() == Type.STOP) {
                        clip.close();
                    }
                }

            });
            if (music) {
                musicClip = clip;
            }
            ais.close();
            clip.start();
        } catch (Exception e) {
        }
    }

    public void loop() {
        if (musicClip != null) {
            musicClip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    public void stop() {
        musicClip.stop();
        musicClip.close();
    }
}
