package ru.otus.krivonos.library.dao;

import com.github.mongobee.Mongobee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import ru.otus.krivonos.library.bee.MongoBeeConfig;
import ru.otus.krivonos.library.model.Genre;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataMongoTest
@Import(MongoBeeConfig.class)
class DbGenreRepositoryTest {
	@Autowired
	private GenreRepository genreRepository;
	@Autowired
	private Mongobee mongobee;

	@BeforeEach
	void init() throws Exception {
		mongobee.execute();
	}

	@Test
	void shouldReturnAllGenres() {
		List<Genre> genres = genreRepository.findAll();

		assertThat(genres)
			.hasSize(4)
			.doesNotHaveDuplicates();
	}

	@Test
	void shouldReturnTrueIfGenreExist() {
		List<Genre> genres = genreRepository.findAll();
		genres.stream().forEach(System.out::println);
		boolean isExist = genreRepository.existsByType("Русская классика");

		assertTrue(isExist);
	}

	@Test
	void shouldReturnFalseIfGenreIsNotExist() {
		boolean isExist = genreRepository.existsByType("Несуществующий жанр");

		assertFalse(isExist);
	}

	@Test
	void shouldSaveGenre() {
		Genre genre = new Genre("Техническа литература");

		genreRepository.save(genre);

		Optional<Genre> expectedGenre = genreRepository.findById(genre.getId());
		assertThat(expectedGenre)
			.isPresent()
			.get()
			.isEqualToIgnoringGivenFields(genre, "id");
	}

	@Test
	void shouldUpdateGenre() {
		Genre expectedGenre = new Genre("1", "Техническа литература");

		genreRepository.save(expectedGenre);

		Optional<Genre> optionalActualGenre = genreRepository.findById("1");
		assertThat(optionalActualGenre)
			.isPresent()
			.get()
			.isEqualToComparingFieldByField(expectedGenre);
	}

	@Test
	void shouldReturnEmptyOptionalGenreById() {
		Optional<Genre> optionalGenre = genreRepository.findById("-1");

		assertThat(optionalGenre)
			.isNotPresent();
	}
}