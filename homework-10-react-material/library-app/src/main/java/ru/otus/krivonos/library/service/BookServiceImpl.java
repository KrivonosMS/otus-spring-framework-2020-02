package ru.otus.krivonos.library.service;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.krivonos.library.dao.AuthorRepository;
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
	private final AuthorRepository authorRepository;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void createBook(String bookTitle, String authorName, long genreId) {
		long startTime = System.currentTimeMillis();
		LOG.debug("method=createBook action=\"сохранеие книги в библиотеку\" bookTitle={} authorName={} genreId={}", bookTitle, authorName, genreId);

		Book book = saveBook(0, bookTitle, authorName, genreId);

		long endTime = System.currentTimeMillis();
		LOG.debug("method=createBook action=\"книга сохранена в библиотеку\" book={} time={}ms", book, endTime - startTime);
	}

	private Book saveBook(long bookId, String bookTitle, String authorName, long genreId) {
		bookTitle = bookTitle == null ? "" : bookTitle.trim();
		authorName = authorName == null ? "" : authorName.trim();
		if ("".equals(bookTitle)) {
			throw new BookServiceException("Не задано название книги");
		}
		if ("".equals(authorName)) {
			throw new BookServiceException("Не задан автор книги");
		}
		Author author = authorRepository.findByName(authorName).orElse(new Author(authorName));
		Genre genre = genreRepository.findById(genreId).orElseThrow(() -> new BookServiceException("Отсутствует литературный жанр c Id=" + genreId));
		Book book;
		if (bookId == 0) {
			book = new Book(0, bookTitle, author, genre);
		} else {
			book = bookRepository.findById(bookId).orElseThrow(() -> new BookServiceException("Отсутствует книга c Id=" + bookId));
			book.setTitle(bookTitle);
			book.setAuthor(author);
			book.setGenre(genre);
		}
		book = bookRepository.save(book);
		return book;
	}

	@Override
	public Book findBookBy(long id) {
		long startTime = System.currentTimeMillis();
		LOG.debug("method=findBookBy action=\"получение книги\" bookId={}", id);

		Book book = bookRepository.findById(id).orElseThrow(() -> new BookServiceException("Книга с id='" + id + "' в библиотеке не найдена"));

		long endTime = System.currentTimeMillis();
		LOG.debug("method=findBookBy action=\"найдена книга\" book={} time={}ms", book, endTime - startTime);

		return book;
	}

	@Override
	public List<Book> findAllBooks() {
		long startTime = System.currentTimeMillis();
		LOG.debug("method=findAllBooks action=\"получени всех книг\"");

		List<Book> books = bookRepository.findAll();

		long endTime = System.currentTimeMillis();
		LOG.debug("method=findAllBooks action=\"получены все книги\" count={} time={}ms", books.size(), endTime - startTime);

		return books;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateBook(long bookId, String bookTitle, String authorName, long genreId) {
		long startTime = System.currentTimeMillis();
		LOG.debug("method=save action=\"обновление книги с id={}\" bookTitle={} authorName={} genreId={}", bookId, bookTitle, authorName, genreId);

		Book book = saveBook(bookId, bookTitle, authorName, genreId);

		long endTime = System.currentTimeMillis();
		LOG.debug("method=save action=\"книга обновлена\" book={} time={}ms", book, endTime - startTime);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteBookBy(long id) {
		long startTime = System.currentTimeMillis();
		LOG.debug("method=deleteBookBy action=\"удаление книги\" bookId={}", id);

		Book book = bookRepository.findById(id).orElseThrow(() -> new BookServiceException("Книга с id='" + id + "' в библиотеке не найдена"));
		bookRepository.delete(book);

		long endTime = System.currentTimeMillis();
		LOG.debug("method=deleteBookBy action=\"удалена книга\" optional={} time={}ms", book, endTime - startTime);
	}
}