package ru.otus.krivonos.library.exception;

import java.util.List;

public class NotValidParameterDataException extends MainException {
	private final List<String> errors;

	public NotValidParameterDataException(List<String> errors) {
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
