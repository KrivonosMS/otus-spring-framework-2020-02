package ru.otus.krivonos.library.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.krivonos.library.dao.BookDao;
import ru.otus.krivonos.library.dao.GenreDao;
import ru.otus.krivonos.library.domain.Author;
import ru.otus.krivonos.library.domain.Book;
import ru.otus.krivonos.library.domain.Genre;
import ru.otus.krivonos.library.exception.NotValidParameterDataException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class LibraryServiceImplTest {
	@Mock
	private BookDao bookDao;
	@Mock
	private GenreDao genreDao;
	@InjectMocks
	private LibraryServiceImpl libraryService;

	@Test
	void shouldThrowNotValidParameterDataExceptionWhenSaveBookWithArgsAreNull() {
		NotValidParameterDataException exception = Assertions.assertThrows(NotValidParameterDataException.class, () -> {
			libraryService.saveBook(null, null, null);
		});

		assertEquals("Не задано название книги; Не задан автор книги; Не задан литературный жанр", exception.getInfo());
	}

	@Test
	void shouldThrowNotValidParameterDataExceptionWhenSaveBookWithArgsAreEmpty() {
		NotValidParameterDataException exception = Assertions.assertThrows(NotValidParameterDataException.class, () -> {
			libraryService.saveBook("  ", "  ", "  ");
		});

		assertEquals("Не задано название книги; Не задан автор книги; Не задан литературный жанр", exception.getInfo());
	}

	@Test
	void shouldThrowNotValidParameterDataExceptionWhenSaveBookAndGenreIsNotExist() throws Exception {
		when(genreDao.isExist(any())).thenReturn(false);
		NotValidParameterDataException exception = Assertions.assertThrows(NotValidParameterDataException.class, () -> {
			libraryService.saveBook("test_title", "test_author", "test_genre");
		});

		assertEquals("Жанр 'test_genre' отстутствует, добавтьте его при необходимости", exception.getInfo());
	}

	@Test
	void shouldСallDaoMethodToSaveBook() throws Exception {
		when(genreDao.isExist(any())).thenReturn(true);
		libraryService.saveBook("test_title", "test_author", "test_genre");

		verify(bookDao, times(1)).saveBook(any());
	}

	@Test
	void shouldThrowNotValidParameterDataExceptionWhenFindBookByIdWhichIsNotExist() throws Exception {
		when(bookDao.findBookBy(1l)).thenReturn(Optional.empty());
		NotValidParameterDataException exception = Assertions.assertThrows(NotValidParameterDataException.class, () -> {
			libraryService.findBookBy(1l);
		});

		assertEquals("Книга с id='1' в библиотеке не найдена", exception.getInfo());
	}

	@Test
	void shouldReturnBookById() throws Exception {
		Book expectedBook = new Book("title", new Author("author"), new Genre("genre"));
		Optional<Book> optionalBook = Optional.of(expectedBook);
		when(bookDao.findBookBy(1l)).thenReturn(optionalBook);

		Book actualBook = libraryService.findBookBy(1l);

		assertThat(expectedBook).isEqualToComparingFieldByField(actualBook);
	}

	@Test
	void shouldCallFindAllBooksDaoMethod() throws Exception {
		libraryService.findAllBooks();

		verify(bookDao, times(1)).findAllBooks();
	}


	@Test
	void shouldThrowNotValidParameterDataExceptionWhenUpdateBookWithArgsAreNull() {
		NotValidParameterDataException exception = Assertions.assertThrows(NotValidParameterDataException.class, () -> {
			libraryService.updateBook(1l, null, null, null);
		});

		assertEquals("Не задано название книги; Не задан автор книги; Не задан литературный жанр", exception.getInfo());
	}

	@Test
	void shouldThrowNotValidParameterDataExceptionWhenUpdateBookWithArgsAreEmpty() {
		NotValidParameterDataException exception = Assertions.assertThrows(NotValidParameterDataException.class, () -> {
			libraryService.updateBook(1l, "  ", "  ", "  ");
		});

		assertEquals("Не задано название книги; Не задан автор книги; Не задан литературный жанр", exception.getInfo());
	}

	@Test
	void shouldThrowNotValidParameterDataExceptionWhenUpdateBookAndGenreIsNotExist() throws Exception {
		when(genreDao.isExist(any())).thenReturn(false);
		when(bookDao.findBookBy(1l)).thenReturn(Optional.of(mock(Book.class)));
		NotValidParameterDataException exception = Assertions.assertThrows(NotValidParameterDataException.class, () -> {
			libraryService.updateBook(1l, "test_title", "test_author", "test_genre");
		});

		assertEquals("Жанр 'test_genre' отстутствует, добавтьте его при необходимости", exception.getInfo());
	}

	@Test
	void shouldThrowNotValidParameterDataExceptionWhenUpdateBookWhichIsNotExist() throws Exception {
		when(genreDao.isExist(any())).thenReturn(true);
		when(bookDao.findBookBy(1l)).thenReturn(Optional.empty());
		NotValidParameterDataException exception = Assertions.assertThrows(NotValidParameterDataException.class, () -> {
			libraryService.updateBook(1l, "test_title", "test_author", "test_genre");
		});

		assertEquals("Книга с id='1' в библиотеке не найдена", exception.getInfo());
	}

	@Test
	void shouldСallDaoMethodToUpdateBook() throws Exception {
		when(genreDao.isExist(any())).thenReturn(true);
		when(bookDao.findBookBy(1l)).thenReturn(Optional.of(mock(Book.class)));
		libraryService.updateBook(1l, "test_title", "test_author", "test_genre");

		verify(bookDao, times(1)).saveBook(any());
	}

	@Test
	void shouldThrowNotValidParameterDataExceptionWhenDeleteBookWhichIsNotExist() throws Exception {
		when(bookDao.findBookBy(1l)).thenReturn(Optional.empty());
		NotValidParameterDataException exception = Assertions.assertThrows(NotValidParameterDataException.class, () -> {
			libraryService.deleteBookBy(1l);
		});

		assertEquals("Книга с id='1' в библиотеке не найдена", exception.getInfo());
	}

	@Test
	void shouldСallDeleteBookByDaoMethod() throws Exception {
		when(bookDao.findBookBy(1l)).thenReturn(Optional.of(mock(Book.class)));
		libraryService.deleteBookBy(1l);

		verify(bookDao, times(1)).deleteBookBy(1l);
	}

	@Test
	void shouldCallFindAllGenres() throws Exception {
		libraryService.findAllGenres();

		verify(genreDao, times(1)).findAllGenres();
	}

	@Test
	void shouldThrowNotValidParameterDataExceptionWhenSaveGenreWhichIsNull() {
		NotValidParameterDataException exception = Assertions.assertThrows(NotValidParameterDataException.class, () -> {
			libraryService.saveGenre(null);
		});

		assertEquals("Не задан литературный жанр", exception.getInfo());
	}

	@Test
	void shouldThrowNotValidParameterDataExceptionWhenSaveGenreWhichIsEmpty() {
		NotValidParameterDataException exception = Assertions.assertThrows(NotValidParameterDataException.class, () -> {
			libraryService.saveGenre(null);
		});

		assertEquals("Не задан литературный жанр", exception.getInfo());
	}

	@Test
	void shouldCallSaveGenreDaoMethod() throws Exception {
		libraryService.saveGenre("genre");

		verify(genreDao, times(1)).saveGenre(any());
	}
}