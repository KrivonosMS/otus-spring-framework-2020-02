package ru.otus.krivonos.exam.domain.model;

import ru.otus.krivonos.exam.domain.DomainException;

public class AnswerCreationException extends DomainException {
	AnswerCreationException(String message) {
		super(message);
	}
}
