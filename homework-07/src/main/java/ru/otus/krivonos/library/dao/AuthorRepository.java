package ru.otus.krivonos.library.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.krivonos.library.model.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
