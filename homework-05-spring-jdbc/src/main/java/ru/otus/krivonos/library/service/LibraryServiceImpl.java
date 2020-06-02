package ru.otus.krivonos.library.service;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.otus.krivonos.library.dao.LibraryDao;
import ru.otus.krivonos.library.dao.LibraryDaoException;
import ru.otus.krivonos.library.domain.Author;
import ru.otus.krivonos.library.domain.Book;
import ru.otus.krivonos.library.domain.BookTitle;
import ru.otus.krivonos.library.domain.Genre;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class LibraryServiceImpl implements LibraryService {
	public static final Logger LOG = LoggerFactory.getLogger(LibraryServiceImpl.class);

	private final LibraryDao libraryDao;

	@Override
	public void saveBook(String bookTitle, String authorName, String genreType) throws LibraryServiceException, NotValidParameterDataException {
		long startTime = System.currentTimeMillis();
		LOG.debug("method=save action=\"добавление книги в библиотеку\" bookTitle={} authorName={} genreType={}", bookTitle, authorName, genreType);

		List<String> errors = validateOnNullAndEmpty(bookTitle, authorName, genreType);
		if (!errors.isEmpty()) {
			throw new NotValidParameterDataException(errors);
		}
		try {
			BookTitle title = new BookTitle(bookTitle);
			Author author = new Author(authorName);
			Genre genre = new Genre(genreType);
			if (libraryDao.isExist(genre)) {
				Book book = new Book(title, author, genre);
				long bookId = libraryDao.saveBook(book);

				long endTime = System.currentTimeMillis();
				LOG.debug("method=save action=\"книга добавлена в библиотеку\" bookId={} book={} time={}ms", bookId, book, endTime - startTime);
			} else {
				throw new NotValidParameterDataException(Arrays.asList("Жанр '" + genre.type() + "' отстутствует, добавтьте его при необходимости"));
			}
		} catch (LibraryDaoException e) {
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

			Book book = libraryDao.findBookBy(id).orElseThrow(() -> new NotValidParameterDataException(Arrays.asList("Книга с id='" + id + "' в библиотеке не найдена")));

			long endTime = System.currentTimeMillis();
			LOG.debug("method=findBookBy action=\"найдена книга\" book={} time={}ms", book, endTime - startTime);

			return book;
		} catch (LibraryDaoException e) {
			throw new LibraryServiceException("Возникла ошибка во время работы с репозиторием книг", e);
		}
	}

	@Override
	public List<Book> findAllBooks() throws LibraryServiceException {
		try {
			long startTime = System.currentTimeMillis();
			LOG.debug("method=findAllBooks action=\"получени всех книг\"");

			List<Book> books = libraryDao.findAllBooks();

			long endTime = System.currentTimeMillis();
			LOG.debug("method=findAllBooks action=\"получены все книги\" count={} time={}ms", books.size(), endTime - startTime);

			return books;
		} catch (LibraryDaoException e) {
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
			Optional<Book> optionalBook = libraryDao.findBookBy(id);
			optionalBook.orElseThrow(() -> new NotValidParameterDataException(Arrays.asList("Книга с id='" + id + "' в библиотеке не найдена")));
			BookTitle title = new BookTitle(bookTitle);
			Author author = new Author(authorName);
			Genre genre = new Genre(genreType);
			if (libraryDao.isExist(genre)) {
				Book book = new Book(id, title, author, genre);
				libraryDao.updateBook(book);

				long endTime = System.currentTimeMillis();
				LOG.debug("method=updateBook action=\"книга обновлена\" book={} time={}ms", book, endTime - startTime);
			} else {
				throw new NotValidParameterDataException(Arrays.asList("Жанр '" + genre.type() + "' отстутствует, добавтьте его при необходимости"));
			}
		} catch (LibraryDaoException e) {
			throw new LibraryServiceException("Возникла ошибка во время работы с репозиторием книг", e);
		}
	}

	@Override
	public void deleteBookBy(long id) throws LibraryServiceException, NotValidParameterDataException {
		try {
			long startTime = System.currentTimeMillis();
			LOG.debug("method=deleteBookBy action=\"удаление книги\" bookId={}", id);

			Optional<Book> optionalBook = libraryDao.findBookBy(id);
			if (optionalBook.isPresent()) {
				libraryDao.deleteBookBy(id);

				long endTime = System.currentTimeMillis();
				LOG.debug("method=deleteBookBy action=\"удалена книга\" optionalBook={} time={}ms", optionalBook, endTime - startTime);
			} else {
				throw new NotValidParameterDataException(Arrays.asList("Книга с id='" + id + "' в библиотеке не найдена"));
			}
		} catch (LibraryDaoException e) {
			throw new LibraryServiceException("Возникла ошибка во время работы с репозиторием книг", e);
		}
	}

	@Override
	public List<Genre> findAllGenres() throws LibraryServiceException {
		try {
			long startTime = System.currentTimeMillis();
			LOG.debug("method=findAllGenres action=\"получени всех литературных жанров\"");

			List<Genre> genres = libraryDao.findAllGenres();

			long endTime = System.currentTimeMillis();
			LOG.debug("method=findAllGenres action=\"получены все литературные жанры\" count={} time={}ms", genres.size(), endTime - startTime);

			return genres;
		} catch (LibraryDaoException e) {
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
			if (libraryDao.isExist(genre)) {
				throw new NotValidParameterDataException(Arrays.asList("Указанный литературный жанр '" + genre.type() + "' уже существует"));
			}
			libraryDao.saveGenre(genre);

			long endTime = System.currentTimeMillis();
			LOG.debug("method=saveGenre action=\"сохранен литературный жанр\" genre={} time={}ms", genre, endTime - startTime);
		} catch (LibraryDaoException e) {
			throw new LibraryServiceException("Возникла ошибка во время работы с репозиторием книг", e);
		}
	}
}