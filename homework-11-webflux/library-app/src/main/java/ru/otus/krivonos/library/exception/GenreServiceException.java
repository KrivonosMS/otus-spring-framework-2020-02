package ru.otus.krivonos.library.exception;

public class GenreServiceException extends MainException {
	public GenreServiceException(String message) {
		super(message);
	}

	public GenreServiceException(String message, Throwable cause) {
		super(message, cause);
	}
}
