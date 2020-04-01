package ru.otus.krivonos.exam.domain.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CheckList {
	public static final Logger LOG = LoggerFactory.getLogger(CheckList.class);

	private final Map<QuestionNumber, Question> unmodifiableQuestions;
	private final Map<QuestionNumber, CorrectTestAnswer> unmodifiableCorrectTestAnswers;

	private CheckList(Map<QuestionNumber, Question> questions, Map<QuestionNumber, CorrectTestAnswer> correctTestAnswers) {
		this.unmodifiableQuestions = Collections.unmodifiableMap(questions);
		this.unmodifiableCorrectTestAnswers = Collections.unmodifiableMap(correctTestAnswers);
	}

	public static CheckList createInstanceFrom(List<String[]> rowsData, double successResultPercent) throws CheckListCreationException {
		if (rowsData == null || rowsData.isEmpty()) {
			throw new CheckListCreationException("Отсутствуют данные для формирования теста");
		}
		LOG.debug("method=from action=\"формирование теста\" rowSize={} successResultPercent={}", rowsData.size(), successResultPercent);

		int rowsSize = rowsData.size();
		Map<QuestionNumber, Question> questions = new HashMap<>();
		Map<QuestionNumber, CorrectTestAnswer> answers = new HashMap<>();
		for (int i = 0; i < rowsSize; i++) {
			try {
				String[] row = rowsData.get(i);
				QuestionNumber questionNumber = QuestionNumber.createInstanceFrom(i + 1);
				Question question = Question.createInstanceFrom(row, questionNumber);
				questions.put(questionNumber, question);
				CorrectTestAnswer answer = CorrectTestAnswer.createInstanceFrom(row, questionNumber);
				answers.put(questionNumber, answer);
			} catch (QuestionCreationException e) {
				throw new CheckListCreationException("Ошибка при создании вопроса из строки № " + (i + 1), e);
			} catch (AnswerCreationException e) {
				throw new CheckListCreationException("Ошибка при создании ответа из строки № " + (i + 1), e);
			}
		}
		CheckList checkList = new CheckList(questions, answers);

		LOG.debug("method=from action=\"завершение формирования теста\" rowSize={} test={} successResultPercent={}", rowsSize, checkList, successResultPercent);

		return checkList;
	}

	public Result calculateResult(PersonAnswers personAnswers) throws CalculatedResultException, ResultCreationException {
		if (personAnswers == null) {
			throw new CalculatedResultException("Не заданы ответы пользователя");
		}
		LOG.debug("method=calculateResult action=\"вычисление результатов тестроивания\" username={}", personAnswers.username());

		Map<QuestionNumber, String> answers = personAnswers.personAnswers();
		int correctCountAnswer = 0;
		for (QuestionNumber questionNumber : answers.keySet()) {
			if (unmodifiableCorrectTestAnswers.get(questionNumber).answer().equalsIgnoreCase(answers.get(questionNumber))) {
				correctCountAnswer++;
			}
		}

		LOG.debug("method=calculateResult action=\"завершение вычисления результатов тестроивания\" username={} correctCountAnswer={} allQuestionCount={}", personAnswers.username(), correctCountAnswer, unmodifiableCorrectTestAnswers.size());

		return Result.createInstanceFrom (personAnswers.username(), 50, (double) correctCountAnswer / unmodifiableCorrectTestAnswers.size());
	}

	public Map<QuestionNumber, Question> unmodifiableQuestions() {
		return this.unmodifiableQuestions;
	}

	Map<QuestionNumber, CorrectTestAnswer> unmodifiableCorrectTestAnswers() {
		return this.unmodifiableCorrectTestAnswers;
	}

	@Override
	public String toString() {
		return "CheckList{" +
			"questions=" + unmodifiableQuestions +
			", correctTestAnswers=" + unmodifiableCorrectTestAnswers +
			'}';
	}
}