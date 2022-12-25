package fr.niixx.melchior.cli;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import fr.niixx.melchior.cli.commands.CasparCG;
import fr.niixx.melchior.cli.commands.Config;
import fr.niixx.melchior.cli.commands.Player;
import fr.niixx.melchior.cli.errors.CommandArgumentsException;
import fr.niixx.melchior.cli.errors.UnknownCommandException;

public class Cli {
	
	private BufferedWriter tx;
	private BufferedReader rx;
	public String[] currentCommand = {""};
	
	private Boolean promptText = true;
	private Boolean headlessMode = false;

	public Cli(OutputStream btx, InputStream brx) throws IOException, InterruptedException {	
		initBasicIO(btx, brx);
		String[] noCommand = {""};
		while(true) {
			currentCommand = prompt();
			if(currentCommand[0].equals("exit")) {
				print("Exited");
				return;
			}
			
			try {
				switch(currentCommand[0]) {
					case "set": cmd_set(); break;
					case "config": new Config(this); break;
					case "casparcg": new CasparCG(this); break; 
					case "player": new Player(this); break;
					default: throw new UnknownCommandException();
				}
			} catch(Exception e) {
				print("\u001B[31m" + e.getMessage() + "\u001B[0m");
			}
			
			currentCommand = noCommand;
		}
						
	}
	
	private void cmd_set() throws Exception {
		switch(currentCommand[1]) {
			case "prompt":
				switch(currentCommand[2]) {
					case "on": promptText = true; print("Prompt Text Enabled"); break;
					case "off": promptText = false; print("Prompt Text Disabled"); break;
				}
				break;
				
			case "headless":
				switch(currentCommand[2]) {
					case "on": if(tx == null) break; headlessMode = true; print("Headless Mode Enabled"); break;
					case "off": if(tx == null) break; headlessMode = false; print("Headless Mode Disabled"); break;
				}
				break;
				
			default: throw new CommandArgumentsException();
		}
	}
	
	public String[] prompt() throws IOException {
		if(promptText && !headlessMode) {
			tx.write(currentCommand[0] + "> ");
			tx.flush();
		}
		
		String line = null;
		
		while ((line = rx.readLine()) != null) {
			if(line.replace(" ", "") == "") {
				tx.write(currentCommand[0] + "> ");
				tx.flush();
				continue;
			}
			
			line = line.replaceFirst("^[ ]+", "");
			line = line.replaceFirst("[ ]+$", "");
			
			if(line.contains(" ")) return line.split(" ");
			else {
				String[] cmd = { line };
				return cmd;
			}	
		}
		throw new IOException("Cannot parse line");
		
	}
	
	public void print(String line) throws IOException {
		if(headlessMode) return;
		tx.write(line);
		tx.newLine();
		tx.flush();
	}
	
	private void initBasicIO(OutputStream tx, InputStream rx) throws IOException, InterruptedException {
		if(rx == null) throw new IOException("Input stream is null or undefined.");
		
		InputStreamReader rx_reader = new InputStreamReader(rx, "UTF-8");
		this.rx = new BufferedReader(rx_reader);
		
		OutputStreamWriter tx_writer;
		if(tx != null) {
			tx_writer = new OutputStreamWriter(tx, "UTF-8");
			this.tx = new BufferedWriter(tx_writer);
			
			this.tx.write("\u001B[0m");
			this.tx.flush();
		} else {
			this.tx = null;
			this.headlessMode = true;
		}
	}

}
