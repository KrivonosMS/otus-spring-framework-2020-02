package ru.otus.krivonos.library.dao;

import ru.otus.krivonos.library.model.Genre;
import ru.otus.krivonos.library.exception.GenreDaoException;

import java.util.List;
import java.util.Optional;

public interface GenreDao {
	List<Genre> findAll() throws GenreDaoException;

	Optional<Genre> findBy(long id) throws GenreDaoException;

	boolean isExist(String genreType) throws GenreDaoException;

	long save(Genre genre) throws GenreDaoException;
}
