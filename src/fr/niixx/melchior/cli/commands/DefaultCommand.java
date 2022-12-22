package fr.niixx.melchior.cli.commands;

import java.io.IOException;

import fr.niixx.melchior.Registry;
import fr.niixx.melchior.cli.Cli;
import fr.niixx.melchior.cli.errors.CommandArgumentsException;

public class DefaultCommand implements Registry {
	
	Cli cli;
	Boolean isInteractive = false;
	String[] activeCommand;

	public DefaultCommand(Cli cli) throws Exception {
		this.cli = cli;
		activeCommand = cli.currentCommand;
				
		checkArgs(2, false);
		if(activeCommand[1].equals("cli")) {
			isInteractive = true;
			interactive();
		} else 
			cmds();
		
	}
	
	void cmds() throws Exception {
		switch(activeCommand[checkInt(1)]) {
			default: throw new CommandArgumentsException();
		}
	}
	
	void interactive() throws IOException {
		while(true) {
			activeCommand = cli.prompt();
			if(activeCommand[0].equals("exit")) {
				cli.print("Back to main CLI");
				return;
			}
			
			try {
				cmds();
			} catch(Exception e) {
				cli.print("\u001B[31m" + e.getMessage() + "\u001B[0m");
			}
		}
	}
	
	int checkInt(int input) {
		if(isInteractive) return input - 1;
		else return input;
	}
	
	void checkArgs(int requiredLength, Boolean Strict) throws CommandArgumentsException {
		if(Strict) {
			if(activeCommand.length != checkInt(requiredLength)) throw new CommandArgumentsException();
		} else
			if(activeCommand.length < checkInt(requiredLength)) throw new CommandArgumentsException();
	}
}