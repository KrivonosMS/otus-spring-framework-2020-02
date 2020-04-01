package ru.otus.krivonos.exam.domain.model;

public class CorrectTestAnswer {
	private String answer;
	private QuestionNumber questionNumber;

	private CorrectTestAnswer(String[] row, QuestionNumber questionNumber) throws AnswerCreationException {
		addAnswer(row);
		addQuestionNumber(questionNumber);
	}

	static CorrectTestAnswer createInstanceFrom(String[] row, QuestionNumber questionNumber) throws AnswerCreationException {
		return new CorrectTestAnswer(row, questionNumber);
	}

	private void addAnswer(String[] row) throws AnswerCreationException {
		if (row == null || row.length < 2 || row[1] == null || "".equals(row[1])) {
			throw new AnswerCreationException("Отсутствует ответ для вопроса теста");
		}
		this.answer = row[1].trim();
	}

	private void addQuestionNumber(QuestionNumber questionNumber) {
		this.questionNumber = questionNumber;
	}

	public String answer() {
		return answer;
	}

	@Override
	public String toString() {
		return "CorrectTestAnswer{" +
			"answer='" + answer + '\'' +
			", questionNumber=" + questionNumber +
			'}';
	}
}
