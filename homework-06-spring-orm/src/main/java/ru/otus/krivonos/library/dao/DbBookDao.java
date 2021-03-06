package ru.otus.krivonos.library.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.otus.krivonos.library.exception.BookDaoException;
import ru.otus.krivonos.library.exception.CommentDaoException;
import ru.otus.krivonos.library.model.Book;
import ru.otus.krivonos.library.model.Comment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class DbBookDao implements BookDao {
	public static final Logger LOG = LoggerFactory.getLogger(DbBookDao.class);

	@PersistenceContext
	private EntityManager em;

	@Override
	public Optional<Book> findBy(long id) throws BookDaoException {
		try {
			return Optional.ofNullable(em.find(Book.class, id));
		} catch (Exception e) {
			throw new BookDaoException("Возникла непредвиденная ошибка при получении книги с id=" + id, e);
		}
	}

	@Override
	public List<Book> findAll() throws BookDaoException {
		try {
			TypedQuery<Book> query = em.createQuery("select distinct(b) from Book b join fetch b.author inner join fetch b.genre left join fetch b.comments", Book.class);
			return query.getResultList();
		} catch (Exception e) {
			throw new BookDaoException("Возникла непредвиденная ошибка при получении списка всех книг", e);
		}
	}

	@Override
	public long save(Book book) throws BookDaoException {
		try {
			LOG.debug("method=save action=\"сохранение книги\" book={}", book);

			if (book.getId() == 0) {
				LOG.debug("method=save action=persist");

				em.persist(book);
			} else {
				LOG.debug("method=save action=merge");

				em.merge(book);
			}
			return book.getId();
		} catch (Exception e) {
			throw new BookDaoException("Возникла непредвиденная ошибка при сохранении книги", e);
		}
	}

	@Override
	public void delete(Book book) throws BookDaoException {
		try {
			em.remove(book);
		} catch (Exception e) {
			throw new BookDaoException("Возникла непредвиденная ошибка при удалении книги с id=" + book.getId(), e);
		}
	}

	@Override
	public List<Comment> findAllCommentsBy(long bookId) throws BookDaoException {
		Book book = findBy(bookId).orElseThrow(()-> new BookDaoException("Не найдена книга с id=" + bookId));
		try {
			return book.getComments();
		} catch (Exception e) {
			throw new BookDaoException("Возникла непредвиденная при получении комментариев к книге с id=" + bookId, e);
		}
	}
}