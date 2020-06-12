package ru.otus.krivonos.library.exception;

public class CommentServiceException extends MainException {
	public CommentServiceException(String clientMessage, String message) {
		super(clientMessage,message);
	}
}