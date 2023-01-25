package fr.niixx.melchior.insertplayer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import fr.niixx.melchior.Registry;

public class WebServer {
	
	private HttpServer server;
	
	public WebServer() throws IOException {
		server = HttpServer.create(new InetSocketAddress(80), 0);
		server.createContext("/", new ConnHandler());
		server.start();
	}
}

class ConnHandler implements HttpHandler, Registry {
	@Override
	public void handle(HttpExchange ex) throws IOException {
		String query = ex.getRequestURI().getPath().substring(1);
		
		String page = overlays.pages.get(query);
		if(page == null) page = "";
		String response = page;

		ex.getResponseHeaders().add("content-type", "text/html");
		ex.sendResponseHeaders(200, response.length());
		OutputStream os = ex.getResponseBody();
		os.write(response.getBytes());
		os.close();
		
		overlays.pages.remove(query);
  }

}