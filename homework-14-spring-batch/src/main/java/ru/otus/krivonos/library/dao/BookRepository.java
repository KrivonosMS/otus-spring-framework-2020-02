package ru.otus.krivonos.library.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.krivonos.library.model.BookJPA;

public interface BookRepository extends JpaRepository<BookJPA, Long> {
}
