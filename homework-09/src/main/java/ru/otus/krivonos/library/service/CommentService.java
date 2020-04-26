package ru.otus.krivonos.library.service;

import ru.otus.krivonos.library.exception.CommentServiceException;

public interface CommentService {
	void addBookComment(long bookId, String text) throws CommentServiceException;

	void deleteCommentById(long id) throws CommentServiceException;
}
