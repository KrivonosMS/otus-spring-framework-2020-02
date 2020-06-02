package ru.otus.krivonos.library.service;

public class LibraryServiceException extends Exception {
	LibraryServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	LibraryServiceException(String message) {
		super(message);
	}
}
