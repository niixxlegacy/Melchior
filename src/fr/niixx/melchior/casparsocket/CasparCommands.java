package fr.niixx.melchior.casparsocket;

import fr.niixx.melchior.Registry;

public class CasparCommands implements Registry {

	public static String playMedia(String filename, int Layer) {
		if(Layer <= 0) Layer = 1;
		return "PLAY " + config.get("casparcg.media.defaultChannel") + "-" + String.valueOf(Layer) + " " + filename;
	}
	
	public static String stopMedia(int Layer) {
		if(Layer <= 0) Layer = 1;
		return "STOP " + config.get("casparcg.media.defaultChannel") + "-" + String.valueOf(Layer);
	}
	
	public static String clear() {
		return "CLEAR " + config.get("casparcg.media.defaultChannel");
	}
	
	public static String playWeb(String filename, int Layer) {
		if(Layer <= 0) Layer = 1;
		return "PLAY " + config.get("casparcg.media.defaultChannel") + "-" + String.valueOf(Layer) + " [HTML]\"" + filename + "\" MIX 0";
	}
	
	public static String ping() {
		return "INFO";
	}

}
