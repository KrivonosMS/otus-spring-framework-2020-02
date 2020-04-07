package ru.otus.krivonos.library.dao;

import org.springframework.stereotype.Repository;
import ru.otus.krivonos.library.model.Author;
import ru.otus.krivonos.library.exception.AuthorDaoException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public class DbAuthorDao implements AuthorDao {
	@PersistenceContext
	private EntityManager em;

	@Override
	public Optional<Author> findBy(long id) throws AuthorDaoException {
		try {
			return Optional.ofNullable(em.find(Author.class, id));
		} catch (Exception e) {
			throw new AuthorDaoException("Возникла непредвиденная ошибка во вермя поиска автора с id=" + id, e);
		}
	}
}
