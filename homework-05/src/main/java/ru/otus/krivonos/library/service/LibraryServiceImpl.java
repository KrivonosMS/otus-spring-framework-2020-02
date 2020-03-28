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
import java.util.List;

@AllArgsConstructor
@Service
public class LibraryServiceImpl implements LibraryService {
	public static final Logger LOG = LoggerFactory.getLogger(LibraryServiceImpl.class);

	private final LibraryDao libraryDao;

	@Override
	public void save(String bookTitle, String authorName, String genreType) throws LibraryServiceException {
		long startTime = System.currentTimeMillis();
		LOG.debug("method=save action=\"добавление книги в библиотеку\" bookTitle={} authorName={} genreType={}", bookTitle, authorName, genreType);

		List<String> errors = validateOnNullAndEmpty(bookTitle, authorName, genreType);
		if (!errors.isEmpty()) {
			throw new LibraryServiceException(String.join(". ", errors));
		}
		try {
			BookTitle title = new BookTitle(bookTitle);
			Author author = new Author(authorName);
			Genre genre = new Genre(genreType);
			if (libraryDao.isExist(genre)) {
				Book book = new Book(title, author, genre);
				long bookId = libraryDao.save(new Book(title, author, genre));

				long endTime = System.currentTimeMillis();
				LOG.debug("method=save action=\"книга добавлена в библиотеку\" bookId={} book={} time={}ms", bookId, book, endTime - startTime);
			} else {
				throw new LibraryServiceException("Указанный жанр '" + genre.type() + "' отсутствует в приложении");
			}
		} catch (LibraryDaoException e) {
			throw new LibraryServiceException("Возникла ошибка во время работы с репозиторием книг", e);
		}
	}

	private List<String> validateOnNullAndEmpty(String bookTitle, String authorName, String genreType) {
		List<String> errors = new ArrayList<>();
		if (bookTitle == null || "".equals(bookTitle.trim())) {
			errors.add("Не задано название книги.");
		}
		if (authorName == null || "".equals(authorName.trim())) {
			errors.add("Не задан автор книги.");
		}
		if (genreType == null || "".equals(genreType.trim())) {
			errors.add("Не задан литературный жанр.");
		}
		return errors;
	}
}
