package ru.otus.krivonos.exam.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.otus.krivonos.exam.domain.DomainException;
import ru.otus.krivonos.exam.domain.TestRepository;
import ru.otus.krivonos.exam.domain.model.*;
import ru.otus.krivonos.exam.infrastructore.MessagePrinter;
import ru.otus.krivonos.exam.infrastructore.ScanReader;

import java.util.HashMap;
import java.util.Map;

@Service
public class ApplicationService {
	public static final Logger LOG = LoggerFactory.getLogger(ApplicationService.class);

	private final TestRepository repository;
	private final ScanReader scanReader;
	private final MessagePrinter messagePrinter;

	public ApplicationService(TestRepository repository, ScanReader scanReader, MessagePrinter messagePrinter) {
		this.repository = repository;
		this.scanReader = scanReader;
		this.messagePrinter = messagePrinter;
	}

	public void startTest(String type) throws ApplicationServiceException {
		try {
			LOG.debug("method=startTest action=\"проведение тестирование\" type={}", type);

			Localization localizationType = localization(type);
			CheckList checkList = repository.obtainTest(localizationType);
			messagePrinter.printGreeting(localizationType);
			messagePrinter.printAskName(localizationType);
			String username = scanReader.nextLine();
			Map<QuestionNumber, Question> questions = checkList.unmodifiableQuestions();
			Map<QuestionNumber, String> answers = new HashMap<>();
			for (QuestionNumber questionNumber : questions.keySet()) {
				System.out.println(questions.get(questionNumber).question());
				answers.put(questionNumber, scanReader.nextLine());
			}
			PersonAnswers personAnswers = PersonAnswers.createInstanceFrom(answers, username);
			Result result = checkList.calculateResult(personAnswers);
			if(result.isSuccess()) {
				messagePrinter.printSuccessResult(localizationType, result);
			} else {
				messagePrinter.printBadResult(localizationType, result);
			}

			LOG.debug("method=startTest action=\"завершение проведение тестирование\" username={} result={}", username, result);
		} catch (DomainException e) {
			throw new ApplicationServiceException(e.getInfo(),e);
		} finally {
			scanReader.close();
		}
	}

	private Localization localization(String arg) {
		String locale = arg;
		Localization localizationType;
		switch (locale) {
			case("ru"): {
				localizationType = Localization.RU;
				break;
			}
			case("en"): {
				localizationType = Localization.EN;
				break;
			}
			default: {
				localizationType =Localization.RU;
			}
		}
		return localizationType;
	}
}
