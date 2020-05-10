package ru.otus.krivonos.library.service;

import ru.otus.krivonos.library.exception.GenreServiceException;
import ru.otus.krivonos.library.model.Genre;

import java.util.List;

public interface GenreService {
	List<Genre> findAllGenres() throws GenreServiceException;

	void saveGenre(String type) throws GenreServiceException;
}
