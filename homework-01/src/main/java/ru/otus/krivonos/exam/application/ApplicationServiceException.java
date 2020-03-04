package ru.otus.krivonos.exam.application;

public class ApplicationServiceException extends Exception {
	ApplicationServiceException(String message, Throwable cause) {
		super(message, cause);
	}
}
