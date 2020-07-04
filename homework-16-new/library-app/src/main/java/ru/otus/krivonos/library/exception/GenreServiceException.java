package ru.otus.krivonos.library.exception;

public class GenreServiceException extends MainException {
	public GenreServiceException(String clientMessage,String message) {
		super(clientMessage, message);
	}
}