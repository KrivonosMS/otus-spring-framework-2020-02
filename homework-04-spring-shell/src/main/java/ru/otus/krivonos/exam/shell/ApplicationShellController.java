package ru.otus.krivonos.exam.shell;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import ru.otus.krivonos.exam.application.ApplicationService;
import ru.otus.krivonos.exam.application.ApplicationServiceException;
import ru.otus.krivonos.exam.domain.MessageRepository;
import ru.otus.krivonos.exam.domain.MessageRepositoryException;

@ShellComponent
@RequiredArgsConstructor
public class ApplicationShellController {
	public static final Logger LOG = LoggerFactory.getLogger(ApplicationShellController.class);

	private String username;
	private final ApplicationService applicationService;
	private final MessageRepository messageRepository;

	@ShellMethod(value = "Login command", key = {"login"})
	public String login(String username) throws MessageRepositoryException {
		this.username = username;
		return messageRepository.greetingMessage(username);
	}

	@ShellMethod(value = "Publish event command", key = {"exam", "start", "start exam"})
	@ShellMethodAvailability(value = "isSuccessLogin")
	public String startExam() throws MessageRepositoryException {
		String msg = "";
		try {
			applicationService.startTest(username);
		} catch (ApplicationServiceException e) {
			LOG.error("Возникла ошибка в работе приложения", e);
			msg = messageRepository.applicationErrorMessage();
		} catch (Exception e) {
			LOG.error("Возникла непредвиденная ошибка", e);
			msg = messageRepository.applicationErrorMessage();
		}
		return msg;
	}

	private Availability isSuccessLogin() throws MessageRepositoryException {
		return (username == null || "".equals(username)) ? Availability.unavailable(messageRepository.usernameAbsent()) : Availability.available();
	}
}