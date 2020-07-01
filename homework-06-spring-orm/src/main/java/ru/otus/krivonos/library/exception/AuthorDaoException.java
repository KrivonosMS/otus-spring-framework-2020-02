package ru.otus.krivonos.library.exception;

import ru.otus.krivonos.library.exception.MainException;

public class AuthorDaoException extends MainException {
	public AuthorDaoException(String message, Throwable cause) {
		super(message, cause);
	}
}
