package ru.otus.krivonos.exam.domain;

public class TestRepositoryException extends Exception {
	public TestRepositoryException(String message) {
		super(message);
	}

	public TestRepositoryException(String message, Throwable cause) {
		super(message, cause);
	}
}
