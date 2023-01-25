package fr.niixx.melchior.playlist;

import java.sql.SQLException;
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
	private int lastIndex = -1;
	private PlaylistItem currentItem = null;
	private String dbprint;

	public Playlist(String playlistName) throws ClassNotFoundException, SQLException {
		reset(playlistName);
	}
	
	public void reset(String playlistName) throws ClassNotFoundException, SQLException {
		this.list = new ArrayList<>();
		this.playlistName = playlistName;
		this.currentIndex = -1;
		this.lastIndex = -1;
		this.currentItem = null;
		
		DBConnector db = new DBConnector();
		DBResult res = db.queryRead("SELECT * FROM pl_" + this.playlistName);
		for(HashMap<String, Object> item : res) {
			int type =			(item.get("type") == null)		? 0 : (int)item.get("type");
			String label =		(item.get("label") == null)		? "" : (String)item.get("label");
			String content =	(item.get("content") == null)	? "" : (String)item.get("content");
			int duration =		(item.get("duration") == null)	? 0 : (int)item.get("duration");
			int layer =			(item.get("layer") == null)		? 0 : (int)item.get("layer");
			
			list.add(new PlaylistItem(type, label, content, duration, layer));
		}
			
		db.close();
		
		dbprint = res.print();
		lastIndex = list.size() - 1;
		return;
	}

	public PlaylistItem advance() {
		currentIndex++;
		return jump(currentIndex);
	}
	
	public PlaylistItem jump(int index) {
		if(index > lastIndex) {
			currentIndex = -1;
			currentItem = null;
			return null;
		}
		
		currentIndex = index;
		currentItem = list.get(index);
		return currentItem;
	}
	
	public PlaylistItem current() {
		return currentItem;
	}
	
	public int getLastIndex() {
		return lastIndex;
	}
	
	public String getlist() {
		return dbprint;
	}
	
	public int getIndex() {
		return currentIndex;
	}
	
	public Iterator<PlaylistItem> iterator() {
		return list.iterator();
	}
	
}