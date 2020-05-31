package ru.otus.krivonos.library.exception;

public class BookServiceException extends MainException {
	public BookServiceException(String clientMessage, String message) {
		super(clientMessage, message);
	}
}