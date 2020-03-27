package ru.otus.krivonos.exam.infrastructure;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import ru.otus.krivonos.exam.domain.MessageRepository;
import ru.otus.krivonos.exam.domain.MessageRepositoryException;
import ru.otus.krivonos.exam.domain.model.Result;

@Component
public class BundleMessageRepository implements MessageRepository {
	private final MessageSource messageSource;
	private final Localization localization;

	public BundleMessageRepository(MessageSource messageSource, Localization localization) {
		this.messageSource = messageSource;
		this.localization = localization;
	}

	@Override
	public String greetingMessage(String username) throws MessageRepositoryException {
		try {
			return messageSource.getMessage(
				"greeting",
				new String[]{username},
				localization.locale()
			);
		} catch (Exception e) {
			throw new MessageRepositoryException("Непредвиденная ошибка при получении сообщения с приветствием", e);
		}
	}

	@Override
	public String usernameAbsent() throws MessageRepositoryException {
		try {
			return messageSource.getMessage(
				"username.absent",
				new String[]{},
				localization.locale()
			);
		} catch (Exception e) {
			throw new MessageRepositoryException("Непредвиденная ошибка при получении информационного сообщения об отстутсвии имени пользователя", e);
		}
	}

	@Override
	public String successResultMessage(Result result) throws MessageRepositoryException {
		try {
			return messageSource.getMessage(
				"success.result",
				new String[]{result.username(), String.valueOf(result.userPercentResult())},
				localization.locale()
			);
		} catch (Exception e) {
			throw new MessageRepositoryException("Непредвиденная ошибка при получении сообщения положительного результата прохождения тестирования", e);
		}
	}

	@Override
	public String badResultMessage(Result result) throws MessageRepositoryException {
		try {
			return messageSource.getMessage(
				"bad.result",
				new String[]{result.username(), String.valueOf(result.userPercentResult())},
				localization.locale()
			);
		} catch (Exception e) {
			throw new MessageRepositoryException("Непредвиденная ошибка при получении сообщения отрицательного результата прохождения тестирования", e);
		}
	}

	@Override
	public String applicationErrorMessage() throws MessageRepositoryException {
		try {
			return messageSource.getMessage(
				"application.error",
				new String[]{},
				localization.locale()
			);
		} catch (Exception e) {
			throw new MessageRepositoryException("Непредвиденная ошибка при получении сообщения об ошибке приложения", e);
		}
	}
}
