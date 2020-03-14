package ru.otus.krivonos.exam.domain.model;

import ru.otus.krivonos.exam.domain.DomainException;

public class ResultCreationException extends DomainException {
	ResultCreationException(String message) {
		super(message);
	}
}
