package ru.otus.krivonos.library.service;

import ru.otus.krivonos.library.model.Book;

import java.util.List;

public interface BookService {
	void createBook(String bookTitle, String authorName, long genreId);

	Book findBookBy(long id);

	List<Book> findAllBooks();

	void updateBook(long id, String bookTitle, String authorName, long genreId);

	void deleteBookBy(long id);
}
