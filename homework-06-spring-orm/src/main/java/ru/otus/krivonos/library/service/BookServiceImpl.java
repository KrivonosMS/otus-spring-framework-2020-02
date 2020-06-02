package ru.otus.krivonos.library.service;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.krivonos.library.dao.AuthorDao;
import ru.otus.krivonos.library.dao.BookDao;
import ru.otus.krivonos.library.dao.GenreDao;
import ru.otus.krivonos.library.exception.AuthorDaoException;
import ru.otus.krivonos.library.exception.BookDaoException;
import ru.otus.krivonos.library.exception.BookServiceException;
import ru.otus.krivonos.library.exception.GenreDaoException;
import ru.otus.krivonos.library.model.Author;
import ru.otus.krivonos.library.model.Book;
import ru.otus.krivonos.library.model.Genre;

import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@Service
public class BookServiceImpl implements BookService {
	public static final Logger LOG = LoggerFactory.getLogger(BookServiceImpl.class);

	private final BookDao bookDao;
	private final GenreDao genreDao;
	private final AuthorDao authorDao;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveBook(String bookTitle, long authorId, String authorName, long genreId) throws BookServiceException {
		saveBook(0, bookTitle, authorId, authorName, genreId);
	}

	@Override
	public Book findBookBy(long id) throws BookServiceException {
		try {
			long startTime = System.currentTimeMillis();
			LOG.debug("method=findBookBy action=\"получение книги\" bookId={}", id);

			Book book = bookDao.findBy(id).orElseThrow(() -> new BookServiceException(Arrays.asList("Книга с id='" + id + "' в библиотеке не найдена")));

			long endTime = System.currentTimeMillis();
			LOG.debug("method=findBookBy action=\"найдена книга\" book={} time={}ms", book, endTime - startTime);

			return book;
		} catch (BookDaoException e) {
			throw new BookServiceException("Возникла непредвиденная ошибка при поиске книги", e);
		}
	}

	@Override
	public List<Book> findAllBooks() throws BookServiceException {
		try {
			long startTime = System.currentTimeMillis();
			LOG.debug("method=findAllBooks action=\"получени всех книг\"");

			List<Book> books = bookDao.findAll();

			long endTime = System.currentTimeMillis();
			LOG.debug("method=findAllBooks action=\"получены все книги\" count={} time={}ms", books.size(), endTime - startTime);

			return books;
		} catch (BookDaoException e) {
			throw new BookServiceException("Возникла непредвиденная ошибка при получении всех книг", e);
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveBook(long bookId, String bookTitle, long authorId, String authorName, long genreId) throws BookServiceException {
		long startTime = System.currentTimeMillis();
		LOG.debug("method=save action=\"сохранеие книги в библиотеку\" bookId={} bookTitle={} authorId={} authorName={} genreId={}", bookId, bookTitle, authorId, authorName, genreId);

		bookTitle = bookTitle == null ? "" : bookTitle.trim();
		authorName = authorName == null ? "" : authorName.trim();
		if ("".equals(bookTitle)) {
			throw new BookServiceException("Не задано название книги");
		}
		if (authorId == 0 && "".equals(authorName)) {
			throw new BookServiceException("Не задан автор книги");
		}
		try {
			Author author;
			if (authorId == 0) {
				author = new Author(authorName);
			} else {
				author = authorDao.findBy(authorId).orElseThrow(() -> new BookServiceException("Отсутствует автор с id=" + authorId));
			}
			Genre genre = genreDao.findBy(genreId).orElseThrow(() -> new BookServiceException("Отсутствует литературный жанр с id=" + genreId));
			Book book = new Book(bookId, bookTitle, author, genre);
			bookId = bookDao.save(book);

			long endTime = System.currentTimeMillis();
			LOG.debug("method=save action=\"книга сохранена в библиотеку\" bookId={} book={} time={}ms", bookId, book, endTime - startTime);
		} catch (BookDaoException | GenreDaoException | AuthorDaoException e) {
			throw new BookServiceException("Возникла непредвиденная ошибка при сохранении книги", e);
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteBookBy(long id) throws BookServiceException {
		try {
			long startTime = System.currentTimeMillis();
			LOG.debug("method=deleteBookBy action=\"удаление книги\" bookId={}", id);

			Book book = bookDao.findBy(id).orElseThrow(() -> new BookServiceException("Книга с id='" + id + "' в библиотеке не найдена"));
			bookDao.delete(book);

			long endTime = System.currentTimeMillis();
			LOG.debug("method=deleteBookBy action=\"удалена книга\" optional={} time={}ms", book, endTime - startTime);
		} catch (BookDaoException e) {
			throw new BookServiceException("Возникла непредвиденная ошибка при удалении книги", e);
		}
	}
}