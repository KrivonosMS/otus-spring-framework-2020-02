package ru.otus.krivonos.exam.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.krivonos.exam.domain.model.*;

import java.util.HashMap;
import java.util.Map;

public class ExamServiceImpl implements ExamService {
	public static final Logger LOG = LoggerFactory.getLogger(ExamServiceImpl.class);

	private final ExamRepository examRepository;
	private final MessageRepository messageRepository;
	private final IOService ioService;
	private final double successThresholdPercent;

	public ExamServiceImpl(ExamRepository examRepository, MessageRepository messageRepository, IOService ioService, double successThresholdPercent) {
		this.examRepository = examRepository;
		this.messageRepository = messageRepository;
		this.ioService = ioService;
		this.successThresholdPercent = successThresholdPercent;
	}

	@Override
	public void startExam(String username) throws ExamServiceException {
		if (username == null || "".equals(username)) {
			throw new ExamServiceException("Не задано имя экзаминуемого");
		}
		try {
			LOG.debug("method=startExam action=\"начало экзамена\"");

			CheckList checkList = examRepository.obtainTest();

			LOG.debug("method=startExam action=\"получены вопросы из репозитория\" checkList={}", checkList);

			ioService.printMessage(messageRepository.greetingMessage(username));

			Map<QuestionNumber, Question> questions = checkList.unmodifiableQuestions();
			Map<QuestionNumber, String> answers = new HashMap<>();
			for (QuestionNumber questionNumber : questions.keySet()) {
				ioService.printMessage(questions.get(questionNumber).question());
				answers.put(questionNumber, ioService.readMessage());
			}
			PersonAnswers personAnswers = PersonAnswers.createInstanceFrom(answers, username);
			Result result = checkList.calculateResult(personAnswers, successThresholdPercent);
			if (result.isSuccess()) {
				ioService.printMessage(messageRepository.successResultMessage(result));
			} else {
				ioService.printMessage(messageRepository.badResultMessage(result));
			}

			LOG.debug("method=startExam action=\"завершено тестирование\" result={}", result);
		} catch (Exception e) {
			throw new ExamServiceException("Возникла непредвиденная ошибка", e);
		}
	}
}
