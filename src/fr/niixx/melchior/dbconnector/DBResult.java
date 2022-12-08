package fr.niixx.melchior.dbconnector;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class DBResult {
	private ArrayList<HashMap<String, Object>> content = new ArrayList<>();
	private ArrayList<String> columns = new ArrayList<>();
	
	public DBResult(ResultSet input) throws SQLException {
		ResultSetMetaData resMetadata = input.getMetaData();
		
		for (int i = 1; i <= resMetadata.getColumnCount(); i++ ) columns.add(resMetadata.getColumnName(i));
		
	    while(input.next()) {
	    	HashMap<String, Object> row = new HashMap<>();
	    	for (int i = 1; i <= resMetadata.getColumnCount(); i++ ) row.put(columns.get(i - 1), input.getObject(i));
	    	content.add(row);
	    }
	}
	
	public int getSize() {
		return content.size();
	}
	
	public ArrayList<String> getColumns() {
		return columns;
	}
	
	public HashMap<String, Object> getLine(int line) {
		try {
			return content.get(line);
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}
	
	public Object getObject(int line, String column) {
		try {
			return content.get(line).get(column);
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
		
	}
	
	public String print() {
		// Fetch Longest String length by column
		ArrayList<Integer> columnLengths = new ArrayList<>();
		
		for(String column : columns) {
			ArrayList<String> strings = new ArrayList<>();
			strings.add(column);
			
			for(HashMap<String, Object> line : content)
				strings.add(line.get(column).toString());
			
			int maxStringLength = 0;
			for(int active_column = 0; active_column < strings.size(); active_column++)
			    if(strings.get(active_column).length() > maxStringLength)
					maxStringLength = strings.get(active_column).length();
			
			columnLengths.add(maxStringLength);
		}
		
		String out = "";
		
		// Generate top border
		out += "+";
		for(int active_column = 0; active_column < columns.size(); active_column++) {
			out += "-";
			for(int i = 0; i < columnLengths.get(active_column); i++) out += "-";
			out += "-+";
		}
		out += "\n";
		
		// Generate columns names
		out += "|";
		for(int active_column = 0; active_column < columns.size(); active_column++) {
			String content = columns.get(active_column);
			int spaceNeeded = columnLengths.get(active_column) - content.length();

			for(int i = 0; i < spaceNeeded; i++) content += " ";
			
			out += " ";
			out += content;
			out += " |";
		}
		out += "\n";
		
		// Generate middle border
		out += "+";
		for(int active_column = 0; active_column < columns.size(); active_column++) {
			out += "-";
			for(int i = 0; i < columnLengths.get(active_column); i++) out += "-";
			out += "-+";
		}
		out += "\n";
		
		// Generate table body
		for(HashMap<String, Object> line : content) {
			out += "|";
			for(int active_column = 0; active_column < columns.size(); active_column++) {
				String content = (String)line.get(columns.get(active_column).toString());
				int spaceNeeded = columnLengths.get(active_column) - content.length();

				for(int i = 0; i < spaceNeeded; i++) content += " ";
				out += " ";
				out += content;
				out += " |";
			}
			out += "\n";
		}
		
		// Generate bottom border
		out += "+";
		for(int active_column = 0; active_column < columns.size(); active_column++) {
			out += "-";
			for(int i = 0; i < columnLengths.get(active_column); i++) out += "-";
			out += "-+";
		}
		
		return out;
	}
}
