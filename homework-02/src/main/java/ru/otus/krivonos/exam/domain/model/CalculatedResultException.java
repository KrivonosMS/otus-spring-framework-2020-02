package ru.otus.krivonos.exam.domain.model;

import ru.otus.krivonos.exam.domain.DomainException;

public class CalculatedResultException extends DomainException {
	CalculatedResultException(String message) {
		super(message);
	}
}
