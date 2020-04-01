package ru.otus.krivonos.library.service;

import org.springframework.stereotype.Service;
import ru.otus.krivonos.library.configuration.ConsoleContext;

import java.io.PrintStream;

@Service
public class ConsoleOutputService implements OutputService {
	private final PrintStream out;

	public ConsoleOutputService(ConsoleContext consoleContext) {
		this.out = consoleContext.out();
	}

	@Override
	public void printText(String message) throws OutputServiceException {
		try {
			out.println(message);
		} catch (Exception e) {
			throw new OutputServiceException("Возникла непредвиденная ошибка во время вывода информации");
		}
	}
}