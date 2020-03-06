package ru.otus.krivonos.exam.domain.model;

public class Result {
	private String username;
	private double successResultPercent;
	private double userCorrectAnswersPercent;

	private Result(String username, double successResultPercent, double userCorrectAnswersPercent) throws ResultCreationException {
		setUsername(username);
		setSuccessResultPercent(successResultPercent);
		setUserCorrectAnswersPercent(userCorrectAnswersPercent);
	}

	static Result createInstanceFrom(String username, double successResultPercent, double userCorrectAnswersPercent) throws ResultCreationException {
		return new Result(username, successResultPercent, userCorrectAnswersPercent);
	}

	public boolean isSuccess() {
		return userCorrectAnswersPercent >= successResultPercent;
	}

	public double userPercentResult() {
		return userCorrectAnswersPercent;
	}

	public String username() {
		return this.username;
	}

	private void setUsername(String username) throws ResultCreationException {
		if (username == null || "".equals(username)) {
			throw new ResultCreationException("Не задан пользователь");
		}
		this.username = username;
	}

	private void setSuccessResultPercent(double successResultPercent) {
		this.successResultPercent = successResultPercent;
	}

	private void setUserCorrectAnswersPercent(double userCorrectAnswers) {
		this.userCorrectAnswersPercent = userCorrectAnswers * 100;
	}

	@Override
	public String toString() {
		return "Result{" +
			"username='" + username + '\'' +
			", successResultPercent=" + successResultPercent +
			", userCorrectAnswersPercent=" + userCorrectAnswersPercent +
			'}';
	}
}
