package ru.otus.krivonos.library.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.krivonos.library.domain.Author;
import ru.otus.krivonos.library.domain.Book;
import ru.otus.krivonos.library.domain.Genre;
import ru.otus.krivonos.library.exception.BookDaoException;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import(DbBookDao.class)
class DbBookDaoTest {
	@Autowired
	private BookDao bookDao;
	@Autowired
	private TestEntityManager em;

	@Test
	void shouldReturnAllBooks() throws Exception {
		List<Book> books = bookDao.findAllBooks();

		assertThat(books)
			.hasSize(7)
			.doesNotHaveDuplicates();
	}

	@Test
	void shouldReturnBookById() throws Exception {
		Optional<Book> optionalBook = bookDao.findBookBy(2l);

		assertThat(optionalBook).isNotEmpty();
		assertThat(optionalBook).isPresent().get()
			.matches(book -> book.getTitle().equals("Евгений Онегин"))
			.matches(book -> book.getAuthor().getName().equals("Александр Пушкин"))
			.matches(book -> book.getGenre().getType().equals("Классическая проза"))
			.matches(book -> book.getComments() != null && book.getComments().size() == 3);
	}

	@Test
	void shouldDeleteBookById() throws Exception {
		Book expectedBook = em.find(Book.class, 2l);
		assertThat(expectedBook).isNotNull();
		em.detach(expectedBook);

		bookDao.deleteBookBy(2l);

		Book actualBook = em.find(Book.class, 2l);
		assertThat(actualBook).isNull();
	}

	@Test
	void shouldReturnEmptyOptionalBookWhenNotExist() throws Exception {
		Optional<Book> optionalBook = bookDao.findBookBy(-2l);

		assertThat(optionalBook).isNotPresent();
	}

	@Test
	void shouldSaveBookWhenAuthorExist() throws Exception {
		Author author = new Author("Александр Пушкин");
		Genre genre = new Genre("Классическая проза");
		String bookTitle = "Евгений Онегин";
		Book expectedBook = new Book(bookTitle, author, genre);

		bookDao.saveBook(expectedBook);

		assertThat(expectedBook.getId()).isGreaterThan(0);
		em.detach(expectedBook);
		Book actualBook = em.find(Book.class, expectedBook.getId());
		assertThat(actualBook)
			.isNotNull()
			.matches(book -> book.getTitle().equals("Евгений Онегин"))
			.matches(book -> book.getAuthor() != null && "Александр Пушкин".equals(book.getAuthor().getName()))
			.matches(book -> book.getGenre() != null && "Классическая проза".equals(book.getGenre().getType()));
	}

	@Test
	void shouldSaveBookWhenAuthorNotExist() throws Exception {
		Author author = new Author("Михаил Лермонтов");
		Genre genre = new Genre("Классическая проза");
		String bookTitle = "Смерть поэта";
		Book expectedBook = new Book(bookTitle, author, genre);

		bookDao.saveBook(expectedBook);

		assertThat(expectedBook.getId()).isGreaterThan(0);
		em.detach(expectedBook);
		Book actualBook = em.find(Book.class, expectedBook.getId());
		assertThat(actualBook)
			.isNotNull()
			.matches(book -> book.getTitle().equals("Смерть поэта"))
			.matches(book -> book.getAuthor() != null && book.getAuthor().getName().equals("Михаил Лермонтов"))
			.matches(book -> book.getGenre() != null && book.getGenre().getType().equals("Классическая проза"));
	}

	@Test
	void shouldThrowLibraryDaoExceptionSaveBookWhenGenreIsNotExist() throws Exception {
		Author author = new Author("Михаил Лермонтов");
		Genre genre = new Genre("Техническая литература");
		String bookTitle = "Смерть поэта";
		Book book = new Book(bookTitle, author, genre);

		BookDaoException exception = Assertions.assertThrows(BookDaoException.class, () -> {
			bookDao.saveBook(book);
		});

		assertEquals("Литературный жанр 'Техническая литература' отсутствует", exception.getMessage());
	}

	@Test
	void shouldNotDeleteBookWhenBookIsNotExist() throws Exception {
		int count = bookDao.findAllBooks().size();

		bookDao.deleteBookBy(-2l);

		assertEquals(count, bookDao.findAllBooks().size());
	}

	@Test
	void shouldThrowLibraryDaoExceptionWhenBookIsNull() {
		BookDaoException exception = Assertions.assertThrows(BookDaoException.class, () -> {
			bookDao.saveBook(null);
		});

		assertEquals("Не задана книга для сохранения", exception.getMessage());
	}

	@Test
	void shouldUpdateBook() throws Exception {
		Author author = new Author("Михаил Лермонтов");
		Genre genre = new Genre("Классическая проза");
		String bookTitle = "Смерть поэта";
		Book expectedBook = new Book(1, bookTitle, author, genre);

		bookDao.saveBook(expectedBook);

		em.detach(expectedBook);
		Book actualBook = em.find(Book.class, expectedBook.getId());
		assertThat(actualBook)
			.isNotNull()
			.matches(book -> book.getTitle().equals("Смерть поэта"))
			.matches(book -> book.getAuthor() != null && "Михаил Лермонтов".equals(book.getAuthor().getName()))
			.matches(book -> book.getGenre() != null && "Классическая проза".equals(book.getGenre().getType()));
	}
}