package ru.otus.krivonos.library.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.krivonos.library.model.GenreJPA;

public interface GenreRepository extends JpaRepository<GenreJPA, Long> {
}
