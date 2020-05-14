package ru.otus.krivonos.library.service;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    @Transactional(rollbackFor = Exception.class)
    public void createGenre(String type) throws GenreServiceException {
        long startTime = System.currentTimeMillis();
        LOG.debug("method=createGenre action=\"сохранение нового литературного жанра\" type={}", type);

        Genre genre = saveGenre(0, type);

        long endTime = System.currentTimeMillis();
        LOG.debug("method=createGenre action=\"сохранен литературный жанр\" genre={} time={}ms", genre, endTime - startTime);
    }

    private Genre saveGenre(long id, String type) {
        if (type == null || "".equals(type.trim())) {
            throw new GenreServiceException("Не задан литературный жанр");
        }

        if (genreRepository.existsByType(type)) {
            throw new GenreServiceException("Указанный литературный жанр '" + type + "' уже существует");
        }

        Genre genre;
        if (id == 0) {
            genre = new Genre(type);
        } else {
            genre = genreRepository.findById(id).orElseThrow(() -> new GenreServiceException("Отсутствует литературный жанр c Id=" + id));
            genre.setType(type);
        }
        genreRepository.save(genre);

        return genre;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateGenre(long id, String type) throws GenreServiceException {
        long startTime = System.currentTimeMillis();
        LOG.debug("method=updateGenre action=\"сохранение литературного жанра\" id={} type={}", id, type);

        Genre genre = saveGenre(id, type);

        long endTime = System.currentTimeMillis();
        LOG.debug("method=updateGenre action=\"литературный жанр сохранен\" genre={} time={}ms", genre, endTime - startTime);
    }
}