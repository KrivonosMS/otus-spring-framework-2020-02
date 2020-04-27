package ru.otus.krivonos.library.service;

import ru.otus.krivonos.library.exception.BookServiceException;
import ru.otus.krivonos.library.model.Book;

import java.util.List;

public interface BookService {
	void createBook(String bookTitle, String authorName, long genreId) throws BookServiceException;

	Book findBookBy(long id) throws BookServiceException;

	List<Book> findAllBooks() throws BookServiceException;

	void updateBook(long id, String bookTitle, String authorName, long genreId) throws BookServiceException;

	void deleteBookBy(long id) throws BookServiceException;
}
