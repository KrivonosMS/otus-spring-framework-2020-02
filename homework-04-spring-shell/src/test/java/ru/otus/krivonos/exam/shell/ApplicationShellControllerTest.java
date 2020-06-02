package ru.otus.krivonos.exam.shell;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.shell.CommandNotCurrentlyAvailable;
import org.springframework.shell.Shell;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.krivonos.exam.application.ApplicationService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@DisplayName("Тест команд shell")
class ApplicationShellControllerTest {
	@Autowired
	private Shell shell;

	@MockBean
	private ApplicationService applicationService;

	@DisplayName("должен возвращать приветствие для команды логина")
	@Test
	void shouldReturnExpectedGreetingAfterLoginCommandEvaluated() {
		String res = (String) shell.evaluate(() -> "login Mihail");

		assertThat(res).isEqualTo("Mihail, Вас приветствует программа тестроивания студентов.");
	}

	@DisplayName("должен возвращать CommandNotCurrentlyAvailable с сообщением ошибки для команды start exam, если не был выполнен вход")
	@DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
	@Test
	void shouldReturnCommandNotCurrentlyAvailableObjectAfterStartExamCommandEvaluated() {
		CommandNotCurrentlyAvailable res = (CommandNotCurrentlyAvailable) shell.evaluate(() -> "start exam");

		assertThat(res).isInstanceOf(CommandNotCurrentlyAvailable.class);
		assertThat(res.getAvailability().getReason()).isEqualTo("Вы не указали свое имя.");
		assertFalse(res.getAvailability().isAvailable());
	}

	@DisplayName("должен вызвать соответствующий метод сервиса если икоманда выполнена после входа")
	@DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
	@Test
	void shouldReturnExpectedMessageAndFirePublishMethodAfterPublishCommandEvaluated() throws Exception {
		shell.evaluate(() -> "login Mihail");

		String res = (String) shell.evaluate(() -> "start");

		assertThat(res).isEqualTo("");
		verify(applicationService, times(1)).startTest("Mihail");
	}
}