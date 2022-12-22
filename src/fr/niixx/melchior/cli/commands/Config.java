package fr.niixx.melchior.cli.commands;

import java.io.IOException;

import fr.niixx.melchior.Registry;
import fr.niixx.melchior.cli.Cli;
import fr.niixx.melchior.cli.errors.CommandArgumentsException;

public class Config extends DefaultCommand implements Registry {	
	public Config(Cli cli) throws Exception {
		super(cli);
	}

	void cmds() throws Exception {
		switch(activeCommand[checkInt(1)]) {
			case "set":
				checkArgs(4, true);
				cmd_set(activeCommand[checkInt(2)], activeCommand[checkInt(3)]);
				break;
				
			case "get":
				checkArgs(3, true);
				cmd_get(activeCommand[checkInt(2)]);
				break;
								
			default: throw new CommandArgumentsException();
		}
	}

	private void cmd_get(String key) throws IOException {
		String out = config.get(key);
		if(out == null) out = "Key not set";
		cli.print(out);
	}
	
	private void cmd_set(String key, String value) throws IOException {
		config.put(key, value);
		cli.print("OK");
	}

}
