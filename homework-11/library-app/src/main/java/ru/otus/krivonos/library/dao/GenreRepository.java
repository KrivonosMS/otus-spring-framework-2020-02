package ru.otus.krivonos.library.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.otus.krivonos.library.model.Genre;

public interface GenreRepository extends ReactiveMongoRepository<Genre, String> {
}
