package ru.otus.krivonos.library.dao;

public class LibraryDaoException extends Exception {
	LibraryDaoException(String message, Throwable cause) {
		super(message, cause);
	}

	LibraryDaoException(String message) {
		super(message);
	}
}
