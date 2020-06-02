package ru.otus.krivonos.library.exception;

public class CommentServiceException extends MainException {
	public CommentServiceException(String message) {
		super(message);
	}

	public CommentServiceException(String message, Throwable cause) {
		super(message, cause);
	}
}
