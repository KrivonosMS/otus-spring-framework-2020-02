package ru.otus.krivonos.library.configuration;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.PrintStream;

@NoArgsConstructor
@AllArgsConstructor
@Component
public class ConsoleContext {
	private PrintStream out = System.out;

	public PrintStream out() {
		return out;
	}
}