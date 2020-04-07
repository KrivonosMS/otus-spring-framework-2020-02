package ru.otus.krivonos.library.dao;

import ru.otus.krivonos.library.model.Comment;
import ru.otus.krivonos.library.exception.CommentDaoException;

import java.util.List;

public interface CommentDao {
	long addBookComment(Comment comment) throws CommentDaoException;

	List<Comment> findAllCommentsBy(long bookId) throws CommentDaoException;

	void deleteCommentById(long id) throws CommentDaoException;

	boolean isExist(long id) throws CommentDaoException;
}
