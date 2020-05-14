package ru.otus.krivonos.library.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.krivonos.library.dao.GenreRepository;
import ru.otus.krivonos.library.exception.GenreServiceException;
import ru.otus.krivonos.library.model.Genre;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
			genreService.createGenre(null);
		});

		assertEquals("Не задан литературный жанр", exception.getInfo());
	}

	@Test
	void shouldThrowServiceExceptionWhenSaveGenreWhichIsEmpty() {
		GenreServiceException exception = Assertions.assertThrows(GenreServiceException.class, () -> {
			genreService.createGenre("");
		});

		assertEquals("Не задан литературный жанр", exception.getInfo());
	}

	@Test
	void shouldCallSaveGenreDaoMethod() {
		genreService.createGenre("genre");

		verify(genreRepository, times(1)).save(any());
	}

	@Test
	void shouldThrowServiceExceptionWhenUpdateGenreWhichIsNull() {
		GenreServiceException exception = Assertions.assertThrows(GenreServiceException.class, () -> {
			genreService.updateGenre(1, null);
		});

		assertEquals("Не задан литературный жанр", exception.getInfo());
	}

	@Test
	void shouldThrowServiceExceptionWhenUpdateGenreWhichIsEmpty() {
		GenreServiceException exception = Assertions.assertThrows(GenreServiceException.class, () -> {
			genreService.updateGenre(1, "");
		});

		assertEquals("Не задан литературный жанр", exception.getInfo());
	}

	@Test
	void shouldCallUpdateGenreDaoMethod() {
		Genre genre = new Genre(1, "genre");
		when(genreRepository.findById(1L)).thenReturn(Optional.of(genre));

		genreService.updateGenre(1, "genre");

		verify(genreRepository, times(1)).findById(1L);
		verify(genreRepository, times(1)).save(any());
	}
}