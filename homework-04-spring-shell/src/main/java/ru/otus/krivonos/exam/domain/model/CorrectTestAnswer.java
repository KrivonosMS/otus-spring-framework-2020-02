package ru.otus.krivonos.exam.domain.model;

public class CorrectTestAnswer {
	private String answer;
	private QuestionNumber questionNumber;

	private CorrectTestAnswer(String answer, QuestionNumber questionNumber) throws AnswerCreationException {
		addAnswer(answer);
		addQuestionNumber(questionNumber);
	}

	static CorrectTestAnswer createInstanceFrom(String answer, QuestionNumber questionNumber) throws AnswerCreationException {
		return new CorrectTestAnswer(answer, questionNumber);
	}

	private void addAnswer(String answer) throws AnswerCreationException {
		if (answer == null || "".equals(answer.trim())) {
			throw new AnswerCreationException("Отсутствует ответ для вопроса теста");
		}
		this.answer = answer.trim();
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
