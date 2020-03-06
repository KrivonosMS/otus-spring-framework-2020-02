package ru.otus.krivonos.exam.domain.model;

import ru.otus.krivonos.exam.domain.DomainException;

public class PersonAnswersCreationException extends DomainException {
	PersonAnswersCreationException(String message) {
		super(message);
	}
}
