package de.fiduciagad.sharea.server.data.exceptions;

public class ModificationException extends Exception {

	private static final long serialVersionUID = 1L;

	public ModificationException(String message) {
		super(message);
	}

	public ModificationException(String message, Throwable cause) {
		super(message, cause);
	}

	public ModificationException(Throwable cause) {
		super(cause);
	}

}
