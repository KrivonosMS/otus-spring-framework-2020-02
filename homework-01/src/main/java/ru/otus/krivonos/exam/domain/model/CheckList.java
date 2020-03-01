package ru.otus.krivonos.exam.domain.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CheckList {
	public static final Logger LOG = LoggerFactory.getLogger(CheckList.class);

	private Map<QuestionNumber, Question> questions;
	private Map<QuestionNumber, CorrectTestAnswer> correctTestAnswers;

	private CheckList() {
		questions = new HashMap<>();
		correctTestAnswers = new HashMap<>();
	}

	public static CheckList from(List<String[]> rowsData) throws CheckListCreationException {
		if (rowsData == null || rowsData.isEmpty()) {
			throw new CheckListCreationException("Отсутствуют данные для формирования теста");
		}
		LOG.debug("method=from action=\"формирование теста\"");

		CheckList checkList = new CheckList();
		int rowsSize = rowsData.size();
		for (int i = 0; i < rowsSize; i++) {
			try {
				String[] row = rowsData.get(i);
				QuestionNumber questionNumber = QuestionNumber.from(i + 1);
				Question question = Question.from(row, questionNumber);
				checkList.setQuestion(questionNumber, question);
				CorrectTestAnswer answer = CorrectTestAnswer.from(row, questionNumber);
				checkList.setAnswer(questionNumber, answer);
			} catch (QuestionCreationException e) {
				throw new CheckListCreationException("Ошибка при создании вопроса из строки № " + (i + 1), e);
			} catch (AnswerCreationException e) {
				throw new CheckListCreationException("Ошибка при создании ответа из строки № " + (i + 1), e);
			}
		}

		LOG.debug("method=from action=\"завершение формирования теста\" rowSize={} test={}", rowsSize, checkList);

		return checkList;
	}

	public double calculateResult(PersonAnswers personAnswers) throws CalculatedResultException {
		if (personAnswers == null) {
			throw new CalculatedResultException("Не заданы ответы пользователя");
		}
		LOG.debug("method=calculateResult action=\"вычисление результатов тестроивания\" username={}", personAnswers.username());

		Map<QuestionNumber, String> answers = personAnswers.personAnswers();
		int correctCountAnswer = 0;
		for (QuestionNumber questionNumber : answers.keySet()) {
			if (correctTestAnswers.get(questionNumber).answer().equalsIgnoreCase(answers.get(questionNumber))) {
				correctCountAnswer++;
			}
		}

		LOG.debug("method=calculateResult action=\"завершение вычисления результатов тестроивания\" username={} correctCountAnswer={} allQuestionCount={}", personAnswers.username(), correctCountAnswer, correctTestAnswers.size());

		return (double) correctCountAnswer / correctTestAnswers.size();
	}

	private void setQuestion(QuestionNumber questionNumber, Question question) {
		questions.put(questionNumber, question);
	}

	private void setAnswer(QuestionNumber questionNumber, CorrectTestAnswer answer) {
		correctTestAnswers.put(questionNumber, answer);
	}

	public Map<QuestionNumber, Question> questions() {
		return this.questions;
	}

	Map<QuestionNumber, CorrectTestAnswer> answers() {
		return this.correctTestAnswers;
	}

	@Override
	public String toString() {
		return "CheckList{" +
			"questions=" + questions +
			", correctTestAnswers=" + correctTestAnswers +
			'}';
	}
}