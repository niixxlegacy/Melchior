package fr.niixx.melchior.cli.commands;

import java.io.IOException;
import java.util.Arrays;

import fr.niixx.melchior.Registry;
import fr.niixx.melchior.casparsocket.CasparCommands;
import fr.niixx.melchior.cli.Cli;
import fr.niixx.melchior.cli.errors.CommandArgumentsException;

public class CasparCG extends DefaultCommand implements Registry {	
	public CasparCG(Cli cli) throws Exception {
		super(cli);
	}

	void cmds() throws Exception {
		switch(activeCommand[checkInt(1)]) {
			case "open":
				checkArgs(2, true);
				cmd_open();
				break;
				
			case "close":
				checkArgs(2, true);
				cmd_close();
				break;
				
			case "play":
				checkArgs(5, true);
				cmd_play(activeCommand[checkInt(2)], activeCommand[checkInt(3)], activeCommand[checkInt(4)]);
				break;
				
			case "stop":
				checkArgs(3, true);
				cmd_stop(activeCommand[checkInt(2)]);
				break;
				
			case "clear":
				checkArgs(3, true);
				cmd_clear(activeCommand[checkInt(2)]);
				break;
				
			case "ping":
				checkArgs(2, true);
				cmd_ping();
				break;
				
			case "exec":
				checkArgs(3, false);
				cmd_exec(activeCommand);
				break;
								
			default: throw new CommandArgumentsException();
		}
	}

	private void cmd_open() throws IOException {
		casparsocket.open();
		cli.print("OK");
	}
	
	private void cmd_close() throws IOException {
		casparsocket.close();
		cli.print("OK");
	}
	
	private void cmd_play(String type, String name, String layer) throws IOException, CommandArgumentsException {
		switch(type) {
			case "media": cli.print(casparsocket.send(CasparCommands.playMedia(name, Integer.valueOf(layer)))); break;
			case "web": cli.print(casparsocket.send(CasparCommands.playWeb(name, Integer.valueOf(layer)))); break;
			default: throw new CommandArgumentsException();
		}
	}
	
	private void cmd_stop(String layer) throws IOException {
		cli.print(casparsocket.send(CasparCommands.stopMedia(Integer.valueOf(layer))));
	}
	
	private void cmd_clear(String layer) throws IOException {
		cli.print(casparsocket.send(CasparCommands.clear(Integer.valueOf(layer))));
	}
	
	private void cmd_ping() throws IOException {
		cli.print(casparsocket.send(CasparCommands.ping()));
	}
	
	private void cmd_exec(String[] command) throws IOException {
		String[] cArray = Arrays.copyOfRange(command, checkInt(2), command.length);
		StringBuilder cBuilder = new StringBuilder();
		for(String cmd : cArray) cBuilder.append(cmd + " ");
		String cString = cBuilder.substring(0, cBuilder.length() - 1);
		
		cli.print(casparsocket.send(cString));
	}
	
}