package ru.otus.krivonos.library.service;

import ru.otus.krivonos.library.exception.GenreServiceException;
import ru.otus.krivonos.library.model.Genre;

import java.util.List;

public interface GenreService {
	List<Genre> findAllGenres() throws GenreServiceException;

	void createGenre(String type) throws GenreServiceException;

	void updateGenre(long id, String type) throws GenreServiceException;
}
