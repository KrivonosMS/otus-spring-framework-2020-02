package ru.otus.krivonos.exam.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.otus.krivonos.exam.config.ApplicationProperties;
import ru.otus.krivonos.exam.domain.*;
import ru.otus.krivonos.exam.domain.model.*;
import ru.otus.krivonos.exam.infrastructore.MessagePrinter;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Service
public class ApplicationService {
	public static final Logger LOG = LoggerFactory.getLogger(ApplicationService.class);

	private final ExamRepository repository;
	private final IOService ioService;
	private final MessagePrinter messagePrinter;
	private final ApplicationProperties applicationProperties;

	public ApplicationService(ExamRepository repository, IOService ioService, MessagePrinter messagePrinter, ApplicationProperties applicationProperties) {
		this.repository = repository;
		this.ioService = ioService;
		this.messagePrinter = messagePrinter;
		this.applicationProperties = applicationProperties;
	}

	public void startTest() throws ApplicationServiceException {
		try {
			LOG.debug("method=startTest action=\"проведение тестирование\"");

			setLocale(applicationProperties.getLocalization());
			ExamServiceImpl service = new ExamServiceImpl(repository, ioService);
			messagePrinter.printGreeting();
			messagePrinter.printAskName();
			String username = ioService.readMessage();
			Result result = service.startExam(username);
			if (result.isSuccess()) {
				messagePrinter.printSuccessResult(result);
			} else {
				messagePrinter.printBadResult(result);
			}

			LOG.debug("method=startTest action=\"завершение проведение тестирование\" username={} result={}", username, result);
		} catch (ExamServiceException | IOServiceException e) {
			throw new ApplicationServiceException("Возникла непредвиденная ошика во время проведения тестирования", e);
		}
	}

	private static void setLocale(String locale) {
		List<String> localeList = Arrays.asList(locale.split("_"));
		if(!localeList.isEmpty() && localeList.size() > 1) {
			Locale.setDefault(new Locale(localeList.get(0), localeList.get(1)));
		}
	}
}
