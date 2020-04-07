package ru.otus.krivonos.library.dao;

import ru.otus.krivonos.library.model.Book;
import ru.otus.krivonos.library.exception.BookDaoException;

import java.util.List;
import java.util.Optional;

public interface BookDao {
	Optional<Book> findBy(long id) throws BookDaoException;

	List<Book> findAll() throws BookDaoException;

	long save(Book book) throws BookDaoException;

	void deleteBy(long id) throws BookDaoException;
}
