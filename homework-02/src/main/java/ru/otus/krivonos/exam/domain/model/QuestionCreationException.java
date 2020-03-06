package ru.otus.krivonos.exam.domain.model;

import ru.otus.krivonos.exam.domain.DomainException;

class QuestionCreationException extends DomainException {
	QuestionCreationException(String message) {
		super(message);
	}
}
