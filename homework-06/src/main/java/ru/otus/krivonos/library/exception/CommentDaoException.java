package ru.otus.krivonos.library.exception;

public class CommentDaoException extends MainException
{
	public CommentDaoException(String message) {
		super(message);
	}

	public CommentDaoException(String message, Throwable cause) {
		super(message, cause);
	}
}
