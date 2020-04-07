package ru.otus.krivonos.library.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.krivonos.library.model.Genre;
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
	public List<Genre> findAll() throws GenreDaoException {
		try {
			TypedQuery<Genre> query = em.createQuery("select g from Genre g", Genre.class);
			return query.getResultList();
		} catch (Exception e) {
			throw new GenreDaoException("Возникла непредвиденная ошибка при получении всех литературных жанров", e);
		}
	}

	@Override
	public Optional<Genre> findBy(long id) throws GenreDaoException {
		try {
			return Optional.ofNullable(em.find(Genre.class, id));
		} catch (Exception e) {
			throw new GenreDaoException("Возникла непредвиденная ошибка во вермя поиска литературного жанра с id=" + id, e);
		}
	}

	@Override
	public boolean isExist(String genreType) throws GenreDaoException {
		try {
			TypedQuery<Genre> query = em.createQuery("select g from Genre g where g.type = :type", Genre.class);
			query.setParameter("type", genreType);
			List<Genre> result = query.getResultList();
			return !result.isEmpty();
		} catch (Exception e) {
			throw new GenreDaoException("Возникла непредвиденная ошибка во вермя проверки наличия литературного жанра '" + genreType + "'", e);
		}
	}

	@Override
	public long save(Genre genre) throws GenreDaoException {
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
