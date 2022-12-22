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
import fr.niixx.melchior.cli.errors.UnknownCommandException;

public class Cli {
	
	private BufferedWriter tx;
	private BufferedReader rx;
	public String[] currentCommand = {""};

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
					case "config": new Config(this); break;
					case "casparcg": new CasparCG(this); break; 
					default: throw new UnknownCommandException();
				}
			} catch(Exception e) {
				print("\u001B[31m" + e.getMessage() + "\u001B[0m");
			}
			
			currentCommand = noCommand;
		}
						
	}
	
	public String[] prompt() throws IOException {
		tx.write(currentCommand[0] + "> ");
		tx.flush();
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
		tx.write(line);
		tx.newLine();
		tx.flush();
	}
	
	private void initBasicIO(OutputStream tx, InputStream rx) throws IOException, InterruptedException {
		if(tx == null || rx == null) throw new IOException("Input or output stream is null or undefined.");
		
		InputStreamReader rx_reader = new InputStreamReader(rx, "UTF-8");
		
		// Waiting for the reader to be ready (optional but should really be here)
		//for(int i = 0; i < 50; i++) {
		//	if(rx_reader.ready()) break;
		//	Thread.sleep(100);
		//	if(i == 50) throw new IOException("Timeout when trying to initialize the input stream reader");
		//}
		
		OutputStreamWriter tx_writer = new OutputStreamWriter(tx, "UTF-8");
			
		this.tx = new BufferedWriter(tx_writer);
		this.rx = new BufferedReader(rx_reader);
		
		this.tx.write("\u001B[0m");
		this.tx.flush();
	}

}
