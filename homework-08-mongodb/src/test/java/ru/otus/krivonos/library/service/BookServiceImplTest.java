package ru.otus.krivonos.library.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
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
	@InjectMocks
	private BookServiceImpl bookService;

	@Test
	void shouldThrowServiceExceptionWhenSaveBookAndBookTitleIsNull() {
		BookServiceException exception = Assertions.assertThrows(BookServiceException.class, () -> {
			bookService.saveBook(null, "test_author_name'", "1");
		});

		assertEquals("Не задано название книги", exception.getInfo());
	}

	@Test
	void shouldThrowServiceExceptionWhenSaveBookAndBookTitleIsEmpty() {
		BookServiceException exception = Assertions.assertThrows(BookServiceException.class, () -> {
			bookService.saveBook("", "test_author_name'", "1");
		});

		assertEquals("Не задано название книги", exception.getInfo());
	}

	@Test
	void shouldThrowServiceExceptionWhenSaveBookAndAuthorIsEmpty() {
		BookServiceException exception = Assertions.assertThrows(BookServiceException.class, () -> {
			bookService.saveBook("test_title", "", "1");
		});

		assertEquals("Не задан автор книги", exception.getInfo());
	}

	@Test
	void shouldThrowServiceExceptionWhenSaveBookAndAuthorIsNull() {
		BookServiceException exception = Assertions.assertThrows(BookServiceException.class, () -> {
			bookService.saveBook("test_title", "", "1");
		});

		assertEquals("Не задан автор книги", exception.getInfo());
	}

	@Test
	void shouldСallDaoMethodToSaveBook() throws Exception {
		when(genreRepository.findById("1")).thenReturn(Optional.of(new Genre("test_genre")));
		bookService.saveBook("test_title", "test_author", "1");

		verify(bookRepository, times(1)).save(any());
	}

	@Test
	void shouldThrowServiceExceptionWhenFindBookByIdWhichIsNotExist() {
		when(bookRepository.findById("1")).thenReturn(Optional.empty());
		BookServiceException exception = Assertions.assertThrows(BookServiceException.class, () -> {
			bookService.findBookBy("1");
		});

		assertEquals("Книга с id='1' в библиотеке не найдена", exception.getMessage());
	}

	@Test
	void shouldReturnBookById() throws Exception {
		Book expectedBook = new Book("title", new Author("author"), new Genre("genre"));
		Optional<Book> optionalBook = Optional.of(expectedBook);
		when(bookRepository.findById("1")).thenReturn(optionalBook);

		Book actualBook = bookService.findBookBy("1");

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
			bookService.saveBook("1", "", "", "1");
		});

		assertEquals("Не задано название книги", exception.getInfo());
	}

	@Test
	void shouldThrowServiceExceptionWhenUpdateBookAndBookTitleIsNull() {
		BookServiceException exception = Assertions.assertThrows(BookServiceException.class, () -> {
			bookService.saveBook("1", null, "", "1");
		});

		assertEquals("Не задано название книги", exception.getInfo());
	}

	@Test
	void shouldThrowServiceExceptionWhenUpdateBookAndAuthorNameIsEmpty() {
		BookServiceException exception = Assertions.assertThrows(BookServiceException.class, () -> {
			bookService.saveBook("1", "book_title", "", "1");
		});

		assertEquals("Не задан автор книги", exception.getInfo());
	}

	@Test
	void shouldThrowServiceExceptionWhenUpdateBookAndAuthorNameIsNull() {
		BookServiceException exception = Assertions.assertThrows(BookServiceException.class, () -> {
			bookService.saveBook("1", "book_title", null, "1");
		});

		assertEquals("Не задан автор книги", exception.getInfo());
	}

	@Test
	void shouldThrowServiceExceptionWhenUpdateBookAndGenreIsNotExist() throws Exception {
		when(genreRepository.findById("1")).thenReturn(Optional.empty());
		BookServiceException exception = Assertions.assertThrows(BookServiceException.class, () -> {
			bookService.saveBook("1", "test_title", "test_author", "1");
		});

		assertEquals("Отсутствует литературный жанр с id=1", exception.getInfo());
	}

	@Test
	void shouldСallDaoMethodToUpdateBook() throws Exception {
		when(genreRepository.findById("1")).thenReturn(Optional.of(new Genre("test_genre_type")));
		bookService.saveBook("1", "test_title", "test_author", "1");

		verify(bookRepository, times(1)).save(any());
	}

	@Test
	void shouldThrowServiceExceptionWhenDeleteBookWhichIsNotExist() {
		when(bookRepository.findById("1")).thenReturn(Optional.empty());
		BookServiceException exception = Assertions.assertThrows(BookServiceException.class, () -> {
			bookService.deleteBookBy("1");
		});

		assertEquals("Книга с id='1' в библиотеке не найдена", exception.getInfo());
	}

	@Test
	void shouldСallDeleteBookByDaoMethod() throws Exception {
		Book bookMock = mock(Book.class);
		when(bookRepository.findById("1")).thenReturn(Optional.of(bookMock));
		bookService.deleteBookBy("1");

		verify(bookRepository, times(1)).delete(bookMock);
	}
}