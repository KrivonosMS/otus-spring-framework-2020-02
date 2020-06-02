package ru.otus.krivonos.library.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.krivonos.library.model.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {
	boolean existsByType(String type);
}
