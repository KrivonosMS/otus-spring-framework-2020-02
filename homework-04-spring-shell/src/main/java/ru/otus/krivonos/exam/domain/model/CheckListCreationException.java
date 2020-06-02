package ru.otus.krivonos.exam.domain.model;

public class CheckListCreationException extends Exception {
	CheckListCreationException(String message) {
		super(message);
	}

	CheckListCreationException(String message, Throwable cause) {
		super(message, cause);
	}
}
