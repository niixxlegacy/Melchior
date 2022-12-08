package fr.niixx.melchior.dbconnector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import fr.niixx.melchior.Registry;

public class DBConnector implements Registry {
	
	Connection dbConnection;

	public DBConnector() throws ClassNotFoundException, SQLException {
	      Class.forName("com.mysql.cj.jdbc.Driver");
	      	      
	      String ServerAddress = "jdbc:mysql://"
	    	+ "@" + config.get("database.server.address")
	      	+ ":" + config.get("database.server.port")
	      	+ "/" + config.get("database.server.base");
	      
	      this.dbConnection = DriverManager.getConnection(ServerAddress, config.get("database.server.user"), config.get("database.server.pass"));
	}
	
	public void close() throws SQLException {
		dbConnection.close();
	}
	
	public DBResult queryRead(String query) throws SQLException {
		Statement stmt = this.dbConnection.createStatement();
		ResultSet res = stmt.executeQuery(query);
		
		return new DBResult(res);
	}
	
	public int queryWrite(String query) throws SQLException {
		Statement stmt = this.dbConnection.createStatement();
		return stmt.executeUpdate(query);
	}	
}