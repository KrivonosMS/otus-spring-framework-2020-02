package ru.otus.krivonos.exam.domain.model;

import java.util.Objects;

public class QuestionNumber {
	private int number;

	private QuestionNumber(int number) {
		setNumber(number);
	}

	static QuestionNumber createInstanceFrom(int rowNumber) {
		return new QuestionNumber(rowNumber);
	}

	private void setNumber(int number) {
		this.number = number;
	}

	public int number() {
		return number;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		QuestionNumber that = (QuestionNumber) o;
		return number == that.number;
	}

	@Override
	public int hashCode() {
		return Objects.hash(number);
	}

	@Override
	public String toString() {
		return "QuestionNumber{" +
			"number=" + number +
			'}';
	}
}
