package ru.otus.krivonos.library.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.krivonos.library.model.Author;
import ru.otus.krivonos.library.model.Book;
import ru.otus.krivonos.library.model.Genre;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Transactional
class DbBookRepositoryTest {
	@Autowired
	private BookRepository bookRepository;
	@Autowired
	private TestEntityManager em;

	@Test
	void shouldReturnAllBooks() {
		List<Book> books = bookRepository.findAll();

		assertThat(books)
			.hasSize(7)
			.doesNotHaveDuplicates();
	}

	@Test
	void shouldReturnBookById() {
		Optional<Book> optionalBook = bookRepository.findById(2l);

		assertThat(optionalBook).isNotEmpty();
		assertThat(optionalBook).isPresent().get()
			.matches(book -> book.getTitle().equals("Евгений Онегин"))
			.matches(book -> book.getAuthor().getName().equals("Александр Пушкин"))
			.matches(book -> book.getGenre().getType().equals("Классическая проза"))
			.matches(book -> book.getComments() != null && book.getComments().size() == 3);
	}

	@Test
	void shouldDeleteBook() {
		Book expectedBook = em.find(Book.class, 2l);
		assertThat(expectedBook).isNotNull();

		bookRepository.delete(expectedBook);

		em.flush();
		em.detach(expectedBook);
		Book actualBook = em.find(Book.class, 2l);
		assertThat(actualBook).isNull();
	}

	@Test
	void shouldReturnEmptyOptionalBookWhenNotExist() {
		Optional<Book> optionalBook = bookRepository.findById(-2l);

		assertThat(optionalBook).isNotPresent();
	}

	@Test
	void shouldPersistBookWhenAuthorExist() {
		Author author = em.find(Author.class, 1l);
		Genre genre = em.find(Genre.class, 1l);
		String bookTitle = "Евгений Онегин";
		Book expectedBook = new Book(bookTitle, author, genre);

		bookRepository.save(expectedBook);

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
	void shouldPersistBookWhenAuthorNotExist() {
		Author author = new Author("Михаил Лермонтов");
		Genre genre = em.find(Genre.class, 1l);
		String bookTitle = "Смерть поэта";
		Book expectedBook = new Book(bookTitle, author, genre);

		bookRepository.save(expectedBook);

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
	void shouldUpdateBookWhenAuthorNotExist() {
		Author author = new Author("Михаил Лермонтов");
		Genre genre = em.find(Genre.class, 1l);
		String bookTitle = "Смерть поэта";
		Book expectedBook = new Book(1, bookTitle, author, genre);

		bookRepository.save(expectedBook);

		em.detach(expectedBook);
		Book actualBook = em.find(Book.class, expectedBook.getId());
		assertThat(actualBook)
			.isNotNull()
			.matches(book -> book.getTitle().equals("Смерть поэта"))
			.matches(book -> book.getAuthor() != null && "Михаил Лермонтов".equals(book.getAuthor().getName()))
			.matches(book -> book.getGenre() != null && "Классическая проза".equals(book.getGenre().getType()));
	}

	@Test
	void shouldUpdateBookWhenAuthorExist() {
		Author author = em.find(Author.class, 1l);
		Genre genre = em.find(Genre.class, 1l);
		String bookTitle = "Смерть поэта";
		Book expectedBook = new Book(1, bookTitle, author, genre);

		bookRepository.save(expectedBook);

		em.detach(expectedBook);
		Book actualBook = em.find(Book.class, expectedBook.getId());
		assertThat(actualBook)
			.isNotNull()
			.matches(book -> book.getTitle().equals("Смерть поэта"))
			.matches(book -> book.getAuthor() != null && "Александр Пушкин".equals(book.getAuthor().getName()))
			.matches(book -> book.getGenre() != null && "Классическая проза".equals(book.getGenre().getType()));
	}
}