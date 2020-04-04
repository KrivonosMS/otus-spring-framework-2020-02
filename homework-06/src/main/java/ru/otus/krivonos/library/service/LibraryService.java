package ru.otus.krivonos.library.service;

import ru.otus.krivonos.library.domain.Book;
import ru.otus.krivonos.library.domain.Genre;
import ru.otus.krivonos.library.exception.LibraryServiceException;
import ru.otus.krivonos.library.exception.NotValidParameterDataException;

import java.util.List;

public interface LibraryService {
	void saveBook(String bookTitle, String authorName, String genreType) throws LibraryServiceException, NotValidParameterDataException;

	Book findBookBy(long id) throws LibraryServiceException, NotValidParameterDataException;

	List<Book> findAllBooks() throws LibraryServiceException;

	void updateBook(long id, String bookTitle, String authorName, String genreType) throws LibraryServiceException, NotValidParameterDataException;

	void deleteBookBy(long id) throws LibraryServiceException, NotValidParameterDataException;

	List<Genre> findAllGenres() throws LibraryServiceException;

	void saveGenre(String type) throws LibraryServiceException, NotValidParameterDataException;
}
