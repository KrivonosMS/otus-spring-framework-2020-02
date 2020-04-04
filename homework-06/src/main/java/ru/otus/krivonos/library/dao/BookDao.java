package ru.otus.krivonos.library.dao;

import ru.otus.krivonos.library.domain.Book;
import ru.otus.krivonos.library.exception.BookDaoException;

import java.util.List;
import java.util.Optional;

public interface BookDao {
	Optional<Book> findBookBy(long id) throws BookDaoException;

	List<Book> findAllBooks() throws BookDaoException;

	long saveBook(Book book) throws BookDaoException;

	void deleteBookBy(long id) throws BookDaoException;
}
