package ru.otus.krivonos.library.service;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.otus.krivonos.library.dao.BookRepository;
import ru.otus.krivonos.library.dao.GenreRepository;
import ru.otus.krivonos.library.exception.BookServiceException;
import ru.otus.krivonos.library.model.Author;
import ru.otus.krivonos.library.model.Book;
import ru.otus.krivonos.library.model.Genre;

import java.util.List;

@AllArgsConstructor
@Service
public class BookServiceImpl implements BookService {
	public static final Logger LOG = LoggerFactory.getLogger(BookServiceImpl.class);

	private final BookRepository bookRepository;
	private final GenreRepository genreRepository;

	@Override
	public void saveBook(String bookTitle, String authorName, String genreId) throws BookServiceException {
		saveBook("", bookTitle, authorName, genreId);
	}

	@Override
	public Book findBookBy(String id) throws BookServiceException {
		long startTime = System.currentTimeMillis();
		LOG.debug("method=findBookBy action=\"получение книги\" bookId={}", id);

		Book book = bookRepository.findById(id).orElseThrow(() -> new BookServiceException("Книга с id='" + id + "' в библиотеке не найдена"));

		long endTime = System.currentTimeMillis();
		LOG.debug("method=findBookBy action=\"найдена книга\" book={} time={}ms", book, endTime - startTime);

		return book;
	}

	@Override
	public List<Book> findAllBooks() throws BookServiceException {
		long startTime = System.currentTimeMillis();
		LOG.debug("method=findAllBooks action=\"получени всех книг\"");

		List<Book> books = bookRepository.findAll();

		long endTime = System.currentTimeMillis();
		LOG.debug("method=findAllBooks action=\"получены все книги\" count={} time={}ms", books.size(), endTime - startTime);

		return books;
	}

	@Override
	public void saveBook(String bookId, String bookTitle, String authorName, String genreId) throws BookServiceException {
		long startTime = System.currentTimeMillis();
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
		Genre genre = genreRepository.findById(genreId).orElseThrow(() -> new BookServiceException("Отсутствует литературный жанр с id=" + genreId));
		Book book = new Book(bookId, bookTitle, author, genre);
		book = bookRepository.save(book);

		long endTime = System.currentTimeMillis();
		LOG.debug("method=save action=\"книга сохранена в библиотеку\" bookId={} book={} time={}ms", bookId, book, endTime - startTime);
	}

	@Override
	public void deleteBookBy(String id) throws BookServiceException {
		long startTime = System.currentTimeMillis();
		LOG.debug("method=deleteBookBy action=\"удаление книги\" bookId={}", id);

		Book book = bookRepository.findById(id).orElseThrow(() -> new BookServiceException("Книга с id='" + id + "' в библиотеке не найдена"));
		bookRepository.delete(book);

		long endTime = System.currentTimeMillis();
		LOG.debug("method=deleteBookBy action=\"удалена книга\" optional={} time={}ms", book, endTime - startTime);
	}
}