package ru.otus.krivonos.library.exception;

public class BookDaoException extends MainException {
	public BookDaoException(String message, Throwable cause) {
		super(message, cause);
	}

	public BookDaoException(String message) {
		super(message);
	}
}
