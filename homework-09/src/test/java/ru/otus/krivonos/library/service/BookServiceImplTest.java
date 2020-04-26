package ru.otus.krivonos.library.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.krivonos.library.dao.AuthorRepository;
import ru.otus.krivonos.library.dao.BookRepository;
import ru.otus.krivonos.library.dao.GenreRepository;
import ru.otus.krivonos.library.exception.BookServiceException;
import ru.otus.krivonos.library.model.Author;
import ru.otus.krivonos.library.model.Book;
import ru.otus.krivonos.library.model.Genre;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class BookServiceImplTest {
	@Mock
	private BookRepository bookRepository;
	@Mock
	private GenreRepository genreRepository;
	@Mock
	private AuthorRepository authorRepository;
	@InjectMocks
	private BookServiceImpl bookService;

	@Test
	void shouldThrowServiceExceptionWhenSaveBookAndBookTitleIsNull() {
		BookServiceException exception = Assertions.assertThrows(BookServiceException.class, () -> {
			bookService.createBook(null, "test_author_name'", "test_genre");
		});

		assertEquals("Не задано название книги", exception.getInfo());
	}

	@Test
	void shouldThrowServiceExceptionWhenSaveBookAndBookTitleIsEmpty() {
		BookServiceException exception = Assertions.assertThrows(BookServiceException.class, () -> {
			bookService.createBook("", "test_author_name'", "test_genre");
		});

		assertEquals("Не задано название книги", exception.getInfo());
	}

	@Test
	void shouldThrowServiceExceptionWhenSaveBookAndAuthorIsEmpty() {
		BookServiceException exception = Assertions.assertThrows(BookServiceException.class, () -> {
			bookService.createBook("test_title", "", "test_genre");
		});

		assertEquals("Не задан автор книги", exception.getInfo());
	}

	@Test
	void shouldThrowServiceExceptionWhenSaveBookAndAuthorIsNull() {
		BookServiceException exception = Assertions.assertThrows(BookServiceException.class, () -> {
			bookService.createBook("test_title", "", "test_genre");
		});

		assertEquals("Не задан автор книги", exception.getInfo());
	}

	@Test
	void shouldThrowServiceExceptionWhenSaveBookAndGenreIsNotExist() {
		when(genreRepository.findByType("test_genre")).thenReturn(Optional.empty());
		BookServiceException exception = Assertions.assertThrows(BookServiceException.class, () -> {
			bookService.createBook("test_title", "test_author", "test_genre");
		});

		assertEquals("Отсутствует литературный жанр 'test_genre'", exception.getInfo());
	}

	@Test
	void shouldСallDaoMethodToSaveBook() {
		when(genreRepository.findByType("test_genre")).thenReturn(Optional.of(new Genre("test_genre")));
		bookService.createBook("test_title", "test_author", "test_genre");

		verify(bookRepository, times(1)).save(any());
	}

	@Test
	void shouldThrowServiceExceptionWhenFindBookByIdWhichIsNotExist() {
		when(bookRepository.findById(1l)).thenReturn(Optional.empty());
		BookServiceException exception = Assertions.assertThrows(BookServiceException.class, () -> {
			bookService.findBookBy(1l);
		});

		assertEquals("Книга с id='1' в библиотеке не найдена", exception.getMessage());
	}

	@Test
	void shouldReturnBookById() throws Exception {
		Book expectedBook = new Book("title", new Author("author"), new Genre("genre"));
		Optional<Book> optionalBook = Optional.of(expectedBook);
		when(bookRepository.findById(1l)).thenReturn(optionalBook);

		Book actualBook = bookService.findBookBy(1l);

		assertThat(expectedBook).isEqualToComparingFieldByField(actualBook);
	}

	@Test
	void shouldCallFindAllBooksDaoMethod() throws Exception {
		bookService.findAllBooks();

		verify(bookRepository, times(1)).findAll();
	}

	@Test
	void shouldThrowServiceExceptionWhenUpdateBookAndBookTitleIsEmpty() {
		BookServiceException exception = Assertions.assertThrows(BookServiceException.class, () -> {
			bookService.createBook(1l, "", 1l, "", 1l);
		});

		assertEquals("Не задано название книги", exception.getInfo());
	}

	@Test
	void shouldThrowServiceExceptionWhenUpdateBookAndBookTitleIsNull() {
		BookServiceException exception = Assertions.assertThrows(BookServiceException.class, () -> {
			bookService.createBook(1l, null, 1l, "", 1l);
		});

		assertEquals("Не задано название книги", exception.getInfo());
	}

	@Test
	void shouldThrowServiceExceptionWhenUpdateBookAndAuthorNameIsEmpty() {
		BookServiceException exception = Assertions.assertThrows(BookServiceException.class, () -> {
			bookService.createBook(1l, "book_title", 0, "", 1l);
		});

		assertEquals("Не задан автор книги", exception.getInfo());
	}

	@Test
	void shouldThrowServiceExceptionWhenUpdateBookAndAuthorNameIsNull() {
		BookServiceException exception = Assertions.assertThrows(BookServiceException.class, () -> {
			bookService.createBook(1l, "book_title", 0, null, 1l);
		});

		assertEquals("Не задан автор книги", exception.getInfo());
	}

	@Test
	void shouldThrowServiceExceptionWhenUpdateBookAndGenreIsNotExist() throws Exception {
		when(genreRepository.findById(1l)).thenReturn(Optional.empty());
		when(authorRepository.findById(1l)).thenReturn(Optional.of(new Author("test_author")));
		BookServiceException exception = Assertions.assertThrows(BookServiceException.class, () -> {
			bookService.createBook(1l, "test_title", 1l, "test_author", 1l);
		});

		assertEquals("Отсутствует литературный жанр с id=1", exception.getInfo());
	}

	@Test
	void shouldСallDaoMethodToUpdateBook() throws Exception {
		when(genreRepository.findById(1l)).thenReturn(Optional.of(new Genre("test_genre_type")));
		bookService.createBook(1l, "test_title", 0, "test_author", 1l);

		verify(bookRepository, times(1)).save(any());
	}

	@Test
	void shouldThrowServiceExceptionWhenDeleteBookWhichIsNotExist() {
		when(bookRepository.findById(1l)).thenReturn(Optional.empty());
		BookServiceException exception = Assertions.assertThrows(BookServiceException.class, () -> {
			bookService.deleteBookBy(1l);
		});

		assertEquals("Книга с id='1' в библиотеке не найдена", exception.getInfo());
	}

	@Test
	void shouldСallDeleteBookByDaoMethod() throws Exception {
		Book bookMock = mock(Book.class);
		when(bookRepository.findById(1l)).thenReturn(Optional.of(bookMock));
		bookService.deleteBookBy(1l);

		verify(bookRepository, times(1)).delete(bookMock);
	}
}