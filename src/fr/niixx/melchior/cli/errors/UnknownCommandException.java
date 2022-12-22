package fr.niixx.melchior.cli.errors;

public class UnknownCommandException extends Exception {
	private static final long serialVersionUID = 6858507523415836507L;
	
	public UnknownCommandException() {
		super("Unknown Command");
	}
	
	public UnknownCommandException(Throwable err) {
		super("Unknown Command", err);
	}
	
	public UnknownCommandException(String errorMessage) {
		super(errorMessage);
	}
	
	public UnknownCommandException(String errorMessage, Throwable err) {
	    super(errorMessage, err);
	}
}
