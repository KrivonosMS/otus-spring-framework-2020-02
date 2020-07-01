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

		String msg = messageRepository.greetingMessage("test_username");

		assertEquals("test_username, Вас приветствует программа тестроивания студентов.", msg);
	}

	@Test
	@DisplayName("должен получить англоязычное приветствие")
	void shouldReturnEnglishGreeting() throws Exception {
		when(localization.locale()).thenReturn(new Locale("en", "EN"));

		String msg = messageRepository.greetingMessage("test_username");

		assertEquals("Hello test_username.", msg);
	}

	@Test
	@DisplayName("должен получить рускоязычное сообщение об отстутсвии имени тестируемого")
	void shouldReturnRussianUsernameAbsentMessage() throws Exception {
		when(localization.locale()).thenReturn(new Locale("ru", "RU"));

		String msg = messageRepository.usernameAbsent();

		assertEquals("Вы не указали свое имя.", msg);
	}

	@Test
	@DisplayName("должен получить англоязычное сообщение об отстутсвии имени тестируемого")
	void shouldReturnEnglishUsernameAbsentMessage() throws Exception {
		when(localization.locale()).thenReturn(new Locale("en", "EN"));

		String msg = messageRepository.usernameAbsent();

		assertEquals("You need write your name.", msg);
	}

	@Test
	@DisplayName("должен получить рускоязычное сообщение о неудовлетворительном прохождения тестирования\"")
	void shouldReturnRussiaBadResult() throws Exception {
		when(localization.locale()).thenReturn(new Locale("ru", "RU"));

		String msg = messageRepository.badResultMessage(Result.createInstanceFrom("test_username", 50, 0.4));

		assertEquals("test_username, Вы не прошли тестирование. Вы верно ответили на 40.0% вопрсов.", msg);
	}

	@Test
	@DisplayName("должен получить англоязычное сообщение о неудовлетворительном прохождения тестирования")
	void shouldReturnEnglishBadResult() throws Exception {
		when(localization.locale()).thenReturn(new Locale("en", "EN"));

		String msg = messageRepository.badResultMessage(Result.createInstanceFrom("test_username", 50, 0.4));

		assertEquals("test_username, sorry. You result 40.0% is bad. Try again later.", msg);
	}

	@Test
	@DisplayName("должен получить рускоязычное сообщение о неудовлетворительном прохождения тестирования\"")
	void shouldReturnRussiaSuccessfulResult() throws Exception {
		when(localization.locale()).thenReturn(new Locale("ru", "RU"));

		String msg = messageRepository.successResultMessage(Result.createInstanceFrom("test_username", 50, 0.6));

		assertEquals("test_username, Вы успешно прошли тестирование, ответив на 60.0% вопрсов.", msg);
	}

	@Test
	@DisplayName("должен получить англоязычное сообщение о неудовлетворительном прохождения тестирования")
	void shouldReturnEnglishSuccessfulResult() throws Exception {
		when(localization.locale()).thenReturn(new Locale("en", "EN"));

		String msg = messageRepository.successResultMessage(Result.createInstanceFrom("test_username", 50, 0.6));

		assertEquals("test_username, congratulation! you result 60.0% is successful.", msg);
	}

	@Test
	@DisplayName("должен получить рускоязычное сообщение об ошибке приложения\"")
	void shouldReturnRussiaApplicationErrorMessage() throws Exception {
		when(localization.locale()).thenReturn(new Locale("ru", "RU"));

		String msg = messageRepository.applicationErrorMessage();

		assertEquals("возникла непредвиденная ошибка в работе приложенияя...", msg);
	}

	@Test
	@DisplayName("должен получить англоязычное сообщение об ошибке приложения")
	void shouldReturnEnglishApplicationErrorMessage() throws Exception {
		when(localization.locale()).thenReturn(new Locale("en", "EN"));

		String msg = messageRepository.applicationErrorMessage();

		assertEquals("unexpected error occurred...", msg);
	}

	@Configuration
	@EnableAutoConfiguration
	@ComponentScan({"ru.otus.krivonos.exam.infrastructure", "ru.otus.krivonos.exam.config"})
	public static class TestContextConfig {
	}
}