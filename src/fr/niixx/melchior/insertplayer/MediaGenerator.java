package fr.niixx.melchior.insertplayer;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ThreadLocalRandom;

import fr.niixx.melchior.Registry;

public class MediaGenerator implements Registry {
	public MediaGenerator() {
		
	}
	
	public String create(String template, String[] args, int duration) throws Exception {
		String id = generateId();
		String data = getTemplate(template);
				
		for(int i = 0; i < args.length; i++) data = data.replaceAll("!%" + i, args[i]);
		
		data = data.replaceAll("!%duration", String.valueOf(duration));
		
		overlays.pages.put(id, data);
		return id;
	}
	
	private String generateId() {
		char[] allowedChars = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
		String id = "";
		
		for(int i = 1; i <= 16; i++)
			id += allowedChars[ThreadLocalRandom.current().nextInt(0, allowedChars.length)];
		
		return id;
	}
	
	private String getTemplate(String templateName) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("./html/" + templateName + ".html"), "utf-8"));
		StringBuilder sb = new StringBuilder();
		String line;
		while((line = br.readLine()) != null) sb.append(line);
		br.close();
		return sb.toString();
	}
}