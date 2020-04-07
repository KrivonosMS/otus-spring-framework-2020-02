package ru.otus.krivonos.library.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.krivonos.library.dao.AuthorDao;
import ru.otus.krivonos.library.dao.BookDao;
import ru.otus.krivonos.library.dao.GenreDao;
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
	private BookDao bookDao;
	@Mock
	private GenreDao genreDao;
	@Mock
	private AuthorDao authorDao;
	@InjectMocks
	private BookServiceImpl libraryService;

	@Test
	void shouldThrowServiceExceptionWhenSaveBookAndBookTitleIsNull() {
		BookServiceException exception = Assertions.assertThrows(BookServiceException.class, () -> {
			libraryService.saveBook(null, 0, "test_author_name'", 1);
		});

		assertEquals("Не задано название книги", exception.getInfo());
	}

	@Test
	void shouldThrowServiceExceptionWhenSaveBookAndBookTitleIsEmpty() {
		BookServiceException exception = Assertions.assertThrows(BookServiceException.class, () -> {
			libraryService.saveBook("", 0, "test_author_name'", 1);
		});

		assertEquals("Не задано название книги", exception.getInfo());
	}

	@Test
	void shouldThrowServiceExceptionWhenSaveBookAndAuthorIsEmpty() {
		BookServiceException exception = Assertions.assertThrows(BookServiceException.class, () -> {
			libraryService.saveBook("test_title", 0, "", 1);
		});

		assertEquals("Не задан автор книги", exception.getInfo());
	}

	@Test
	void shouldThrowServiceExceptionWhenSaveBookAndAuthorIsNull() {
		BookServiceException exception = Assertions.assertThrows(BookServiceException.class, () -> {
			libraryService.saveBook("test_title", 0, "", 1);
		});

		assertEquals("Не задан автор книги", exception.getInfo());
	}

	@Test
	void shouldThrowServiceExceptionWhenSaveBookAndGenreIsNotExist() throws Exception {
		when(genreDao.isExist(any())).thenReturn(false);
		BookServiceException exception = Assertions.assertThrows(BookServiceException.class, () -> {
			libraryService.saveBook("test_title", 1, "test_author", -1);
		});

		assertEquals("Отсутствует автор с id=1", exception.getInfo());
	}

	@Test
	void shouldСallDaoMethodToSaveBook() throws Exception {
		when(genreDao.findBy(1l)).thenReturn(Optional.of(new Genre("test_genre")));
		libraryService.saveBook("test_title", 0, "test_author", 1l);

		verify(bookDao, times(1)).save(any());
	}

	@Test
	void shouldThrowServiceExceptionWhenFindBookByIdWhichIsNotExist() throws Exception {
		when(bookDao.findBy(1l)).thenReturn(Optional.empty());
		BookServiceException exception = Assertions.assertThrows(BookServiceException.class, () -> {
			libraryService.findBookBy(1l);
		});

		assertEquals("Книга с id='1' в библиотеке не найдена", exception.getInfo());
	}

	@Test
	void shouldReturnBookById() throws Exception {
		Book expectedBook = new Book("title", new Author("author"), new Genre("genre"));
		Optional<Book> optionalBook = Optional.of(expectedBook);
		when(bookDao.findBy(1l)).thenReturn(optionalBook);

		Book actualBook = libraryService.findBookBy(1l);

		assertThat(expectedBook).isEqualToComparingFieldByField(actualBook);
	}

	@Test
	void shouldCallFindAllBooksDaoMethod() throws Exception {
		libraryService.findAllBooks();

		verify(bookDao, times(1)).findAll();
	}

	@Test
	void shouldThrowServiceExceptionWhenUpdateBookAndBookTitleIsEmpty() {
		BookServiceException exception = Assertions.assertThrows(BookServiceException.class, () -> {
			libraryService.saveBook(1l, "", 1l, "", 1l);
		});

		assertEquals("Не задано название книги", exception.getInfo());
	}

	@Test
	void shouldThrowServiceExceptionWhenUpdateBookAndBookTitleIsNull() {
		BookServiceException exception = Assertions.assertThrows(BookServiceException.class, () -> {
			libraryService.saveBook(1l, null, 1l, "", 1l);
		});

		assertEquals("Не задано название книги", exception.getInfo());
	}

	@Test
	void shouldThrowServiceExceptionWhenUpdateBookAndAuthorNameIsEmpty() {
		BookServiceException exception = Assertions.assertThrows(BookServiceException.class, () -> {
			libraryService.saveBook(1l, "book_title", 0, "", 1l);
		});

		assertEquals("Не задан автор книги", exception.getInfo());
	}

	@Test
	void shouldThrowServiceExceptionWhenUpdateBookAndAuthorNameIsNull() {
		BookServiceException exception = Assertions.assertThrows(BookServiceException.class, () -> {
			libraryService.saveBook(1l, "book_title", 0, null, 1l);
		});

		assertEquals("Не задан автор книги", exception.getInfo());
	}

	@Test
	void shouldThrowServiceExceptionWhenUpdateBookAndGenreIsNotExist() throws Exception {
		when(genreDao.findBy(1l)).thenReturn(Optional.empty());
		when(authorDao.findBy(1l)).thenReturn(Optional.of(new Author("test_author")));
		BookServiceException exception = Assertions.assertThrows(BookServiceException.class, () -> {
			libraryService.saveBook(1l, "test_title", 1l, "test_author", 1l);
		});

		assertEquals("Отсутствует литературный жанр с id=1", exception.getInfo());
	}

	@Test
	void shouldСallDaoMethodToUpdateBook() throws Exception {
		when(genreDao.findBy(1l)).thenReturn(Optional.of(new Genre("test_genre_type")));
		libraryService.saveBook(1l, "test_title", 0, "test_author", 1l);

		verify(bookDao, times(1)).save(any());
	}

	@Test
	void shouldThrowServiceExceptionWhenDeleteBookWhichIsNotExist() throws Exception {
		when(bookDao.findBy(1l)).thenReturn(Optional.empty());
		BookServiceException exception = Assertions.assertThrows(BookServiceException.class, () -> {
			libraryService.deleteBookBy(1l);
		});

		assertEquals("Книга с id='1' в библиотеке не найдена", exception.getInfo());
	}

	@Test
	void shouldСallDeleteBookByDaoMethod() throws Exception {
		when(bookDao.findBy(1l)).thenReturn(Optional.of(mock(Book.class)));
		libraryService.deleteBookBy(1l);

		verify(bookDao, times(1)).deleteBy(1l);
	}
}