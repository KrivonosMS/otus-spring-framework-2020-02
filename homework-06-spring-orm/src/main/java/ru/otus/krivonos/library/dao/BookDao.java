package ru.otus.krivonos.library.dao;

import ru.otus.krivonos.library.exception.BookDaoException;
import ru.otus.krivonos.library.model.Book;
import ru.otus.krivonos.library.model.Comment;

import java.util.List;
import java.util.Optional;

public interface BookDao {
	Optional<Book> findBy(long id) throws BookDaoException;

	List<Book> findAll() throws BookDaoException;

	long save(Book book) throws BookDaoException;

	void delete(Book book) throws BookDaoException;

	List<Comment> findAllCommentsBy(long bookId) throws BookDaoException;
}
