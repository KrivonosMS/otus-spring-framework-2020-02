package ru.otus.krivonos.library.dao;

import ru.otus.krivonos.library.model.Author;
import ru.otus.krivonos.library.exception.AuthorDaoException;

import java.util.Optional;

public interface AuthorDao {
	Optional<Author> findBy(long id) throws AuthorDaoException;
}
