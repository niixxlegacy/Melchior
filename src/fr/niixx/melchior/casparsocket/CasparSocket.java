package fr.niixx.melchior.casparsocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.niixx.melchior.Registry;

public class CasparSocket implements Registry {
	private Socket socket = null;
	private PrintWriter tx = null;
	private BufferedReader rx = null;
	private String serverAddress = null;
	private int serverPort = -1;
	private boolean connected = false;
	
	public CasparSocket() {

	}
	
	public synchronized void open() throws UnknownHostException, IOException {
		if (connected) return;
		
		serverAddress = config.get("casparcg.server.address");
		serverPort = Integer.parseInt(config.get("casparcg.server.port"));
		
		socket = new Socket(InetAddress.getByName(serverAddress), serverPort);
		tx = new PrintWriter(socket.getOutputStream(), true);
		rx = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
		connected = true;
	}
	
	public synchronized void close() throws IOException {
		if (!connected) return;
		tx.close();
		rx.close();
		socket.close();
		socket = null;
		rx = null;
		tx = null;
		serverAddress = null;
		serverPort = -1;
		connected = false;
	}
		
	public synchronized String send(String cmd) throws IOException {
		if (!connected) return "000 Socket not opened";
		
		tx.println(cmd);
		
		String line = rx.readLine();
		Matcher myMatcher = Pattern.compile("^\\d+").matcher(line);
		myMatcher.find();
		int status = Integer.parseInt(line.substring(myMatcher.start(), myMatcher.end()));
		
		Pattern endSequence = null;
		String response = "";
		Boolean getResponse = true;
		int charsRemove = 0;
		
		switch(status) {
			case 200: getResponse = true;	endSequence = Pattern.compile("\\r\\n\\r\\n$");	charsRemove = 4;	break;
			case 201: getResponse = true;	endSequence = Pattern.compile("\\r\\n$");		charsRemove = 2;	break;
			case 202: getResponse = false;	response = "OK";													break;
			
			case 400: getResponse = true;	endSequence = Pattern.compile("\\r\\n$");		charsRemove = 2;	break;
			case 401: getResponse = false;	response = "Illegal Video Channel";									break;
			case 402: getResponse = false;	response = "Parameter Missing";										break;
			case 403: getResponse = false;	response = "Illegal Parameter";										break;
			case 404: getResponse = false;	response = "Media File Not Found";									break;
			
			case 500: getResponse = false;	response = "Internal Server Error";									break;
			case 501: getResponse = false;	response = "Internal Server Error";									break;
			case 502: getResponse = false;	response = "Media File Unreachable";								break;
			case 503: getResponse = false;	response = "Access Error";											break;
		}
				
		if (getResponse) {
			response = "";
			while(true) {
				char character = (char) rx.read();
				response += character;
				if (endSequence.matcher(response).find()) {
					break;
				}		
			}
			response = response.substring(0, response.length()-charsRemove);
		}
		
		return String.valueOf(status) + " " + response;
		
	}
}