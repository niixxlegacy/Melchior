package fr.niixx.melchior;

import java.io.IOException;

import fr.niixx.melchior.casparsocket.CasparCommands;
import fr.niixx.melchior.casparsocket.CasparSocket;

public class Main implements Registry {
	public static void main(String[] args) {
		config.put("casparcg.server.address", "echo.niixx.net");
		config.put("casparcg.server.port", "5250");
		config.put("casparcg.media.defaultChannel", "1");
		
		try {
			CasparSocket cs = new CasparSocket();
			System.out.println(cs.send(CasparCommands.ping()));
		} catch (IOException e) {
			System.out.println("LOL C'est PT : " + e.getMessage());
		}
	}
}

/*
	
*/