package ru.otus.krivonos.library.service;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.otus.krivonos.library.dao.BookRepository;
import ru.otus.krivonos.library.dao.GenreRepository;
import ru.otus.krivonos.library.exception.BookServiceException;
import ru.otus.krivonos.library.model.Author;
import ru.otus.krivonos.library.model.Book;
import ru.otus.krivonos.library.model.Genre;

@AllArgsConstructor
@Service
public class BookServiceImpl implements BookService {
	public static final Logger LOG = LoggerFactory.getLogger(BookServiceImpl.class);

	private final BookRepository bookRepository;
	private final GenreRepository genreRepository;

	@Override
	public Mono<Book> createBook(String bookTitle, String authorName, String genreId, String genreType) throws BookServiceException {
		return saveBook("", bookTitle, authorName, genreId, genreType);
	}

	@Override
	public Mono<Book> updateBook(String bookId, String bookTitle, String authorName, String genreId, String genreType) throws BookServiceException {
		return saveBook(bookId, bookTitle, authorName, genreId, genreType);
	}

	private Mono<Book> saveBook(String bookId, String bookTitle, String authorName, String genreId, String genreType) throws BookServiceException {
		LOG.debug("method=save action=\"сохранеие книги в библиотеку\" bookId={} bookTitle={} authorName={} genreId={}", bookId, bookTitle, authorName, genreId);

		bookTitle = bookTitle == null ? "" : bookTitle.trim();
		authorName = authorName == null ? "" : authorName.trim();
		if ("".equals(bookTitle)) {
			throw new BookServiceException("Не задано название книги");
		}
		if ("".equals(authorName)) {
			throw new BookServiceException("Не задан автор книги");
		}

		Author author = new Author(authorName);
		Genre genre = new Genre(genreId, genreType);
		Book book = new Book(bookId, bookTitle, author, genre);
		return bookRepository.save(book);
	}
}