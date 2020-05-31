package ru.otus.krivonos.library.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.krivonos.library.model.Genre;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@Transactional
class DbGenreRepositoryTest {
	@Autowired
	private GenreRepository bookDao;
	@Autowired
	private TestEntityManager em;

	@Test
	void shouldReturnAllGenres() {
		List<Genre> genres = bookDao.findAll();

		assertThat(genres)
			.hasSize(4)
			.doesNotHaveDuplicates();
	}

	@Test
	void shouldReturnTrueIfGenreExist() {
		boolean isExist = bookDao.existsByType("Русская классика");

		assertTrue(isExist);
	}

	@Test
	void shouldReturnFalseIfGenreIsNotExist() {
		boolean isExist = bookDao.existsByType("Несуществующий жанр");

		assertFalse(isExist);
	}

	@Test
	void shouldSaveGenre() {
		Genre genre = new Genre("Техническа литература");

		bookDao.save(genre);

		assertThat(genre.getId()).isGreaterThan(0);
		Genre expectedGenre = em.find(Genre.class, genre.getId());
		assertThat(expectedGenre).isEqualToIgnoringGivenFields(expectedGenre, "id");
	}

	@Test
	void shouldUpdateGenre() {
		Genre genre = new Genre(1l, "Техническа литература");

		bookDao.save(genre);

		Genre expectedGenre = em.find(Genre.class, 1l);
		assertThat(expectedGenre).isEqualToComparingFieldByField(expectedGenre);
	}

	@Test
	void shouldReturnNotEmptyOptionalGenreById() {
		Optional<Genre> optionalGenre = bookDao.findById(1l);

		assertThat(optionalGenre)
			.isPresent()
			.get()
			.isEqualToComparingFieldByField(new Genre(1l, "Классическая проза"));
	}

	@Test
	void shouldReturnEmptyOptionalGenreById() {
		Optional<Genre> optionalGenre = bookDao.findById(-11l);

		assertThat(optionalGenre)
			.isNotPresent();
	}
}