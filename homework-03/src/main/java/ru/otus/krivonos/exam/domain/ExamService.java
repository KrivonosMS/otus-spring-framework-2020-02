package ru.otus.krivonos.exam.domain;

import ru.otus.krivonos.exam.domain.model.*;
import ru.otus.krivonos.exam.infrastructore.ScanReader;

import java.util.HashMap;
import java.util.Map;

public class ExamService {
	private final ExamRepository examRepository;
	private final ScanReader scanReader;

	public ExamService(ExamRepository examRepository, ScanReader scanReader) {
		this.examRepository = examRepository;
		this.scanReader = scanReader;
	}

	public Result startExam(String username) throws PersonAnswersCreationException, CalculatedResultException, ResultCreationException, ExamRepositoryException {
		CheckList checkList = examRepository.obtainTest();
		Map<QuestionNumber, Question> questions = checkList.unmodifiableQuestions();
		Map<QuestionNumber, String> answers = new HashMap<>();
		for (QuestionNumber questionNumber : questions.keySet()) {
			System.out.println(questions.get(questionNumber).question());
			answers.put(questionNumber, scanReader.nextLine());
		}
		PersonAnswers personAnswers = PersonAnswers.createInstanceFrom(answers, username);
		return checkList.calculateResult(personAnswers);
	}
}
