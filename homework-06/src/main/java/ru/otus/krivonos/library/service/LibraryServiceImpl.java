package ru.otus.krivonos.library.service;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.otus.krivonos.library.dao.BookDao;
import ru.otus.krivonos.library.dao.CommentDao;
import ru.otus.krivonos.library.dao.GenreDao;
import ru.otus.krivonos.library.domain.Author;
import ru.otus.krivonos.library.domain.Book;
import ru.otus.krivonos.library.domain.Comment;
import ru.otus.krivonos.library.domain.Genre;
import ru.otus.krivonos.library.exception.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class LibraryServiceImpl implements LibraryService {
	public static final Logger LOG = LoggerFactory.getLogger(LibraryServiceImpl.class);

	private final BookDao bookDao;
	private final GenreDao genreDao;
	private final CommentDao commentDao;

	@Override
	public void saveBook(String bookTitle, String authorName, String genreType) throws LibraryServiceException {
		long startTime = System.currentTimeMillis();
		LOG.debug("method=save action=\"добавление книги в библиотеку\" bookTitle={} authorName={} genreType={}", bookTitle, authorName, genreType);

		List<String> errors = validateOnNullAndEmpty(bookTitle, authorName, genreType);
		if (!errors.isEmpty()) {
			throw new LibraryServiceException(errors);
		}
		try {
			Author author = new Author(authorName);
			Genre genre = new Genre(genreType);
			if (genreDao.isExist(genre)) {
				Book book = new Book(bookTitle, author, genre);
				long bookId = bookDao.saveBook(book);

				long endTime = System.currentTimeMillis();
				LOG.debug("method=save action=\"книга добавлена в библиотеку\" bookId={} book={} time={}ms", bookId, book, endTime - startTime);
			} else {
				throw new LibraryServiceException(Arrays.asList("Жанр '" + genre.getType() + "' отстутствует, добавтьте его при необходимости"));
			}
		} catch (BookDaoException | GenreDaoException e) {
			throw new LibraryServiceException("Возникла непредвиденная ошибка при сохранении книги", e);
		}
	}

	private List<String> validateOnNullAndEmpty(String bookTitle, String authorName, String genreType) {
		List<String> errors = new ArrayList<>();
		if (bookTitle == null || "".equals(bookTitle.trim())) {
			errors.add("Не задано название книги");
		}
		if (authorName == null || "".equals(authorName.trim())) {
			errors.add("Не задан автор книги");
		}
		if (genreType == null || "".equals(genreType.trim())) {
			errors.add("Не задан литературный жанр");
		}
		return errors;
	}

	@Override
	public Book findBookBy(long id) throws LibraryServiceException {
		try {
			long startTime = System.currentTimeMillis();
			LOG.debug("method=findBookBy action=\"получение книги\" bookId={}", id);

			Book book = bookDao.findBookBy(id).orElseThrow(() -> new LibraryServiceException(Arrays.asList("Книга с id='" + id + "' в библиотеке не найдена")));

			long endTime = System.currentTimeMillis();
			LOG.debug("method=findBookBy action=\"найдена книга\" book={} time={}ms", book, endTime - startTime);

			return book;
		} catch (BookDaoException e) {
			throw new LibraryServiceException("Возникла непредвиденная ошибка при поиске книги", e);
		}
	}

	@Override
	public List<Book> findAllBooks() throws LibraryServiceException {
		try {
			long startTime = System.currentTimeMillis();
			LOG.debug("method=findAllBooks action=\"получени всех книг\"");

			List<Book> books = bookDao.findAllBooks();

			long endTime = System.currentTimeMillis();
			LOG.debug("method=findAllBooks action=\"получены все книги\" count={} time={}ms", books.size(), endTime - startTime);

			return books;
		} catch (BookDaoException e) {
			throw new LibraryServiceException("Возникла непредвиденная ошибка при получении всех книг", e);
		}
	}

	@Override
	public void updateBook(long id, String bookTitle, String authorName, String genreType) throws LibraryServiceException {
		long startTime = System.currentTimeMillis();
		LOG.debug("method=updateBook action=\"обновление книги\" id={} bookTitle={} authorName={} genreType={}", id, bookTitle, authorName, genreType);

		List<String> errors = validateOnNullAndEmpty(bookTitle, authorName, genreType);
		if (!errors.isEmpty()) {
			throw new LibraryServiceException(errors);
		}
		try {
			Optional<Book> optionalBook = bookDao.findBookBy(id);
			optionalBook.orElseThrow(() -> new LibraryServiceException(Arrays.asList("Книга с id='" + id + "' в библиотеке не найдена")));
			Author author = new Author(authorName);
			Genre genre = new Genre(genreType);
			if (genreDao.isExist(genre)) {
				Book book = new Book(id, bookTitle, author, genre);
				bookDao.saveBook(book);

				long endTime = System.currentTimeMillis();
				LOG.debug("method=updateBook action=\"книга обновлена\" book={} time={}ms", book, endTime - startTime);
			} else {
				throw new LibraryServiceException("Жанр '" + genre.getType() + "' отстутствует, добавтьте его при необходимости");
			}
		} catch (BookDaoException | GenreDaoException e) {
			throw new LibraryServiceException("Возникла непредвиденная ошибка при обновлении книги", e);
		}
	}

	@Override
	public void deleteBookBy(long id) throws LibraryServiceException {
		try {
			long startTime = System.currentTimeMillis();
			LOG.debug("method=deleteBookBy action=\"удаление книги\" bookId={}", id);

			Optional<Book> optionalBook = bookDao.findBookBy(id);
			if (optionalBook.isPresent()) {
				bookDao.deleteBookBy(id);

				long endTime = System.currentTimeMillis();
				LOG.debug("method=deleteBookBy action=\"удалена книга\" optionalBook={} time={}ms", optionalBook, endTime - startTime);
			} else {
				throw new LibraryServiceException("Книга с id='" + id + "' в библиотеке не найдена");
			}
		} catch (BookDaoException e) {
			throw new LibraryServiceException("Возникла непредвиденная ошибка при удалении книги", e);
		}
	}

	@Override
	public List<Genre> findAllGenres() throws LibraryServiceException {
		try {
			long startTime = System.currentTimeMillis();
			LOG.debug("method=findAllGenres action=\"получени всех литературных жанров\"");

			List<Genre> genres = genreDao.findAllGenres();

			long endTime = System.currentTimeMillis();
			LOG.debug("method=findAllGenres action=\"получены все литературные жанры\" count={} time={}ms", genres.size(), endTime - startTime);

			return genres;
		} catch (GenreDaoException e) {
			throw new LibraryServiceException("Возникла непредвиденная ошибка при получении вссех литературных жанров", e);
		}
	}

	@Override
	public void saveGenre(String type) throws LibraryServiceException {
		if (type == null || "".equals(type.trim())) {
			throw new LibraryServiceException("Не задан литературный жанр");
		}
		try {
			long startTime = System.currentTimeMillis();
			LOG.debug("method=saveGenre action=\"сохранение литературного жанра\" type={}", type);

			Genre genre = new Genre(type);
			if (genreDao.isExist(genre)) {
				throw new LibraryServiceException("Указанный литературный жанр '" + genre.getType() + "' уже существует");
			}
			genreDao.saveGenre(genre);

			long endTime = System.currentTimeMillis();
			LOG.debug("method=saveGenre action=\"сохранен литературный жанр\" genre={} time={}ms", genre, endTime - startTime);
		} catch (GenreDaoException e) {
			throw new LibraryServiceException("Возникла непредвиденная ошибка при сохранении литературного жанра", e);
		}
	}

	@Override
	public void addBookComment(long bookId, String text) throws LibraryServiceException {
		if (text == null || "".equals(text.trim())) {
			throw new LibraryServiceException("Не задан комментарий для сохранения");
		}
		try {
			long startTime = System.currentTimeMillis();
			LOG.debug("method=addBookComment action=\"сохранение комментария к книге\" bookId={}", bookId);
			LOG.trace("method=addBookComment action=\"сохранение комментария к книге\" bookId={} text={}", bookId, text);

			Optional<Book> optionalBook = bookDao.findBookBy(bookId);
			if (optionalBook.isPresent()) {
				Comment comment = new Comment(bookId, text);
				commentDao.addBookComment(comment);
			} else {
				throw new LibraryServiceException("Не найдена книга для добавления комментария");
			}

			long endTime = System.currentTimeMillis();
			LOG.debug("method=addBookComment action=\"комментарий сохранен\" bookId={} time={}ms", bookId, endTime - startTime);
		} catch (CommentDaoException | BookDaoException e) {
			throw new LibraryServiceException("Возникла непредвиденная ошибка при добавлении комментария к книге", e);
		}
	}

	@Override
	public void deleteCommentById(long id) throws LibraryServiceException {
		try {
			long startTime = System.currentTimeMillis();
			LOG.debug("method=deleteCommentById action=\"удаление комментария\" id={}", id);

			if (commentDao.isExist(id)) {
				commentDao.deleteCommentById(id);
			} else {
				throw new LibraryServiceException("Отсутствует комментарий для удаления");
			}

			long endTime = System.currentTimeMillis();
			LOG.debug("method=deleteCommentById action=\"комментарий удален\" id={} time={}ms", id, endTime - startTime);
		} catch (CommentDaoException e) {
			throw new LibraryServiceException("Возникла непредвиденная ошибка при удалении комментария", e);
		}
	}
}