package ru.otus.krivonos.library.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.krivonos.library.model.Genre;

import java.util.Optional;

public interface GenreRepository extends JpaRepository<Genre, Long> {
	boolean existsByType(String type);

	Optional<Genre> findByType(String type);
}
