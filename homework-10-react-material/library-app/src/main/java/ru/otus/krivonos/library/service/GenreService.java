package ru.otus.krivonos.library.service;

import ru.otus.krivonos.library.model.Genre;

import java.util.List;

public interface GenreService {
	List<Genre> findAllGenres();

	void createGenre(String type);

	void updateGenre(long id, String type);
}
