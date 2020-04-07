package ru.otus.krivonos.library.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.krivonos.library.model.Author;
import ru.otus.krivonos.library.model.Book;
import ru.otus.krivonos.library.model.Genre;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
		List<Book> books = bookDao.findAll();

		assertThat(books)
			.hasSize(7)
			.doesNotHaveDuplicates();
	}

	@Test
	void shouldReturnBookById() throws Exception {
		Optional<Book> optionalBook = bookDao.findBy(2l);

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

		bookDao.deleteBy(2l);

		Book actualBook = em.find(Book.class, 2l);
		assertThat(actualBook).isNull();
	}

	@Test
	void shouldReturnEmptyOptionalBookWhenNotExist() throws Exception {
		Optional<Book> optionalBook = bookDao.findBy(-2l);

		assertThat(optionalBook).isNotPresent();
	}

	@Test
	void shouldPersistBookWhenAuthorExist() throws Exception {
		Author author = em.find(Author.class, 1l);
		Genre genre = em.find(Genre.class, 1l);
		String bookTitle = "Евгений Онегин";
		Book expectedBook = new Book(bookTitle, author, genre);

		bookDao.save(expectedBook);

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
	void shouldPersistBookWhenAuthorNotExist() throws Exception {
		Author author = new Author("Михаил Лермонтов");
		Genre genre = em.find(Genre.class, 1l);
		String bookTitle = "Смерть поэта";
		Book expectedBook = new Book(bookTitle, author, genre);

		bookDao.save(expectedBook);

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
	void shouldNotDeleteBookWhenBookIsNotExist() throws Exception {
		int count = bookDao.findAll().size();

		bookDao.deleteBy(-2l);

		assertEquals(count, bookDao.findAll().size());
	}

	@Test
	void shouldUpdateBookWhenAuthorNotExist() throws Exception {
		Author author = new Author("Михаил Лермонтов");
		Genre genre = em.find(Genre.class, 1l);
		String bookTitle = "Смерть поэта";
		Book expectedBook = new Book(1, bookTitle, author, genre);

		bookDao.save(expectedBook);

		em.detach(expectedBook);
		Book actualBook = em.find(Book.class, expectedBook.getId());
		assertThat(actualBook)
			.isNotNull()
			.matches(book -> book.getTitle().equals("Смерть поэта"))
			.matches(book -> book.getAuthor() != null && "Михаил Лермонтов".equals(book.getAuthor().getName()))
			.matches(book -> book.getGenre() != null && "Классическая проза".equals(book.getGenre().getType()));
	}

	@Test
	void shouldUpdateBookWhenAuthorExist() throws Exception {
		Author author = em.find(Author.class, 1l);
		Genre genre = em.find(Genre.class, 1l);
		String bookTitle = "Смерть поэта";
		Book expectedBook = new Book(1, bookTitle, author, genre);

		bookDao.save(expectedBook);

		em.detach(expectedBook);
		Book actualBook = em.find(Book.class, expectedBook.getId());
		assertThat(actualBook)
			.isNotNull()
			.matches(book -> book.getTitle().equals("Смерть поэта"))
			.matches(book -> book.getAuthor() != null && "Александр Пушкин".equals(book.getAuthor().getName()))
			.matches(book -> book.getGenre() != null && "Классическая проза".equals(book.getGenre().getType()));
	}
}