package ru.otus.krivonos.library.exception;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainException extends Exception {
	private final List<String> errors;

	public MainException(List<String> errors) {
		super();
		this.errors = errors;
	}

	public MainException() {
		this.errors = new ArrayList<>();
	}

	public MainException(String message) {
		super(message);
		this.errors = Arrays.asList(message);
	}

	public MainException(String message, Throwable cause) {
		super(message, cause);
		this.errors = Arrays.asList(message);
	}

	public MainException(Throwable cause) {
		super(cause);
		this.errors = new ArrayList<>();
	}

	@Override
	public String getMessage() {
		return getInfo();
	}

	public String getInfo() {
		return String.join("; ", errors);
	}
}
