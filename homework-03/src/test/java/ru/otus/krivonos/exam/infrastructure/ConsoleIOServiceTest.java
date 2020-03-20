package ru.otus.krivonos.exam.infrastructure;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import ru.otus.krivonos.exam.domain.IOService;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DisplayName("тестирование сервиса IOService")
class ConsoleIOServiceTest {
	private static final String MESSAGE = "тестовое сообщение";

	private ByteArrayOutputStream bos;
	private ConsoleContext consoleContext;
	private IOService ioService;

	@BeforeEach
	void setUp() {
		bos = new ByteArrayOutputStream();
		consoleContext = new ConsoleContext(new PrintStream(bos), System.in);
		ioService = new ConsoleIOService(consoleContext);
	}

	@Test
	@DisplayName("должно печатать '" + MESSAGE + "'")
	void shouldPrintMessage() throws Exception {
		ioService.printMessage(MESSAGE);
		Thread.sleep(1000);
		assertThat(bos.toString()).isEqualTo(MESSAGE + "\r\n");
	}
}