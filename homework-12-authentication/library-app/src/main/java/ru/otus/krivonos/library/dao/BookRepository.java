package ru.otus.krivonos.library.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.krivonos.library.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
}
