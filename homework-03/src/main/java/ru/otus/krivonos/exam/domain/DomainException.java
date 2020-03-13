package ru.otus.krivonos.exam.domain;

public class DomainException extends Exception {
	public DomainException(String message) {
		super(message);
	}

	public DomainException(String message, Throwable cause) {
		super(message, cause);
	}

	public String getInfo() {
		return getMessage();
	}
}
