package ru.otus.krivonos.exam.domain;

import ru.otus.krivonos.exam.domain.model.Result;

public interface MessageRepository {
	String greetingMessage() throws MessageRepositoryException;

	String askingNameMessage() throws MessageRepositoryException;

	String successResultMessage(Result result) throws MessageRepositoryException;

	String badResultMessage(Result result) throws MessageRepositoryException;
}