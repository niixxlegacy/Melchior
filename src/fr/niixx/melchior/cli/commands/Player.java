package fr.niixx.melchior.cli.commands;

import java.io.IOException;
import java.sql.SQLException;

import fr.niixx.melchior.Registry;
import fr.niixx.melchior.cli.Cli;
import fr.niixx.melchior.cli.errors.CommandArgumentsException;

public class Player extends DefaultCommand implements Registry {	
	public Player(Cli cli) throws Exception {
		super(cli);
	}

	void cmds() throws Exception {
		switch(activeCommand[checkInt(1)]) {
			case "init":
				checkArgs(3, true);
				cmd_init(activeCommand[checkInt(2)]);
				break;
				
			case "play":
				checkArgs(2, true);
				cmd_play();
				break;
			
			case "stop":
				checkArgs(2, true);
				cmd_stop();
				break;
				
			case "skip":
				checkArgs(2, true);
				cmd_skip();
				break;
			
			case "jump":
				checkArgs(3, true);
				cmd_jump(activeCommand[checkInt(2)]);
				break;
				
			case "info":
				checkArgs(3, true);
				cmd_info(activeCommand[checkInt(2)]);
				break;
			
			default: throw new CommandArgumentsException();
		}
	}

	private void cmd_init(String name) throws IOException {
		try {
			activeplayer.init(name);
		} catch (ClassNotFoundException | SQLException e) {
			cli.print(e.getMessage());
			return;
		}
		cli.print("OK");
		return;
	}
	
	private void cmd_play() throws IOException {
		cli.print(activeplayer.play());
		return;
	}
	
	private void cmd_stop() throws IOException {
		cli.print(activeplayer.stop());
		return;
	}
	
	private void cmd_skip() throws IOException {
		cli.print(activeplayer.skip());
		return;
	}
	
	private void cmd_jump(String index) throws IOException {
		cli.print(activeplayer.jump(index));
		return;
	}
	
	private void cmd_info(String length) throws IOException {
		if(length.equalsIgnoreCase("short")) cli.print(activeplayer.infonodb());
		else if (length.equalsIgnoreCase("long")) cli.print(activeplayer.info());
	}
}
