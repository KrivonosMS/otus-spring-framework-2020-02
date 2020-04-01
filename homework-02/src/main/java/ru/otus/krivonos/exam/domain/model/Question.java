package ru.otus.krivonos.exam.domain.model;

public class Question {
	private String question;
	private QuestionNumber questionNumber;

	private Question(String[] row, QuestionNumber questionNumber) throws QuestionCreationException {
		setQuestion(row);
		setQuestionNumber(questionNumber);
	}

	static Question createInstanceFrom(String[] row, QuestionNumber questionNumber) throws QuestionCreationException {
		return new Question(row, questionNumber);
	}

	private void setQuestion(String[] row) throws QuestionCreationException {
		if (row == null || row.length < 1 || row[0] == null || "".equals(row[0])) {
			throw new QuestionCreationException("Отсутствует вопрос теста");
		}
		this.question = row[0].trim();
	}

	private void setQuestionNumber(QuestionNumber questionNumber) {
		this.questionNumber = questionNumber;
	}

	public String question() {
		return this.question;
	}

	QuestionNumber questionNumber() {
		return this.questionNumber;
	}

	@Override
	public String toString() {
		return "Question{" +
			"question='" + question + '\'' +
			", questionNumber=" + questionNumber +
			'}';
	}
}
