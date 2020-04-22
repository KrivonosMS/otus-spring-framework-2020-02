package ru.otus.krivonos.library.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.krivonos.library.dao.GenreRepository;
import ru.otus.krivonos.library.exception.GenreServiceException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
class GenreServiceImplTest {
	@Mock
	private GenreRepository genreRepository;
	@InjectMocks
	private GenreServiceImpl genreService;

	@Test
	void shouldCallFindAllGenres() throws Exception {
		genreService.findAllGenres();

		verify(genreRepository, times(1)).findAll();
	}

	@Test
	void shouldThrowServiceExceptionWhenSaveGenreWhichIsNull() {
		GenreServiceException exception = Assertions.assertThrows(GenreServiceException.class, () -> {
			genreService.saveGenre(null);
		});

		assertEquals("Не задан литературный жанр", exception.getInfo());
	}

	@Test
	void shouldThrowServiceExceptionWhenSaveGenreWhichIsEmpty() {
		GenreServiceException exception = Assertions.assertThrows(GenreServiceException.class, () -> {
			genreService.saveGenre(null);
		});

		assertEquals("Не задан литературный жанр", exception.getInfo());
	}

	@Test
	void shouldCallSaveGenreDaoMethod() throws Exception {
		genreService.saveGenre("genre");

		verify(genreRepository, times(1)).save(any());
	}
}