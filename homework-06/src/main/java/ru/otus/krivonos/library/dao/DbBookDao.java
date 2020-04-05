package ru.otus.krivonos.library.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.krivonos.library.domain.Author;
import ru.otus.krivonos.library.domain.Book;
import ru.otus.krivonos.library.domain.Genre;
import ru.otus.krivonos.library.exception.BookDaoException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class DbBookDao implements BookDao {
	@PersistenceContext
	private EntityManager em;

	@Override
	@Transactional(readOnly = true)
	public Optional<Book> findBookBy(long id) throws BookDaoException {
		try {
			return Optional.ofNullable(em.find(Book.class, id));
		} catch (Exception e) {
			throw new BookDaoException("Возникла непредвиденная ошибка при получении книги с id=" + id, e);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<Book> findAllBooks() throws BookDaoException {
		try {
			TypedQuery<Book> query = em.createQuery("select distinct(b) from Book b join fetch b.author inner join fetch b.genre left join fetch b.comments", Book.class);
			return query.getResultList();
		} catch (Exception e) {
			throw new BookDaoException("Возникла непредвиденная ошибка при получении списка всех книг", e);
		}
	}

	@Override
	@Transactional
	public long saveBook(Book book) throws BookDaoException {
		if (book == null) {
			throw new BookDaoException("Не задана книга для сохранения");
		}
		try {
			Author author = book.getAuthor();
			Optional<Author> optionalAuthor = getAuthorByName(author.getName());
			if (optionalAuthor.isPresent()) {
				author = optionalAuthor.get();
				book.setAuthor(author);
			} else {
				em.persist(author);
			}
			Genre genre = book.getGenre();
			Optional<Genre> optionalGenre = getGenreByType(genre.getType());
			if (optionalGenre.isPresent()) {
				genre = optionalGenre.get();
				book.setGenre(genre);
			} else {
				throw new BookDaoException("Литературный жанр '" + genre.getType() + "' отсутствует");
			}
			if (book.getId() == 0) {
				em.persist(book);
			} else {
				em.merge(book);
			}
			return book.getId();
		} catch (BookDaoException e) {
			throw e;
		} catch (Exception e) {
			throw new BookDaoException("Возникла непредвиденная ошибка при сохранении книги", e);
		}
	}

	private Optional<Author> getAuthorByName(String author) {
		TypedQuery<Author> query = em.createQuery("select a from Author a where a.name = :name", Author.class);
		query.setParameter("name", author);
		List<Author> result = query.getResultList();
		return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
	}

	private Optional<Genre> getGenreByType(String type) {
		TypedQuery<Genre> query = em.createQuery("select g from Genre g where g.type = :type", Genre.class);
		query.setParameter("type", type);
		List<Genre> result = query.getResultList();
		return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
	}

	@Override
	@Transactional
	public void deleteBookBy(long id) throws BookDaoException {
		try {
			Query query = em.createQuery("delete from Book b where b.id = :id");
			query.setParameter("id", id);
			query.executeUpdate();
		} catch (Exception e) {
			throw new BookDaoException("Возникла непредвиденная ошибка при удалении книги с id=" + id, e);
		}
	}
}