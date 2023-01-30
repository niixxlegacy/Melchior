package fr.niixx.melchior.cli.commands;

import java.util.Arrays;

import fr.niixx.melchior.Registry;
import fr.niixx.melchior.cli.Cli;
import fr.niixx.melchior.cli.errors.CommandArgumentsException;

public class Inserts extends DefaultCommand implements Registry {	
	public Inserts(Cli cli) throws Exception {
		super(cli);
	}

	void cmds() throws Exception {
		switch(activeCommand[checkInt(1)]) {
			case "init":
				checkArgs(2, true);
				cmd_init();
				break;
				
			case "create":
				checkArgs(4, false);
				cmd_create(activeCommand);
				break;
								
			default: throw new CommandArgumentsException();
		}
	}
	
	private void cmd_init() throws Exception {
		overlays.init();
		cli.print("OK");
	}
	
	//inserts create <template> <duration> <layer> <content content content>
	private void cmd_create(String[] command) throws Exception {
		String args = Arrays.copyOfRange(command, checkInt(5), command.length).toString();
		cli.print(overlays.request(command[checkInt(2)], args.split("\\/\\$\\/"), Integer.valueOf(command[checkInt(3)]), Integer.valueOf(command[checkInt(4)])));
	}
	
}