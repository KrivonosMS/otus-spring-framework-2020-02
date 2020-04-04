package ru.otus.krivonos.library.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.krivonos.library.domain.Genre;
import ru.otus.krivonos.library.exception.GenreDaoException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import(DbGenreDao.class)
class DbGenreDaoTest {
	@Autowired
	private GenreDao bookDao;
	@Autowired
	private TestEntityManager em;

	@Test
	void shouldReturnAllGenres() throws Exception {
		List<Genre> genres = bookDao.findAllGenres();

		assertThat(genres)
			.hasSize(4)
			.doesNotHaveDuplicates();
	}

	@Test
	void shouldReturnTrueIfGenreExist() throws Exception {
		Genre genre = new Genre("Русская классика");

		boolean isExist = bookDao.isExist(genre);

		assertTrue(isExist);
	}

	@Test
	void shouldReturnFalseIfGenreIsNotExist() throws Exception {
		Genre genre = new Genre("Несуществующий жанр");

		boolean isExist = bookDao.isExist(genre);

		assertFalse(isExist);
	}

	@Test
	void shouldThrowLibraryDaoExceptionWhenSaveGenreWhichIsNull() throws Exception {
		GenreDaoException exception = Assertions.assertThrows(GenreDaoException.class, () -> {
			bookDao.saveGenre(null);
		});

		assertEquals("Не задан литературный жанр", exception.getMessage());
	}

	@Test
	void shouldSaveGenre() throws Exception {
		Genre genre = new Genre("Техническа литература");

		bookDao.saveGenre(genre);

		assertThat(genre.getId()).isGreaterThan(0);
		Genre expectedGenre = em.find(Genre.class, genre.getId());
		assertThat(expectedGenre).isEqualToIgnoringGivenFields(expectedGenre, "id");
	}

	@Test
	void shouldUpdateGenre() throws Exception {
		Genre genre = new Genre(1l, "Техническа литература");

		bookDao.saveGenre(genre);

		Genre expectedGenre = em.find(Genre.class, 1l);
		assertThat(expectedGenre).isEqualToComparingFieldByField(expectedGenre);
	}

	@Test
	void shouldThrowLibraryDaoExceptionWhenGenreIsNull() {
		GenreDaoException exception = Assertions.assertThrows(GenreDaoException.class, () -> {
			bookDao.isExist(null);
		});

		assertEquals("Не задан литературный жанр", exception.getMessage());
	}
}