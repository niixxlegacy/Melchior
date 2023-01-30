package fr.niixx.melchior.insertplayer;

import java.io.IOException;
import java.util.HashMap;

import fr.niixx.melchior.Registry;
import fr.niixx.melchior.casparsocket.CasparCommands;
import fr.niixx.melchior.player.PlayerThread;

public class InsertPlayer implements Registry {
	
	public HashMap<String, String> pages;
	private HashMap<Integer, Integer> activeLayers;
	
	private WebServer server;
	private MediaGenerator generator;
	
	private Boolean isReady = false;
	
	public InsertPlayer() {
		
	}
	
	public void init() throws Exception {
		pages = new HashMap<>();
		activeLayers = new HashMap<>();
		server = new WebServer();
		generator = new MediaGenerator();
		
		if(pages == null || server == null || activeLayers == null || generator == null) isReady = false;
		else isReady = true;
	}
	
	public String request(String template, String[] args, int duration, int layer) throws Exception {
		if(!isReady) throw new Exception("Not initialized");
		
		String id = generator.create(template, args, duration);

		casparsocket.send(
			CasparCommands.playWeb(config.get("this.server.address") + "/" + id,
			(layer == 0) ? Integer.valueOf(config.get("casparcg.defaultLayer.inserts")) : layer)
		);
		
		//if(duration == 0) return "OK";
		
		//int count = (activeLayers.containsKey(layer) ? activeLayers.get(layer) : 0) + 1;
		//activeLayers.put(layer, count);
		
		//new InsertThread(duration + 2000, this, layer).start();
		return "OK";
	}
		
	public void clearLayer(int layer) {
		int count = activeLayers.get(layer) - 1;
		if(count < 0) count = 0;
		activeLayers.put(layer, count);
		if(count == 0) try {
			casparsocket.send(CasparCommands.clear(layer));
		} catch (IOException e) { }
	}
	
}
