package ru.otus.krivonos.exam.domain.model;

public class Question {
	private String question;
	private QuestionNumber questionNumber;

	private Question(String question, QuestionNumber questionNumber) throws QuestionCreationException {
		setQuestion(question);
		setQuestionNumber(questionNumber);
	}

	static Question createInstanceFrom(String question, QuestionNumber questionNumber) throws QuestionCreationException {
		return new Question(question, questionNumber);
	}

	private void setQuestion(String question) throws QuestionCreationException {
		if (question == null || "".equals(question.trim())) {
			throw new QuestionCreationException("Отсутствует вопрос теста");
		}
		this.question = question.trim();
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
