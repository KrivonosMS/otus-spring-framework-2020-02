package ru.otus.krivonos.library.dao;

import ru.otus.krivonos.library.exception.CommentDaoException;
import ru.otus.krivonos.library.model.Comment;

public interface CommentDao {
	long addBookComment(Comment comment) throws CommentDaoException;

	void deleteCommentById(long id) throws CommentDaoException;

	boolean isExist(long id) throws CommentDaoException;
}
