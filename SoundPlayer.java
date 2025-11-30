package Sound;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SoundPlayer {
	public static void playSound(String filepath) {
		try {
			File soundFile = new File(filepath);
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
			Clip clip = AudioSystem.getClip();
			clip.open(audioStream);
			clip.setFramePosition(0);//biar bisa di ulang dari awal
			clip.start(); //play sound
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e ) {
			e.printStackTrace();
		}
	}
}
