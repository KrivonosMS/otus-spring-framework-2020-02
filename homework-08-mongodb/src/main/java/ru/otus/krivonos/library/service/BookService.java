package ru.otus.krivonos.library.service;

import ru.otus.krivonos.library.exception.BookServiceException;
import ru.otus.krivonos.library.model.Book;

import java.util.List;

public interface BookService {
	void saveBook(String bookTitle, String authorName, String genreId) throws BookServiceException;

	Book findBookBy(String id) throws BookServiceException;

	List<Book> findAllBooks() throws BookServiceException;

	void saveBook(String id, String bookTitle, String authorName, String genreId) throws BookServiceException;

	void deleteBookBy(String id) throws BookServiceException;
}
