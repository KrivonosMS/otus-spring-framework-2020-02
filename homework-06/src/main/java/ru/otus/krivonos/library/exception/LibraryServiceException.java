package ru.otus.krivonos.library.exception;

public class LibraryServiceException extends MainException {
	public LibraryServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public LibraryServiceException(String message) {
		super(message);
	}
}
