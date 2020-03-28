package ru.otus.krivonos.library.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.krivonos.library.domain.Author;
import ru.otus.krivonos.library.domain.Book;
import ru.otus.krivonos.library.domain.BookTitle;
import ru.otus.krivonos.library.domain.Genre;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@JdbcTest
@Import(DbLibraryDao.class)
class DbLibraryDaoTest {
	@Autowired
	private LibraryDao libraryDao;

	@Test
	void shouldReturnAllBooks() throws Exception {
		List<Book> books = libraryDao.findAllBooks();

		assertEquals(7, books.size());
		assertThat(books).doesNotHaveDuplicates();
	}

	@Test
	void shouldReturnAllGenres() throws Exception {
		List<Genre> genres = libraryDao.findAllGenres();

		assertEquals(4, genres.size());
		assertThat(genres).doesNotHaveDuplicates();
	}

	@Test
	void shouldReturnBookById() throws Exception {
		Author author = new Author("Александр Пушкин");
		Genre genre = new Genre("Классическая проза");
		BookTitle bookTitle = new BookTitle("Евгений Онегин");
		Book expected = new Book(2, bookTitle, author, genre);

		Optional<Book> optionalBook = libraryDao.findBookBy(2l);

		assertThat(optionalBook).isNotEmpty();
		assertThat(optionalBook.get()).isEqualToComparingFieldByField(expected);
	}

	@Test
	void shouldDeleteBookById() throws Exception {
		Optional<Book> optionalBookBeforeDelete = libraryDao.findBookBy(2l);
		optionalBookBeforeDelete.get();

		libraryDao.deleteBy(2l);

		Optional<Book> optionalBookAfterDelete = libraryDao.findBookBy(2l);
		assertFalse(optionalBookAfterDelete.isPresent());
	}

	@Test
	void shouldReturnEmptyOptionalBookWhenNotExist() throws Exception {
		Optional<Book> optionalBookAfterDelete = libraryDao.findBookBy(-2l);

		assertFalse(optionalBookAfterDelete.isPresent());
	}

	@Test
	void shouldSaveBookWhenAuthorExist() throws Exception {
		Author author = new Author("Александр Пушкин");
		Genre genre = new Genre("Классическая проза");
		BookTitle bookTitle = new BookTitle("Евгений Онегин");
		Book expectedBook = new Book(bookTitle, author, genre);

		long id = libraryDao.save(expectedBook);

		Optional<Book> optionalBook = libraryDao.findBookBy(id);
		assertTrue(optionalBook.isPresent());
		Book actualBook = optionalBook.get();
		assertThat(expectedBook).isEqualToIgnoringGivenFields(actualBook, "id");
	}

	@Test
	void shouldSaveBookWhenAuthorNotExist() throws Exception {
		Author author = new Author("Михаил Лермонтов");
		Genre genre = new Genre("Классическая проза");
		BookTitle bookTitle = new BookTitle("Смерть поэта");
		Book expectedBook = new Book(bookTitle, author, genre);

		long id = libraryDao.save(expectedBook);

		Optional<Book> optionalBook = libraryDao.findBookBy(id);
		assertTrue(optionalBook.isPresent());
		Book actualBook = optionalBook.get();
		System.out.println(actualBook.author().name());
		assertThat(expectedBook).isEqualToIgnoringGivenFields(actualBook, "id");
	}

	@Test
	void shouldNotDeleteBookWhenBookIsNotExist() throws Exception {
		int count = libraryDao.findAllBooks().size();

		libraryDao.deleteBy(-2l);

		assertEquals(count, libraryDao.findAllBooks().size());
	}

	@Test
	void shouldReturnTrueIfGenreExist() throws Exception {
		Genre genre = new Genre("Русская классика");

		boolean isExist = libraryDao.isExist(genre);

		assertTrue(isExist);
	}

	@Test
	void shouldReturnFalseIfGenreIsNotExist() throws Exception {
		Genre genre = new Genre("Несуществующий жан");

		boolean isExist = libraryDao.isExist(genre);

		assertFalse(isExist);
	}

	@Test
	void shouldThrowLibraryDaoExceptionWhenBookIsNull() {
		LibraryDaoException exception = Assertions.assertThrows(LibraryDaoException.class, () -> {
			libraryDao.save(null);
		});

		assertEquals("Не задана книга для сохранения", exception.getMessage());
	}

	@Test
	void shouldThrowLibraryDaoExceptionWhenGenreIsNull() {
		LibraryDaoException exception = Assertions.assertThrows(LibraryDaoException.class, () -> {
			libraryDao.isExist(null);
		});

		assertEquals("Не задан литературный жанр", exception.getMessage());
	}
}