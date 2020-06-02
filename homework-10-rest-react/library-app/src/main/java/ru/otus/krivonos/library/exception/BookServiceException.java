package ru.otus.krivonos.library.exception;

import java.util.List;

public class BookServiceException extends MainException {
	public BookServiceException(List<String> errors) {
		super(errors);
	}

	public BookServiceException(String message) {
		super(message);
	}

	public BookServiceException(String message, Throwable cause) {
		super(message, cause);
	}
}
