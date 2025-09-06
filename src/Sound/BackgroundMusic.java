package Sound;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class BackgroundMusic implements Runnable {
    private String filePath;
    public BackgroundMusic(String filePath) {
        this.filePath = filePath;
    }
    @Override
    public void run() {
        try {
            File file = new File(filePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);

            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY); // play forever
            clip.start();

            // keep thread alive while sound is playing
            while (clip.isActive()) {
                Thread.sleep(100);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
