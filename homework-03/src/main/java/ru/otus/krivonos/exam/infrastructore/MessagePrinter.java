package ru.otus.krivonos.exam.infrastructore;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.krivonos.exam.domain.model.Result;

import java.util.Locale;

@Service
public class MessagePrinter {
	private final MessageSource messageSource;

	public MessagePrinter(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	public void printGreeting() {
		System.out.println("================================================================================");
		System.out.println(messageSource.getMessage("greeting", new String[]{}, Locale.getDefault()));
		System.out.println("================================================================================");
	}

	public void printAskName() {
		System.out.println(messageSource.getMessage("ask.name", new String[]{}, Locale.getDefault()));
	}

	public void printSuccessResult(Result result) {
		System.out.println(
			messageSource.getMessage("success.result",
				new String[]{result.username(), String.valueOf(result.userPercentResult())},
				Locale.getDefault())
		);
	}

	public void printBadResult(Result result) {
		System.out.println(messageSource.getMessage("bad.result",
			new String[]{result.username(), String.valueOf(result.userPercentResult())},
			Locale.getDefault())
		);
	}
}
