package ru.otus.krivonos.library.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.krivonos.library.model.Genre;

public interface GenreRepository extends MongoRepository<Genre, String> {
	boolean existsByType(String type);
}
