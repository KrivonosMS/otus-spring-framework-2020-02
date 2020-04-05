package ru.otus.krivonos.library.service;

import ru.otus.krivonos.library.domain.Book;
import ru.otus.krivonos.library.domain.Genre;
import ru.otus.krivonos.library.exception.LibraryServiceException;

import java.util.List;

public interface LibraryService {
	void saveBook(String bookTitle, String authorName, String genreType) throws LibraryServiceException;

	Book findBookBy(long id) throws LibraryServiceException;

	List<Book> findAllBooks() throws LibraryServiceException;

	void updateBook(long id, String bookTitle, String authorName, String genreType) throws LibraryServiceException;

	void deleteBookBy(long id) throws LibraryServiceException;

	List<Genre> findAllGenres() throws LibraryServiceException;

	void saveGenre(String type) throws LibraryServiceException;

	void addBookComment(long bookId, String text) throws LibraryServiceException;

	void deleteCommentById(long id) throws LibraryServiceException;
}
