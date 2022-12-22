package fr.niixx.melchior.cli.errors;

public class CommandArgumentsException extends Exception {
	private static final long serialVersionUID = -1612443909829177821L;

	public CommandArgumentsException() {
		super("Invalid Arguments for this command");
	}
	
	public CommandArgumentsException(Throwable err) {
		super("Invalid Arguments for this command", err);
	}
	
	public CommandArgumentsException(String errorMessage) {
		super(errorMessage);
	}
	
	public CommandArgumentsException(String errorMessage, Throwable err) {
	    super(errorMessage, err);
	}
}
