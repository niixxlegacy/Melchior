package fr.niixx.melchior.playlist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import fr.niixx.melchior.Registry;
import fr.niixx.melchior.dbconnector.DBConnector;
import fr.niixx.melchior.dbconnector.DBResult;

public class Playlist implements Registry, Iterable<PlaylistItem> {
	
	private ArrayList<PlaylistItem> list = new ArrayList<>();
	private String playlistName;
	private int currentIndex = -1;
	private int lastIndex;
	private PlaylistItem currentItem = null;

	public Playlist(String playlistName) throws Exception {
		this.playlistName = playlistName;
		if(!update()) throw new Exception("Unable to update playlist");
	}
	
	public boolean update() {
		try {
			DBConnector db = new DBConnector();
			DBResult res = db.queryRead("SELECT * FROM pl_" + this.playlistName);
			for(HashMap<String, Object> item : res)
				list.add(new PlaylistItem((int)item.get("type"), (String)item.get("label"), (String)item.get("content"), (int)item.get("duration"), (int)item.get("layer")));
			db.close();
			
			this.lastIndex = list.size() - 1;
			return true;
		} catch(Exception e) {
			return false;
		}
	}

	public PlaylistItem advance() {
		currentIndex++;
		if(currentIndex > lastIndex) return null;
		else return jump(currentIndex);
	}
	
	public PlaylistItem jump(int index) {
		this.currentItem = this.list.get(index);
		return list.get(index);
	}
	
	public PlaylistItem current() {
		return currentItem;
	}
	
	public int getLastIndex() {
		return lastIndex;
	}
	
	public Iterator<PlaylistItem> iterator() {
		return list.iterator();
	}
	
}