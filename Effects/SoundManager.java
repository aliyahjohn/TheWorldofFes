package Effects;
import javax.sound.sampled.AudioInputStream;		// for playing sound clips
import javax.sound.sampled.*;
import java.io.*;
import java.util.HashMap;				// for storing sound clips

public class SoundManager {				// a Singleton class
	HashMap<String, Clip> clips;

	private static SoundManager instance = null;	// keeps track of Singleton instance

	private float volume;

	private SoundManager () {
		clips = new HashMap<String, Clip>();

		Clip clip = loadClip("sounds/background.wav");	// played from start of the game
		clips.put("background", clip);

		clip = loadClip("sounds/death.wav");	// played when player dies
		clips.put("death", clip);

		clip = loadClip("sounds/berry.wav");	// played when in contact with berry
		clips.put("berry", clip);

		clip = loadClip("sounds/fire.wav");	// played when in contact with fire
		clips.put("fire", clip);

		clip = loadClip("sounds/twinkle.wav");	// played when in contact with twinkle
		clips.put("twinkle", clip);

        clip = loadClip("sounds/wind.wav");	// played when in contact with wind
		clips.put("wind", clip);

		clip = loadClip("sounds/dirt.wav");	// played when in contact with earth tone
		clips.put("dirt", clip);

		clip = loadClip("sounds/complete.wav");	// played when collected all items
		clips.put("complete", clip);

		clip = loadClip("sounds/bye.wav");	// played when in contact with earth tone
		clips.put("bye", clip);

		volume = 1.0f;
	}


	public static SoundManager getInstance() {	// class method to retrieve instance of Singleton
		if (instance == null)
			instance = new SoundManager();
		
		return instance;
	}		


    	public Clip loadClip (String fileName) {	// gets clip from the specified file
 		AudioInputStream audioIn;
		Clip clip = null;

		try {
    			File file = new File(fileName);
    			audioIn = AudioSystem.getAudioInputStream(file.toURI().toURL()); 
    			clip = AudioSystem.getClip();
    			clip.open(audioIn);
		}
		catch (Exception e) {
 			System.out.println ("Error opening sound files: " + e);
		}
    		return clip;
    	}


	public Clip getClip (String title) {

		return clips.get(title);
	}


    	public void playClip(String title, boolean looping) {
		Clip clip = getClip(title);
		if (clip != null) {
			clip.setFramePosition(0);
			if (looping)
				clip.loop(Clip.LOOP_CONTINUOUSLY);
			else
				clip.start();
		}
    	}

		public void pauseClip(String title, Boolean isPaused){
			long clipTime;
			Clip clip = getClip(title);

			clipTime= clip.getMicrosecondPosition();

			if (isPaused){
				stopClip("background");
			}
			else{
				clip.setMicrosecondPosition(clipTime);
				clip.start();
			}

		}

    	public void stopClip(String title) {
		Clip clip = getClip(title);
		if (clip != null) {
			clip.stop();
		}
    	}

}