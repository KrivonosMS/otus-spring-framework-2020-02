package ru.otus.krivonos.library.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import ru.otus.krivonos.library.dao.BookRepository;
import ru.otus.krivonos.library.dao.GenreRepository;
import ru.otus.krivonos.library.exception.BookServiceException;
import ru.otus.krivonos.library.model.Genre;

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
	void shouldThrowServiceExceptionWhenCreateBookAndBookTitleIsNull() {
		BookServiceException exception = Assertions.assertThrows(BookServiceException.class, () -> {
			bookService.createBook(null, "test_author_name", "1", "genre_type");
		});

		assertEquals("Не задано название книги", exception.getInfo());
	}

	@Test
	void shouldThrowServiceExceptionWhenCreateBokAndBookTitleIsEmpty() {
		BookServiceException exception = Assertions.assertThrows(BookServiceException.class, () -> {
			bookService.createBook("", "test_author_name", "1", "genre_type");
		});

		assertEquals("Не задано название книги", exception.getInfo());
	}

	@Test
	void shouldThrowServiceExceptionWhenCreateBookAndAuthorIsEmpty() {
		BookServiceException exception = Assertions.assertThrows(BookServiceException.class, () -> {
			bookService.createBook("test_title", "", "1", "genre_type");
		});

		assertEquals("Не задан автор книги", exception.getInfo());
	}

	@Test
	void shouldThrowServiceExceptionWhenCreateBookAndAuthorIsNull() {
		BookServiceException exception = Assertions.assertThrows(BookServiceException.class, () -> {
			bookService.createBook("test_title", "", "1", "genre_type");
		});

		assertEquals("Не задан автор книги", exception.getInfo());
	}

	@Test
	void shouldСallDaoMethodToCreateBook() {
		when(genreRepository.findById("1")).thenReturn(Mono.just(new Genre("test_genre")));
		bookService.createBook("test_title", "test_author", "1", "genre_type");

		verify(bookRepository, times(1)).save(any());
	}

	@Test
	void shouldThrowServiceExceptionWhenUpdateBookAndBookTitleIsEmpty() {
		BookServiceException exception = Assertions.assertThrows(BookServiceException.class, () -> {
			bookService.updateBook("1", "", "author_name", "1", "genreType");
		});

		assertEquals("Не задано название книги", exception.getInfo());
	}

	@Test
	void shouldThrowServiceExceptionWhenUpdateBookAndBookTitleIsNull() {
		BookServiceException exception = Assertions.assertThrows(BookServiceException.class, () -> {
			bookService.updateBook("1", null, "author_name", "", "genre");
		});

		assertEquals("Не задано название книги", exception.getInfo());
	}

	@Test
	void shouldThrowServiceExceptionWhenUpdateBookAndAuthorNameIsEmpty() {
		BookServiceException exception = Assertions.assertThrows(BookServiceException.class, () -> {
			bookService.updateBook("1", "title", "", "1", "genre");
		});

		assertEquals("Не задан автор книги", exception.getInfo());
	}

	@Test
	void shouldThrowServiceExceptionWhenUpdateBookAndAuthorNameIsNull() {
		BookServiceException exception = Assertions.assertThrows(BookServiceException.class, () -> {
			bookService.updateBook("1", "title", null, "1", "genre");
		});

		assertEquals("Не задан автор книги", exception.getInfo());
	}

	@Test
	void shouldСallDaoMethodToUpdateBook() {
		when(genreRepository.findById("1")).thenReturn(Mono.just(new Genre("test_genre_type")));
		bookService.updateBook("1", "test_title", "test_author", "1", "type");

		verify(bookRepository, times(1)).save(any());
	}
}