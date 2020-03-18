package ru.otus.krivonos.exam.config;

import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.PrintStream;

@Component
public class ConsoleContext {
	private final PrintStream out = System.out;
	private final InputStream in = System.in;

	public PrintStream out() {
		return out;
	}

	public InputStream in() {
		return in;
	}
}
