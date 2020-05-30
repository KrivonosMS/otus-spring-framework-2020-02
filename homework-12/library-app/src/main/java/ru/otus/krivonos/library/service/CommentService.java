package ru.otus.krivonos.library.service;

import ru.otus.krivonos.library.model.Comment;

public interface CommentService {
	Comment addBookComment(long bookId, String text);

	void deleteCommentById(long id);
}
