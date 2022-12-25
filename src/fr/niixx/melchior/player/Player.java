package fr.niixx.melchior.player;

import java.io.IOException;
import java.sql.SQLException;

import fr.niixx.melchior.Registry;
import fr.niixx.melchior.casparsocket.CasparCommands;
import fr.niixx.melchior.playlist.Playlist;
import fr.niixx.melchior.playlist.PlaylistItem;

public class Player implements Registry {
	
	private PlayerThread currentThread;
	
	private PlaylistItem currentlyPlaying;
	private Boolean isPlaying = false;
	private Playlist playlist = null;
	
	public Player() {
		
	}

	public void init(String playlistName) throws ClassNotFoundException, SQLException {
		playlist = new Playlist(playlistName);
	}
	
	private String start() throws IOException {
		currentlyPlaying = playlist.current();
		if(currentlyPlaying == null) {
			isPlaying = false;
			return "Playlist Ended";
		}
		String out = "Playing " + currentlyPlaying.label + " (" + String.valueOf(playlist.getIndex() + 1) + "/" + String.valueOf(playlist.getLastIndex() + 1) + ")";
		
		switch(currentlyPlaying.type) {
			case PlaylistItem.Comment: break;
			case PlaylistItem.Media: casparsocket.send(CasparCommands.playMedia(currentlyPlaying.content, currentlyPlaying.layer)); break;
			case PlaylistItem.Web: casparsocket.send(CasparCommands.playWeb(currentlyPlaying.content, currentlyPlaying.layer)); break;
			case PlaylistItem.Command: casparsocket.send(currentlyPlaying.content); break;
		}
		
		currentThread = new PlayerThread(currentlyPlaying.duration, this);
		currentThread.start();
		return out;
	}
	
	private void destroyPlayer() {
		if(currentThread != null && currentThread.isAlive()) {
			currentThread.interrupt();
			currentThread = null;
		}	
	}
	
	public String play() throws IOException {
		if(isPlaying) return "Already playing";
		if(playlist == null) return "Not Initiated";
		isPlaying = true;
		return skip();
	}
	
	public String stop() {
		if(!isPlaying) return "Already Stopped";
		isPlaying = false;
		destroyPlayer();
		return "Player Stopped";
	}
	
	public String skip() throws IOException {
		if(!isPlaying) return "Not Playing";
		playlist.advance();
		destroyPlayer();
		return start();
	}
	
	public String jump(String index) throws IOException {
		String clean_index = index.replaceAll("[^0-9]", "");
		if(clean_index.length() == 0) return "Wrong index specified";
		playlist.jump(Integer.valueOf(clean_index) - 1);
		destroyPlayer();
		return start();
	}
	
	public String infonodb() {
		if(isPlaying) return "Playing " + currentlyPlaying.label + " (" + String.valueOf(playlist.getIndex() + 1) + "/" + String.valueOf(playlist.getLastIndex() + 1) + ")";
		else if(currentlyPlaying != null) return "Stopped at " + currentlyPlaying.label + " (" + String.valueOf(playlist.getIndex() + 1) + "/" + String.valueOf(playlist.getLastIndex() + 1) + ")";
			else return "Stopped (No media loaded)";
	}
	
	public String info() {
		String out = infonodb() + '\n';
		out += playlist.getlist();
		return out;
	}
	
	public void handleMediaEndEvent() {
		try {
			skip();
		} catch (IOException e) {
			isPlaying = false;
		}
	}	
}
