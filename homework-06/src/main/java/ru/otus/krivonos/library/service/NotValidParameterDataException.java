package ru.otus.krivonos.library.service;

import java.util.List;

public class NotValidParameterDataException extends Exception {
	private final List<String> errors;

	NotValidParameterDataException(List<String> errors) {
		super();
		this.errors = errors;
	}

	@Override
	public String getMessage() {
		return getInfo();
	}

	public String getInfo() {
		return String.join("; ", errors);
	}
}
