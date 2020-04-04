package ru.otus.krivonos.library.dao;

import ru.otus.krivonos.library.domain.Genre;
import ru.otus.krivonos.library.exception.GenreDaoException;

import java.util.List;

public interface GenreDao {
	List<Genre> findAllGenres() throws GenreDaoException;

	boolean isExist(Genre genre) throws GenreDaoException;

	long saveGenre(Genre genre) throws GenreDaoException;
}
