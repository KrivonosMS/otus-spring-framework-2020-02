package ru.otus.krivonos.library.dao;

import com.github.mongobee.Mongobee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import ru.otus.krivonos.library.bee.MongoBeeConfig;
import ru.otus.krivonos.library.model.Author;
import ru.otus.krivonos.library.model.Book;
import ru.otus.krivonos.library.model.Genre;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@Import(MongoBeeConfig.class)
class DbBookRepositoryTest {
	@Autowired
	private BookRepository bookRepository;
	@Autowired
	private GenreRepository genreRepository;
	@Autowired
	private Mongobee mongobee;

	@BeforeEach
	void init() throws Exception {
		mongobee.execute();
	}

	@Test
	void shouldReturnAllBooks() {
		List<Book> books = bookRepository.findAll();

		assertThat(books)
			.hasSize(7)
			.doesNotHaveDuplicates();
	}

	@Test
	void shouldReturnBookById() {
		Optional<Book> optionalBook = bookRepository.findById("2");

		System.out.println(optionalBook.get());

		assertThat(optionalBook).isNotEmpty();
		assertThat(optionalBook).isPresent().get()
			.matches(book -> book.getTitle().equals("Евгений Онегин"))
			.matches(book -> book.getAuthor().getName().equals("Александр Пушкин"))
			.matches(book -> book.getGenre().getType().equals("Классическая проза"))
			.matches(book -> book.getComments() != null && book.getComments().size() == 3);
	}

	@Test
	void shouldDeleteBook() {
		Optional<Book> optionalExpectedBook = bookRepository.findById("2");
		assertThat(optionalExpectedBook).isPresent();
		Book expectedBook = optionalExpectedBook.get();

		bookRepository.delete(expectedBook);

		Optional<Book> actualBook = bookRepository.findById("2");
		assertThat(actualBook).isNotPresent();
	}

	@Test
	void shouldReturnEmptyOptionalBookWhenNotExist() {
		Optional<Book> optionalBook = bookRepository.findById("-2");

		assertThat(optionalBook).isNotPresent();
	}

	@Test
	void shouldPersistBookWhen() {
		Author author = new Author("Александр Пушкин");
		Optional<Genre> optionalGenre = genreRepository.findById("1");
		assertThat(optionalGenre).isPresent();
		Genre genre = optionalGenre.get();
		String bookTitle = "Евгений Онегин";
		Book expectedBook = new Book(bookTitle, author, genre);

		bookRepository.save(expectedBook);

		Optional<Book> optionalActualBook = bookRepository.findById(expectedBook.getId());
		assertThat(optionalActualBook)
			.isPresent()
			.get()
			.matches(book -> book.getTitle().equals("Евгений Онегин"))
			.matches(book -> book.getAuthor() != null && "Александр Пушкин".equals(book.getAuthor().getName()))
			.matches(book -> book.getGenre() != null && "Классическая проза".equals(book.getGenre().getType()));
	}

	@Test
	void shouldUpdateBookWhenAuthorExist() {
		Author author = new Author("Александр Пушкин");
		Optional<Genre> optionalGenre = genreRepository.findById("1");
		assertThat(optionalGenre).isPresent();
		Genre genre = optionalGenre.get();
		String bookTitle = "Смерть поэта";
		Book expectedBook = new Book("1", bookTitle, author, genre);

		bookRepository.save(expectedBook);

		Optional<Book> optionalActualBook = bookRepository.findById(expectedBook.getId());
		assertThat(optionalActualBook)
			.isPresent()
			.get()
			.matches(book -> book.getTitle().equals("Смерть поэта"))
			.matches(book -> book.getAuthor() != null && "Александр Пушкин".equals(book.getAuthor().getName()))
			.matches(book -> book.getGenre() != null && "Классическая проза".equals(book.getGenre().getType()));
	}
}