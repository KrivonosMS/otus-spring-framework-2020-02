package ru.otus.krivonos.exam.infrastructure;

import org.springframework.stereotype.Service;
import ru.otus.krivonos.exam.domain.IOService;
import ru.otus.krivonos.exam.domain.IOServiceException;

import java.io.PrintStream;
import java.util.Scanner;

@Service
public class ConsoleIOService implements IOService {
	private final PrintStream out;
	private final Scanner sc;

	public ConsoleIOService(ConsoleContext consoleContext) {
		this.out = consoleContext.out();
		this.sc = new Scanner(consoleContext.in());
	}

	@Override
	public void printMessage(String message) throws IOServiceException {
		try {
			out.println(message);
		} catch (Exception e) {
			throw new IOServiceException("Возникла непредвиденная ошибка во время вывода информации");
		}
	}

	@Override
	public String readMessage() throws IOServiceException {
		try {
			return sc.nextLine();
		} catch (Exception e) {
			throw new IOServiceException("Возникла непредвиденная ошибка во время считывания информации");
		}
	}
}
