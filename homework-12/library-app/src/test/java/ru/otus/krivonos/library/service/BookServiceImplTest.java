package ru.otus.krivonos.library.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
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
		BookServiceException exception = Assertions.assertThrows(BookServiceException.class,
			() -> bookService.createBook(null, "test_author_name'", 1)
		);

		assertEquals("Не задано название книги", exception.getInfo());
	}

	@Test
	void shouldThrowServiceExceptionWhenSaveBookAndBookTitleIsEmpty() {
		BookServiceException exception = Assertions.assertThrows(BookServiceException.class,
			() -> bookService.createBook("", "test_author_name'", 1)
		);

		assertEquals("Не задано название книги", exception.getInfo());
	}

	@Test
	void shouldThrowServiceExceptionWhenSaveBookAndAuthorIsEmpty() {
		BookServiceException exception = Assertions.assertThrows(BookServiceException.class,
			() -> bookService.createBook("test_title", "", 1)
		);

		assertEquals("Не задан автор книги", exception.getInfo());
	}

	@Test
	void shouldThrowServiceExceptionWhenSaveBookAndGenreIsNotExist() {
		when(genreRepository.findById(1L)).thenReturn(Optional.empty());
		BookServiceException exception = Assertions.assertThrows(BookServiceException.class,
			() -> bookService.createBook("test_title", "test_author", 1));

		assertEquals("Отсутствует литературный жанр c Id=1", exception.getInfo());
	}

	@Test
	void shouldСallDaoMethodToSaveBook() {
		when(genreRepository.findById(1L)).thenReturn(Optional.of(new Genre("test_genre")));
		bookService.createBook("test_title", "test_author", 1);

		verify(bookRepository, times(1)).save(any());
	}

	@Test
	void shouldThrowServiceExceptionWhenFindBookByIdWhichIsNotExist() {
		when(bookRepository.findById(1L)).thenReturn(Optional.empty());
		BookServiceException exception = Assertions.assertThrows(BookServiceException.class,
			() -> bookService.findBookBy(1L)
		);

		assertEquals("Книга с id='1' в библиотеке не найдена", exception.getMessage());
	}

	@Test
	void shouldReturnBookById() {
		Book expectedBook = new Book("title", new Author("author"), new Genre("genre"));
		Optional<Book> optionalBook = Optional.of(expectedBook);
		when(bookRepository.findById(1L)).thenReturn(optionalBook);

		Book actualBook = bookService.findBookBy(1L);

		assertThat(expectedBook).isEqualToComparingFieldByField(actualBook);
	}

	@Test
	void shouldCallFindAllBooksDaoMethod() {
		bookService.findAllBooks();

		verify(bookRepository, times(1)).findAll();
	}

	@Test
	void shouldThrowServiceExceptionWhenUpdateBookAndBookTitleIsEmpty() {
		BookServiceException exception = Assertions.assertThrows(BookServiceException.class,
			() -> bookService.updateBook(1, "", "", 1)
		);

		assertEquals("Не задано название книги", exception.getInfo());
	}

	@Test
	void shouldThrowServiceExceptionWhenUpdateBookAndBookTitleIsNull() {
		BookServiceException exception = Assertions.assertThrows(BookServiceException.class,
			() -> bookService.updateBook(1, null, "", 1)
		);

		assertEquals("Не задано название книги", exception.getInfo());
	}

	@Test
	void shouldThrowServiceExceptionWhenUpdateBookAndAuthorNameIsEmpty() {
		BookServiceException exception = Assertions.assertThrows(BookServiceException.class,
			() -> bookService.updateBook(1, "book_title", "", 1)
		);

		assertEquals("Не задан автор книги", exception.getInfo());
	}

	@Test
	void shouldThrowServiceExceptionWhenUpdateBookAndAuthorNameIsNull() {
		BookServiceException exception = Assertions.assertThrows(BookServiceException.class,
			() -> bookService.updateBook(1, "book_title", null, 1)
		);

		assertEquals("Не задан автор книги", exception.getInfo());
	}

	@Test
	void shouldThrowServiceExceptionWhenUpdateBookAndGenreIsNotExist() {
		when(genreRepository.findById(1L)).thenReturn(Optional.empty());
		when(authorRepository.findById(1L)).thenReturn(Optional.of(new Author("test_author")));
		BookServiceException exception = Assertions.assertThrows(BookServiceException.class,
			() -> bookService.updateBook(1, "test_title", "test_author", 1)
		);

		assertEquals("Отсутствует литературный жанр c Id=1", exception.getInfo());
	}

	@Test
	void shouldThrowServiceExceptionWhenUpdateBookAndBookIsNotExist() {
		when(genreRepository.findById(1L)).thenReturn(Optional.of(new Genre("test_genre_type")));
		when(bookRepository.findById(2L)).thenReturn(Optional.empty());
		BookServiceException exception = Assertions.assertThrows(BookServiceException.class,
			() -> bookService.updateBook(1, "test_title", "test_author", 1)
		);

		assertEquals("Отсутствует книга c Id=1", exception.getInfo());
	}

	@Test
	void shouldСallDaoMethodToUpdateBook() {
		Book book = mock(Book.class);
		when(genreRepository.findById(1L)).thenReturn(Optional.of(new Genre("test_genre_type")));
		when(bookRepository.findById(2L)).thenReturn(Optional.of(book));
		bookService.updateBook(2, "test_title", "test_author", 1);

		verify(bookRepository, times(1)).save(any());
	}

	@Test
	void shouldThrowServiceExceptionWhenDeleteBookWhichIsNotExist() {
		when(bookRepository.findById(1L)).thenReturn(Optional.empty());
		BookServiceException exception = Assertions.assertThrows(BookServiceException.class,
			() -> bookService.deleteBookBy(1L)
		);

		assertEquals("Книга с id='1' в библиотеке не найдена", exception.getInfo());
	}

	@Test
	void shouldСallDeleteBookByDaoMethod() {
		Book bookMock = mock(Book.class);
		when(bookRepository.findById(1L)).thenReturn(Optional.of(bookMock));
		bookService.deleteBookBy(1L);

		verify(bookRepository, times(1)).delete(bookMock);
	}
}