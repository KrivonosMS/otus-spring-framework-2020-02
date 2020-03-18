package ru.otus.krivonos.exam.domain;

import ru.otus.krivonos.exam.domain.model.*;

import java.util.HashMap;
import java.util.Map;

public class ExamServiceImpl implements ExamService{
	private final ExamRepository examRepository;
	private final IOService ioService;

	public ExamServiceImpl(ExamRepository examRepository, IOService ioService) {
		this.examRepository = examRepository;
		this.ioService = ioService;
	}

	@Override
	public Result startExam(String username) throws ExamServiceException {
		try {
			CheckList checkList = examRepository.obtainTest();
			Map<QuestionNumber, Question> questions = checkList.unmodifiableQuestions();
			Map<QuestionNumber, String> answers = new HashMap<>();
			for (QuestionNumber questionNumber : questions.keySet()) {
				ioService.printMessage(questions.get(questionNumber).question());
				answers.put(questionNumber, ioService.readMessage());
			}
			PersonAnswers personAnswers = PersonAnswers.createInstanceFrom(answers, username);
			return checkList.calculateResult(personAnswers);
		} catch (Exception e) {
			throw new ExamServiceException("Возникла непредвиденная ошибка", e);
		}
	}
}
