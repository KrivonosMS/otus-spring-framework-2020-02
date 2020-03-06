package ru.otus.krivonos.exam.domain.model;

import ru.otus.krivonos.exam.domain.DomainException;

public class CheckListCreationException extends DomainException {
	CheckListCreationException(String message) {
		super(message);
	}

	CheckListCreationException(String message, Throwable cause) {
		super(message, cause);
	}
}
