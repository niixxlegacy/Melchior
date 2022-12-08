package fr.niixx.melchior;

import java.sql.SQLException;

import fr.niixx.melchior.dbconnector.DBConnector;
import fr.niixx.melchior.dbconnector.DBResult;

public class Main implements Registry {
	public static void main(String[] args) {
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
	}
}

/*
	config.put("casparcg.server.address", "echo.niixx.net");
	config.put("casparcg.server.port", "5250");
	config.put("casparcg.media.defaultChannel", "1");
	
	try {
		CasparSocket cs = new CasparSocket();
		System.out.println(cs.send(CasparCommands.ping()));
	} catch (IOException e) {
		System.out.println("LOL C'est PT : " + e.getMessage());
	}
*/