package ru.otus.krivonos.library.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.krivonos.library.model.AuthorMongo;

public interface AuthorMongoRepository extends MongoRepository<AuthorMongo, String> {
}
