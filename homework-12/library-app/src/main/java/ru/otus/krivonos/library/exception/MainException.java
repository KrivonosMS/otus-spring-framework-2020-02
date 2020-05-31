package ru.otus.krivonos.library.exception;

public class MainException extends RuntimeException {
	private final String clientMessage;

	public MainException(String clientMessage, String message) {
		super(message);
		this.clientMessage = clientMessage;
	}

	public MainException(String clientMessage, String message, Throwable cause) {
		super(message, cause);
		this.clientMessage = clientMessage;
	}

	public String getClientMessage() {
		return clientMessage;
	}
}