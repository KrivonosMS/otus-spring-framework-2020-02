package ru.otus.krivonos.library.dao;

import ru.otus.krivonos.library.domain.Book;
import ru.otus.krivonos.library.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface LibraryDao {
	Optional<Book> findBookBy(long id) throws LibraryDaoException;

	List<Book> findAllBooks() throws LibraryDaoException;

	long saveBook(Book book) throws LibraryDaoException;

	void deleteBookBy(long id) throws LibraryDaoException;

	List<Genre> findAllGenres() throws LibraryDaoException;

	boolean isExist(Genre genre) throws LibraryDaoException;

	long saveGenre(Genre genre) throws LibraryDaoException;
}
