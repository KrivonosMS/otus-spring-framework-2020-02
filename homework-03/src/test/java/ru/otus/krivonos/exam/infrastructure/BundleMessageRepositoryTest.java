package ru.otus.krivonos.exam.infrastructure;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.otus.krivonos.exam.domain.MessageRepository;
import ru.otus.krivonos.exam.domain.model.Result;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@DisplayName("тестирование репозитория диалоговых сообщений")
class BundleMessageRepositoryTest {
	@MockBean
	private Localization localization;

	@Autowired
	private MessageRepository messageRepository;

	@Test
	@DisplayName("должен получить русскоязычное приветствие")
	void shouldReturnRussiaGreeting() throws Exception {
		when(localization.locale()).thenReturn(new Locale("ru", "RU"));

		String greeting = messageRepository.greetingMessage();

		assertEquals("Вас приветствует программа тестроивания студентов.", greeting);
	}

	@Test
	@DisplayName("должен получить англоязычное приветствие")
	void shouldReturnEnglishGreeting() throws Exception {
		when(localization.locale()).thenReturn(new Locale("en", "EN"));

		String greeting = messageRepository.greetingMessage();

		assertEquals("Hello dear student.", greeting);
	}

	@Test
	@DisplayName("должен получить рускоязычное предложение представиться")
	void shouldReturnRussiaAskingName() throws Exception {
		when(localization.locale()).thenReturn(new Locale("ru", "RU"));

		String greeting = messageRepository.askingNameMessage();

		assertEquals("Пожалуйста, введите свое имя:", greeting);
	}

	@Test
	@DisplayName("должен получить англоязычное предложение представиться")
	void shouldReturnEnglishAskingName() throws Exception {
		when(localization.locale()).thenReturn(new Locale("en", "EN"));

		String greeting = messageRepository.askingNameMessage();

		assertEquals("Please write your name:", greeting);
	}

	@Test
	@DisplayName("должен получить рускоязычное сообщение о неудовлетворительном прохождения тестирования\"")
	void shouldReturnRussiaBadResult() throws Exception {
		when(localization.locale()).thenReturn(new Locale("ru", "RU"));

		String greeting = messageRepository.badResultMessage(Result.createInstanceFrom("test_username", 50, 0.4));

		assertEquals("test_username, Вы не прошли тестирование. Вы верно ответили на 40.0% вопрсов.", greeting);
	}

	@Test
	@DisplayName("должен получить англоязычное сообщение о неудовлетворительном прохождения тестирования")
	void shouldReturnEnglishBadResult() throws Exception {
		when(localization.locale()).thenReturn(new Locale("en", "EN"));

		String greeting = messageRepository.badResultMessage(Result.createInstanceFrom("test_username", 50, 0.4));

		assertEquals("test_username, sorry. You result 40.0% is bad. Try again later.", greeting);
	}

	@Test
	@DisplayName("должен получить рускоязычное сообщение о неудовлетворительном прохождения тестирования\"")
	void shouldReturnRussiaSuccessfulResult() throws Exception {
		when(localization.locale()).thenReturn(new Locale("ru", "RU"));

		String greeting = messageRepository.successResultMessage(Result.createInstanceFrom("test_username", 50, 0.6));

		assertEquals("test_username, Вы успешно прошли тестирование, ответив на 60.0% вопрсов.", greeting);
	}

	@Test
	@DisplayName("должен получить англоязычное сообщение о неудовлетворительном прохождения тестирования")
	void shouldReturnEnglishSuccessfulResult() throws Exception {
		when(localization.locale()).thenReturn(new Locale("en", "EN"));

		String greeting = messageRepository.successResultMessage(Result.createInstanceFrom("test_username", 50, 0.6));

		assertEquals("test_username, congratulation! you result 60.0% is successful.", greeting);
	}

	@Configuration
	@EnableAutoConfiguration
	@ComponentScan({"ru.otus.krivonos.exam.infrastructure", "ru.otus.krivonos.exam.config"})
	public static class TestContextConfig {
	}
}