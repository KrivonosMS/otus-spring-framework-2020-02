package ru.otus.krivonos.library.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.otus.krivonos.library.model.Book;

public interface BookRepository extends ReactiveMongoRepository<Book, String> {
}
