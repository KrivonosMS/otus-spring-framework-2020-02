package ru.otus.krivonos.library.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.krivonos.library.model.AuthorJPA;

public interface AuthorRepository extends JpaRepository<AuthorJPA, Long> {
}
