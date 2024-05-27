import java.io.File;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

public class SoundPlayer {
    private Clip clip;
    public SoundPlayer (String file ) {
        try {
            File soundFile = new File(file);
            // Reference: https://docs.oracle.com/javase/7/docs/api/javax/sound/sampled/AudioInputStream.html
            AudioInputStream audio = AudioSystem.getAudioInputStream(soundFile);
            AudioFormat format = audio.getFormat();
            DataLine.Info info = new  DataLine.Info(Clip.class, format);
            clip = (Clip) AudioSystem.getLine(info);
            clip.open(audio);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
    public void play() {
        if(clip==null) return;
        if(clip.isRunning())
            clip.stop(); // Stop any other music if any already playing
        clip.setFramePosition(0); // Start from beginning of clip
        clip.start();
    }
}