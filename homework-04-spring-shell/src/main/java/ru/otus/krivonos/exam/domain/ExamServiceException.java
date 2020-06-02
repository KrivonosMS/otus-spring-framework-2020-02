package ru.otus.krivonos.exam.domain;

public class ExamServiceException extends Exception {
	ExamServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	ExamServiceException(String message) {
		super(message);
	}
}
