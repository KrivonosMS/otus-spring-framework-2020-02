package ru.otus.krivonos.library.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.krivonos.library.model.BookMongo;

public interface BookMongoRepository extends MongoRepository<BookMongo, String> {
}
