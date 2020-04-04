package ru.otus.krivonos.library.service;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.otus.krivonos.library.dao.BookDao;
import ru.otus.krivonos.library.exception.BookDaoException;
import ru.otus.krivonos.library.dao.GenreDao;
import ru.otus.krivonos.library.exception.GenreDaoException;
import ru.otus.krivonos.library.domain.Author;
import ru.otus.krivonos.library.domain.Book;
import ru.otus.krivonos.library.domain.Genre;
import ru.otus.krivonos.library.exception.LibraryServiceException;
import ru.otus.krivonos.library.exception.NotValidParameterDataException;

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

	@Override
	public void saveBook(String bookTitle, String authorName, String genreType) throws LibraryServiceException, NotValidParameterDataException {
		long startTime = System.currentTimeMillis();
		LOG.debug("method=save action=\"добавление книги в библиотеку\" bookTitle={} authorName={} genreType={}", bookTitle, authorName, genreType);

		List<String> errors = validateOnNullAndEmpty(bookTitle, authorName, genreType);
		if (!errors.isEmpty()) {
			throw new NotValidParameterDataException(errors);
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
				throw new NotValidParameterDataException(Arrays.asList("Жанр '" + genre.getType() + "' отстутствует, добавтьте его при необходимости"));
			}
		} catch (BookDaoException | GenreDaoException e) {
			throw new LibraryServiceException("Возникла ошибка во время работы с репозиторием книг", e);
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
	public Book findBookBy(long id) throws LibraryServiceException, NotValidParameterDataException {
		try {
			long startTime = System.currentTimeMillis();
			LOG.debug("method=findBookBy action=\"получение книги\" bookId={}", id);

			Book book = bookDao.findBookBy(id).orElseThrow(() -> new NotValidParameterDataException(Arrays.asList("Книга с id='" + id + "' в библиотеке не найдена")));

			long endTime = System.currentTimeMillis();
			LOG.debug("method=findBookBy action=\"найдена книга\" book={} time={}ms", book, endTime - startTime);

			return book;
		} catch (BookDaoException e) {
			throw new LibraryServiceException("Возникла ошибка во время работы с репозиторием книг", e);
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
			throw new LibraryServiceException("Возникла ошибка во время работы с репозиторием книг", e);
		}
	}

	@Override
	public void updateBook(long id, String bookTitle, String authorName, String genreType) throws LibraryServiceException, NotValidParameterDataException {
		long startTime = System.currentTimeMillis();
		LOG.debug("method=updateBook action=\"обновление книги\" id={} bookTitle={} authorName={} genreType={}", id, bookTitle, authorName, genreType);

		List<String> errors = validateOnNullAndEmpty(bookTitle, authorName, genreType);
		if (!errors.isEmpty()) {
			throw new NotValidParameterDataException(errors);
		}
		try {
			Optional<Book> optionalBook = bookDao.findBookBy(id);
			optionalBook.orElseThrow(() -> new NotValidParameterDataException(Arrays.asList("Книга с id='" + id + "' в библиотеке не найдена")));
			Author author = new Author(authorName);
			Genre genre = new Genre(genreType);
			if (genreDao.isExist(genre)) {
				Book book = new Book(id, bookTitle, author, genre);
				bookDao.saveBook(book);

				long endTime = System.currentTimeMillis();
				LOG.debug("method=updateBook action=\"книга обновлена\" book={} time={}ms", book, endTime - startTime);
			} else {
				throw new NotValidParameterDataException(Arrays.asList("Жанр '" + genre.getType() + "' отстутствует, добавтьте его при необходимости"));
			}
		} catch (BookDaoException | GenreDaoException e) {
			throw new LibraryServiceException("Возникла ошибка во время работы с репозиторием книг", e);
		}
	}

	@Override
	public void deleteBookBy(long id) throws LibraryServiceException, NotValidParameterDataException {
		try {
			long startTime = System.currentTimeMillis();
			LOG.debug("method=deleteBookBy action=\"удаление книги\" bookId={}", id);

			Optional<Book> optionalBook = bookDao.findBookBy(id);
			if (optionalBook.isPresent()) {
				bookDao.deleteBookBy(id);

				long endTime = System.currentTimeMillis();
				LOG.debug("method=deleteBookBy action=\"удалена книга\" optionalBook={} time={}ms", optionalBook, endTime - startTime);
			} else {
				throw new NotValidParameterDataException(Arrays.asList("Книга с id='" + id + "' в библиотеке не найдена"));
			}
		} catch (BookDaoException e) {
			throw new LibraryServiceException("Возникла ошибка во время работы с репозиторием книг", e);
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
			throw new LibraryServiceException("Возникла ошибка во время работы с репозиторием книг", e);
		}
	}

	@Override
	public void saveGenre(String type) throws LibraryServiceException, NotValidParameterDataException {
		if (type == null || "".equals(type.trim())) {
			throw new NotValidParameterDataException(Arrays.asList("Не задан литературный жанр"));
		}
		try {
			long startTime = System.currentTimeMillis();
			LOG.debug("method=saveGenre action=\"сохранение литературного жанра\" type={}", type);

			Genre genre = new Genre(type);
			if (genreDao.isExist(genre)) {
				throw new NotValidParameterDataException(Arrays.asList("Указанный литературный жанр '" + genre.getType() + "' уже существует"));
			}
			genreDao.saveGenre(genre);

			long endTime = System.currentTimeMillis();
			LOG.debug("method=saveGenre action=\"сохранен литературный жанр\" genre={} time={}ms", genre, endTime - startTime);
		} catch (GenreDaoException e) {
			throw new LibraryServiceException("Возникла ошибка во время работы с репозиторием книг", e);
		}
	}
}