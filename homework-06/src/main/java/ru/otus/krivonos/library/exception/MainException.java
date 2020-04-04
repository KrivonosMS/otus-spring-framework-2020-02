package ru.otus.krivonos.library.exception;

public class MainException extends Exception
{
	public MainException() {
	}

	public MainException(String message) {
		super(message);
	}

	public MainException(String message, Throwable cause) {
		super(message, cause);
	}

	public MainException(Throwable cause) {
		super(cause);
	}
}
