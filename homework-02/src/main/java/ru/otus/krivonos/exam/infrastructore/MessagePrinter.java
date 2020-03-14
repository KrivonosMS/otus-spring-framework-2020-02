package ru.otus.krivonos.exam.infrastructore;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.krivonos.exam.domain.model.Localization;
import ru.otus.krivonos.exam.domain.model.Result;

import java.util.Locale;

@Service
public class MessagePrinter {
	private final MessageSource messageSource;

	public MessagePrinter(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	public void printGreeting(Localization localization) {
		System.out.println("================================================================================");
		System.out.println(messageSource.getMessage("greeting", new String[]{}, localization.locale()));
		System.out.println("================================================================================");
	}

	public void printAskName(Localization localization) {
		System.out.println(messageSource.getMessage("ask.name", new String[]{}, localization.locale()));
	}

	public void printSuccessResult(Localization localization, Result result) {
		System.out.println(
			messageSource.getMessage("success.result",
				new String[]{result.username(), String.valueOf(result.userPercentResult())},
				localization.locale())
		);
	}

	public void printBadResult(Localization localization, Result result) {
		System.out.println(messageSource.getMessage("bad.result",
			new String[]{result.username(), String.valueOf(result.userPercentResult())},
			localization.locale())
		);
	}
}
