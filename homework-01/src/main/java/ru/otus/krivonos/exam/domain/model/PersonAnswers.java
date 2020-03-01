package ru.otus.krivonos.exam.domain.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class PersonAnswers {
	public static final Logger LOG = LoggerFactory.getLogger(PersonAnswers.class);

	private Map<QuestionNumber, String> personAnswers;
	private String username;

	private PersonAnswers(Map<QuestionNumber, String> personAnswers, String username) throws PersonAnswersCreationException {
		setPersonAnswers(personAnswers);
		addUsername(username);

	}

	public static PersonAnswers createInstanceFrom(Map<QuestionNumber, String> personAnswers, String username) throws PersonAnswersCreationException {
		LOG.debug("method=from action=\"формирование доменного объекта с ответами тестируемого\"");

		PersonAnswers answers = new PersonAnswers(personAnswers, username);

		LOG.debug("method=from action=\"завершение формированиея доменного объекта с ответами тестируемого\" personAnswers={}", answers);

		return answers;
	}

	private void setPersonAnswers(Map<QuestionNumber, String> personAnswers) throws PersonAnswersCreationException {
		if (personAnswers == null) {
			throw new PersonAnswersCreationException("Отсутствуют ответы тестируемого пользователя");
		}
		this.personAnswers = personAnswers;
	}

	private void addUsername(String username) throws PersonAnswersCreationException {
		if (username == null) {
			throw new PersonAnswersCreationException("Отсутствует имя пользователя");
		}
		this.username = username;
	}

	Map<QuestionNumber, String> personAnswers() {
		return personAnswers;
	}

	public String username() {
		return this.username;
	}

	@Override
	public String toString() {
		return "PersonAnswers{" +
			"personAnswers=" + personAnswers +
			", username='" + username + '\'' +
			'}';
	}
}
