package ru.otus.krivonos.library.service;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.krivonos.library.dao.GenreDao;
import ru.otus.krivonos.library.exception.GenreDaoException;
import ru.otus.krivonos.library.exception.GenreServiceException;
import ru.otus.krivonos.library.model.Genre;

import java.util.List;

@AllArgsConstructor
@Service
public class GenreServiceImpl implements GenreService {
	public static final Logger LOG = LoggerFactory.getLogger(GenreServiceImpl.class);

	private final GenreDao genreDao;

	@Override
	public List<Genre> findAllGenres() throws GenreServiceException {
		try {
			long startTime = System.currentTimeMillis();
			LOG.debug("method=findAllGenres action=\"получени всех литературных жанров\"");

			List<Genre> genres = genreDao.findAll();

			long endTime = System.currentTimeMillis();
			LOG.debug("method=findAllGenres action=\"получены все литературные жанры\" count={} time={}ms", genres.size(), endTime - startTime);

			return genres;
		} catch (GenreDaoException e) {
			throw new GenreServiceException("Возникла непредвиденная ошибка при получении вссех литературных жанров", e);
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveGenre(String type) throws GenreServiceException {
		if (type == null || "".equals(type.trim())) {
			throw new GenreServiceException("Не задан литературный жанр");
		}
		try {
			long startTime = System.currentTimeMillis();
			LOG.debug("method=saveGenre action=\"сохранение литературного жанра\" type={}", type);

			if (genreDao.isExist(type)) {
				throw new GenreServiceException("Указанный литературный жанр '" + type + "' уже существует");
			}
			Genre genre = new Genre(type);
			genreDao.save(genre);

			long endTime = System.currentTimeMillis();
			LOG.debug("method=saveGenre action=\"сохранен литературный жанр\" genre={} time={}ms", genre, endTime - startTime);
		} catch (GenreDaoException e) {
			throw new GenreServiceException("Возникла непредвиденная ошибка при сохранении литературного жанра", e);
		}
	}
}