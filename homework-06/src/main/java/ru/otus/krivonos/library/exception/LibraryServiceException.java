package ru.otus.krivonos.library.exception;

import java.util.List;

public class LibraryServiceException extends MainException {
	public LibraryServiceException(List<String> errors) {
		super(errors);
	}

	public LibraryServiceException(String message) {
		super(message);
	}

	public LibraryServiceException(String message, Throwable cause) {
		super(message, cause);
	}
}
