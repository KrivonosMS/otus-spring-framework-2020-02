package ru.otus.krivonos.library.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.krivonos.library.domain.Author;
import ru.otus.krivonos.library.domain.Book;
import ru.otus.krivonos.library.domain.Genre;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public class DbLibraryDao implements LibraryDao {
	@PersistenceContext
	private EntityManager em;

	@Override
	public Optional<Book> findBookBy(long id) throws LibraryDaoException {
		try {
			return Optional.ofNullable(em.find(Book.class, id));
		} catch (Exception e) {
			throw new LibraryDaoException("Возникла непредвиденная ошибка при получении книги с id=" + id, e);
		}
	}

	@Override
	public List<Book> findAllBooks() throws LibraryDaoException {
		try {
			TypedQuery<Book> query = em.createQuery("select b from Book b", Book.class);
			return query.getResultList();
		} catch (Exception e) {
			throw new LibraryDaoException("Возникла непредвиденная ошибка при получении списка всех книг", e);
		}
	}

	@Override
	public long saveBook(Book book) throws LibraryDaoException {
		if (book == null) {
			throw new LibraryDaoException("Не задана книга для сохранения");
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
				throw new LibraryDaoException("Литературный жанр '" + genre.getType() + "' отсутствует");
			}
			if (book.getId() == 0) {
				em.persist(book);
			} else {
				em.merge(book);
			}
			return book.getId();
		} catch (LibraryDaoException e) {
			throw e;
		} catch (Exception e) {
			throw new LibraryDaoException("Возникла непредвиденная ошибка при получении списка всех книг", e);
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
	public void deleteBookBy(long id) throws LibraryDaoException {
		try {
			Query query = em.createQuery("delete from Book b where b.id = :id");
			query.setParameter("id", id);
			query.executeUpdate();
		} catch (Exception e) {
			throw new LibraryDaoException("Возникла непредвиденная ошибка при удалении книги", e);
		}
	}

	@Override
	public List<Genre> findAllGenres() throws LibraryDaoException {
		try {
			TypedQuery<Genre> query = em.createQuery("select g from Genre g", Genre.class);
			return query.getResultList();
		} catch (Exception e) {
			throw new LibraryDaoException("Возникла непредвиденная ошибка при получении всех литературных жанров", e);
		}
	}

	@Override
	public boolean isExist(Genre genre) throws LibraryDaoException {
		if (genre == null) {
			throw new LibraryDaoException("Не задан литературный жанр");
		}
		try {
			return getGenreByType(genre.getType()).isPresent();
		} catch (Exception e) {
			throw new LibraryDaoException("Возникла непредвиденная ошибка во вермя проверки наличия литературного жанра '" + genre.getType() + "'", e);
		}
	}

	@Override
	public long saveGenre(Genre genre) throws LibraryDaoException {
		if (genre == null) {
			throw new LibraryDaoException("Не задан литературный жанр");
		}
		try {
			if (genre.getId() == 0) {
				em.persist(genre);
			} else {
				em.merge(genre);
			}
			return genre.getId();
		} catch (Exception e) {
			throw new LibraryDaoException("Возникла непредвиденная ошибка при добавлении нового литературного жанра " + genre.getType() + "'", e);
		}
	}
}