package ru.otus.krivonos.library.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.krivonos.library.domain.Genre;
import ru.otus.krivonos.library.exception.GenreDaoException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class DbGenreDao implements GenreDao {
	@PersistenceContext
	private EntityManager em;

	@Override
	@Transactional(readOnly = true)
	public List<Genre> findAllGenres() throws GenreDaoException {
		try {
			TypedQuery<Genre> query = em.createQuery("select g from Genre g", Genre.class);
			return query.getResultList();
		} catch (Exception e) {
			throw new GenreDaoException("Возникла непредвиденная ошибка при получении всех литературных жанров", e);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public boolean isExist(Genre genre) throws GenreDaoException {
		if (genre == null) {
			throw new GenreDaoException("Не задан литературный жанр");
		}
		try {
			return getGenreByType(genre.getType()).isPresent();
		} catch (Exception e) {
			throw new GenreDaoException("Возникла непредвиденная ошибка во вермя проверки наличия литературного жанра '" + genre.getType() + "'", e);
		}
	}

	private Optional<Genre> getGenreByType(String type) {
		TypedQuery<Genre> query = em.createQuery("select g from Genre g where g.type = :type", Genre.class);
		query.setParameter("type", type);
		List<Genre> result = query.getResultList();
		return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
	}

	@Override
	@Transactional(rollbackFor = GenreDaoException.class)
	public long saveGenre(Genre genre) throws GenreDaoException {
		if (genre == null) {
			throw new GenreDaoException("Не задан литературный жанр");
		}
		try {
			if (genre.getId() == 0) {
				em.persist(genre);
			} else {
				em.merge(genre);
			}
			return genre.getId();
		} catch (Exception e) {
			throw new GenreDaoException("Возникла непредвиденная ошибка при добавлении нового литературного жанра " + genre.getType() + "'", e);
		}
	}
}
