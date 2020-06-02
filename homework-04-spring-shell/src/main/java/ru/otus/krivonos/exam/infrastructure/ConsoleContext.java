package ru.otus.krivonos.exam.infrastructure;

import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.PrintStream;

@Component
public class ConsoleContext {
	private PrintStream out = System.out;
	private InputStream in = System.in;

	public ConsoleContext() {
	}

	public ConsoleContext(PrintStream out, InputStream in) {
		this.out = out;
		this.in = in;
	}

	public PrintStream out() {
		return out;
	}

	public InputStream in() {
		return in;
	}
}
