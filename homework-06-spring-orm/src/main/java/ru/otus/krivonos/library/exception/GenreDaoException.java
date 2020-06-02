package ru.otus.krivonos.library.exception;

public class GenreDaoException extends MainException {
	public GenreDaoException(String message) {
		super(message);
	}

	public GenreDaoException(String message, Throwable cause) {
		super(message, cause);
	}
}
