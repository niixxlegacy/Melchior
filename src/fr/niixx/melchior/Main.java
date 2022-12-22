package fr.niixx.melchior;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import fr.niixx.melchior.cli.Cli;

public class Main implements Registry {
	public static void main(String[] args) throws IOException, InterruptedException {
		String str = "config set casparcg.server.address echo.niixx.net\nconfig set casparcg.server.port 5250\nconfig set casparcg.media.defaultChannel 1\nexit";
	    ByteArrayInputStream stream = new ByteArrayInputStream(str.getBytes("UTF-8"));
		
		Cli autostart = new Cli(System.out, stream);
		Cli cli = new Cli(System.out, System.in);
	}
	
	
}

/*
		config.put("casparcg.server.address", "echo.niixx.net");
		config.put("casparcg.server.port", "5250");
		config.put("casparcg.media.defaultChannel", "1");
		
		config.put("database.server.address", "echo.niixx.net");
		config.put("database.server.port", "3306");
		config.put("database.server.base", "melchior");
		config.put("database.server.user", "melchior");
		config.put("database.server.pass", "CeNestPasUnMotDePasse");
		
		try {
			CasparSocket cs = new CasparSocket();
			Playlist pl_test = new Playlist("test");
						
			for(PlaylistItem item : pl_test) {
				switch(item.type) {
					case PlaylistItem.Comment: break;
					case PlaylistItem.Media: cs.send(CasparCommands.playMedia(item.content, item.layer)); Thread.sleep(item.duration); break;
					case PlaylistItem.Web: cs.send(CasparCommands.playWeb(item.content, item.layer)); Thread.sleep(item.duration); break;
					case PlaylistItem.Command: cs.send(item.content); Thread.sleep(item.duration); break;
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
*/

/*
	config.put("database.server.address", "echo.niixx.net");
	config.put("database.server.port", "3306");
	config.put("database.server.base", "melchior");
	config.put("database.server.user", "melchior");
	config.put("database.server.pass", "CeNestPasUnMotDePasse");
	
	try {
		DBConnector db = new DBConnector();
		//System.out.println(db.queryWrite("INSERT INTO melchior.config (`key`,`value`) VALUES ('casparcg.media.defaultChannel','1')"));
		
		DBResult res = db.queryRead("SELECT * FROM config");
		System.out.print(res.print());
					
	} catch (ClassNotFoundException | SQLException e) {
		System.out.println("LOL C'est PT : " + e.getMessage());
		e.printStackTrace();
	}
 */

/*
	config.put("casparcg.server.address", "echo.niixx.net");
	config.put("casparcg.server.port", "5250");
	config.put("casparcg.media.defaultChannel", "1");
	
	try {
		CasparSocket cs = new CasparSocket();
		
			cs.send(CasparCommands.playMedia("push-bumper-title-0.mp4", 1));
			Thread.sleep(3000);
			cs.send(CasparCommands.playWeb("http://localhost:8001", 1));
			Thread.sleep(10000);
			cs.send(CasparCommands.clear());
			
		System.out.println(cs.send(CasparCommands.ping()));
	} catch (IOException e) {
		System.out.println("LOL C'est PT : " + e.getMessage());
	}
*/