package ru.otus.krivonos.exam.infrastructure;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import ru.otus.krivonos.exam.domain.MessageRepository;
import ru.otus.krivonos.exam.domain.MessageRepositoryException;
import ru.otus.krivonos.exam.domain.model.Result;

import java.util.Locale;

@Component
public class BundleMessageRepository implements MessageRepository {
	private final MessageSource messageSource;
	private final Locale locale;

	public BundleMessageRepository(MessageSource messageSource, Localization localization) {
		this.messageSource = messageSource;
		this.locale = localization.locale();
	}

	@Override
	public String greetingMessage() throws MessageRepositoryException {
		try {
			return messageSource.getMessage(
				"greeting",
				new String[]{},
				locale
			);
		} catch (Exception e) {
			throw new MessageRepositoryException("Непредвиденная ошибка при получении сообщения с приветствием", e);
		}
	}

	@Override
	public String askingNameMessage() throws MessageRepositoryException {
		try {
			return messageSource.getMessage(
				"ask.name",
				new String[]{},
				locale
			);
		} catch (Exception e) {
			throw new MessageRepositoryException("Непредвиденная ошибка при получении сообщения с просьбой представиться", e);
		}
	}

	@Override
	public String successResultMessage(Result result) throws MessageRepositoryException {
		try {
			return messageSource.getMessage(
				"success.result",
				new String[]{result.username(), String.valueOf(result.userPercentResult())},
				locale
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
				Locale.getDefault()
			);
		} catch (Exception e) {
			throw new MessageRepositoryException("Непредвиденная ошибка при получении сообщения отрицательного результата прохождения тестирования", e);
		}
	}
}
