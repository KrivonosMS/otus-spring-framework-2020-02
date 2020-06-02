package ru.otus.krivonos.library.service;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.otus.krivonos.library.dao.GenreRepository;
import ru.otus.krivonos.library.exception.GenreServiceException;
import ru.otus.krivonos.library.model.Genre;

import java.util.List;

@AllArgsConstructor
@Service
public class GenreServiceImpl implements GenreService {
	public static final Logger LOG = LoggerFactory.getLogger(GenreServiceImpl.class);

	private final GenreRepository genreRepository;

	@Override
	public List<Genre> findAllGenres() throws GenreServiceException {
		long startTime = System.currentTimeMillis();
		LOG.debug("method=findAllGenres action=\"получени всех литературных жанров\"");

		List<Genre> genres = genreRepository.findAll();

		long endTime = System.currentTimeMillis();
		LOG.debug("method=findAllGenres action=\"получены все литературные жанры\" count={} time={}ms", genres.size(), endTime - startTime);

		return genres;
	}

	@Override
	public void saveGenre(String type) throws GenreServiceException {
		if (type == null || "".equals(type.trim())) {
			throw new GenreServiceException("Не задан литературный жанр");
		}
		long startTime = System.currentTimeMillis();
		LOG.debug("method=saveGenre action=\"сохранение литературного жанра\" type={}", type);

		if (genreRepository.existsByType(type)) {
			throw new GenreServiceException("Указанный литературный жанр '" + type + "' уже существует");
		}
		Genre genre = new Genre(type);
		genreRepository.save(genre);

		long endTime = System.currentTimeMillis();
		LOG.debug("method=saveGenre action=\"сохранен литературный жанр\" genre={} time={}ms", genre, endTime - startTime);
	}
}