package ru.otus.krivonos.library.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.krivonos.library.model.Book;

public interface BookRepository extends MongoRepository<Book, String> {
}
