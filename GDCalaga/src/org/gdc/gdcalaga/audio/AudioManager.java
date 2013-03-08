package org.gdc.gdcalaga.audio;

import java.io.File;
import java.util.HashMap;

import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;


/**
 * Audio manager that wraps Slick2D's OpenAL support. Provides a singleton for global
 * access throughout the game. Manages background music playback as well as sound effects.
 * 
 * @author vtdecoy
 *
 */
public class AudioManager {

	private static final String MUSIC_PATH = "." + File.separator + "sound" + File.separator + "music" + File.separator;
	private static final String SFX_PATH = "." + File.separator + "sound" + File.separator + "sfx" + File.separator;
	
	private static final AudioManager manager = new AudioManager();
	
	public static AudioManager getAudioManager() {
		return manager;
	}
	
	private HashMap<AudioAsset,String> idToMusicFile = new HashMap<AudioAsset,String>();
	private HashMap<AudioAsset,Sound> idToSound = new HashMap<AudioAsset,Sound>();
	private Music bgMusic;
	
	private AudioManager() {
		
	}
	
	public void loadAudioAssets() throws SlickException {
		//Considering converting to straight arrays since the count is known ahead of time, would reduce lookup time
		
		//Just save the paths, we can load music on demand
		idToMusicFile.put(AudioAsset.MUSIC_LEVEL_1, MUSIC_PATH + "music.ogg");
		
		//Preload sounds to allow quicker playback
		idToSound.put(AudioAsset.SFX_FIRE1, new Sound(SFX_PATH + "fire1.ogg"));
		idToSound.put(AudioAsset.SFX_FIRE2, new Sound(SFX_PATH + "fire2.wav")); //ogg won't load, try reencoding with Audacity, no luck
	}
	
	public void playMusic(AudioAsset id) throws SlickException {
		String path = idToMusicFile.get(id);
		if (path != null) {
			bgMusic = new Music(path);
			bgMusic.loop();
		}
		else {
			throw new RuntimeException("Error loading music for " + id);
		}
	}
	
	public void pauseMusic() {
		if (bgMusic != null) {
			bgMusic.pause();
		}
	}
	
	public void stopMusic() {
		if (bgMusic != null) {
			bgMusic.stop();
		}
	}
	
	public void playMusic() {
		if (bgMusic != null) {
			bgMusic.loop();
		}
	}
	
	public void playSFX(AudioAsset id) {
		Sound snd = idToSound.get(id);
		if (snd != null) {
			snd.play();
		}
	}
	
}
