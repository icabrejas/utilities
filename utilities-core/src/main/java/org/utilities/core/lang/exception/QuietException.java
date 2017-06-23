package org.utilities.core.lang.exception;

public class QuietException extends RuntimeException {

	private static final long serialVersionUID = 4323468795433961042L;

	public QuietException(String message, Throwable cause) {
		super(message, cause);
	}

	public QuietException(Throwable cause) {
		super(cause);
	}

}
