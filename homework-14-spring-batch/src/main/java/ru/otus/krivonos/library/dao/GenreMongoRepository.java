package ru.otus.krivonos.library.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.krivonos.library.model.GenreMongo;

public interface GenreMongoRepository extends MongoRepository<GenreMongo, String> {
}
