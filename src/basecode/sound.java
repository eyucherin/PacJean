package basecode;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 * This class initializes the background music for the game.
 * 
 * @author PacJean
 * @version 5/11/2021
 */
public class Sound {
	// This boolean value is always set to true so that background can continue
	// looping.
	private static boolean backgroundOn = true;

	/**
	 * This method takes a file name as parameter, reads the file and loops
	 * continuously as long as the background boolean value is set to true.
	 * 
	 * @param fileName
	 */
	public static void playsound(String fileName) {
		try {
			// Create a new file
			File musicPath = new File(fileName);
			// If the file exists, read the file from audioInput, open it, start it, and
			// then loop
			// continuously.
			if (musicPath.exists()) {
				AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
				Clip clip = AudioSystem.getClip();
				clip.open(audioInput);
				clip.start();
				while (backgroundOn) {
					clip.loop(Clip.LOOP_CONTINUOUSLY);
				}
			}
			// If file cannot be found, print error message.
			else {
				System.out.println("Can't find file");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}
}