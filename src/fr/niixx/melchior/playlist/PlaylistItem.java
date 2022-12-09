package fr.niixx.melchior.playlist;

public class PlaylistItem {
	
	public static final int Comment = 0;
	public static final int Media = 1;
	public static final int Web = 2;
	public static final int Command = 3;

	public int type;
	public String label;
	public int duration;
	public String content;
	public int layer;
	
	
	public PlaylistItem(int type, String label, String content, int duration, int layer) {
		this.type = type;
		this.label = label;
		this.duration = duration;
		this.content = content;
		this.layer = layer;
	}
}
