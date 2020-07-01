package ru.otus.krivonos.library.service;

import reactor.core.publisher.Mono;
import ru.otus.krivonos.library.exception.BookServiceException;
import ru.otus.krivonos.library.model.Book;

public interface BookService {
	Mono<Book> createBook(String bookTitle, String authorName, String genreId, String genreType) throws BookServiceException;

	Mono<Book> updateBook(String id, String bookTitle, String authorName, String genreId, String genreType) throws BookServiceException;
}