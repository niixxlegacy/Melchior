package fr.niixx.melchior.cli.errors;

public class CommandExecutionException extends Exception {
	private static final long serialVersionUID = -2476812622372120858L;

	public CommandExecutionException() {
		super("Invalid Arguments for this command");
	}
	
	public CommandExecutionException(Throwable err) {
		super("Invalid Arguments for this command", err);
	}
	
	public CommandExecutionException(String errorMessage) {
		super(errorMessage);
	}
	
	public CommandExecutionException(String errorMessage, Throwable err) {
	    super(errorMessage, err);
	}
}
