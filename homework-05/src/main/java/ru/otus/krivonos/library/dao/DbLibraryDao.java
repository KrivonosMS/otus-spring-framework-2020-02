package ru.otus.krivonos.library.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import ru.otus.krivonos.library.domain.Author;
import ru.otus.krivonos.library.domain.Book;
import ru.otus.krivonos.library.domain.BookTitle;
import ru.otus.krivonos.library.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class DbLibraryDao implements LibraryDao {
	private final NamedParameterJdbcOperations namedParameterJdbcOperations;

	@Override
	public Optional<Book> findBookBy(long id) throws LibraryDaoException {
		try {
			String query =
					"select " +
					" book.id id, " +
					" book.title title, " +
					" author.name name, " +
					" genre.type type " +
					"from " +
					" book " +
					"inner join author " +
					" on book.author_id = author.id " +
					"inner join genre " +
					" on book.genre_id = genre.id " +
					"where " +
					" book.id = :id " +
					"and " +
					" book.is_deleted = 0";
			List<Book> books = namedParameterJdbcOperations.query(query, Map.of("id", id), new BookMapper());
			if (books.isEmpty()) {
				return Optional.empty();
			} else {
				return Optional.of(books.get(0));
			}
		} catch (Exception e) {
			throw new LibraryDaoException("Возникла непредвиденная ошибка при получении книги", e);
		}
	}

	@Override
	public List<Book> findAllBooks() throws LibraryDaoException {
		try {
			String query =
					"select " +
					" book.id, " +
					" book.title title, " +
					" author.name name, " +
					" genre.type type " +
					"from " +
					" book " +
					"inner join author " +
					" on book.author_id = author.id " +
					"inner join genre " +
					" on book.genre_id = genre.id " +
					"where " +
					" book.is_deleted = 0";
			List<Book> books = namedParameterJdbcOperations.query(query, Map.of(), new BookMapper());
			return books;
		} catch (Exception e) {
			throw new LibraryDaoException("Возникла непредвиденная ошибка при получении списка всех книг", e);
		}
	}

	@Override
	public long save(Book book) throws LibraryDaoException {
		if (book == null) {
			throw new LibraryDaoException("Не задана книга для сохранения");
		}
		try {
			String queryDeleteBook = "insert into book(title, author_id, genre_id, is_deleted) values(:title, :authorId, :genreId, 0)";
			MapSqlParameterSource parameters = getMapSqlParameters(book);
			KeyHolder keyHolder = new GeneratedKeyHolder();
			namedParameterJdbcOperations.update(queryDeleteBook, parameters, keyHolder, new String[] {"id"});
			return keyHolder.getKey().longValue();
		} catch (Exception e) {
			throw new LibraryDaoException("Возникла непредвиденная ошибка при получении списка всех книг", e);
		}
	}

	private MapSqlParameterSource getMapSqlParameters(Book book) {
		long authorId = getAuthorId(book.author());
		long genreId = getGenreId(book.genre());
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("authorId", authorId);
		parameters.addValue("genreId", genreId);
		parameters.addValue("title", book.title().value());
		return parameters;
	}

	private long getAuthorId(Author author) {
		String queryAuthorId = "select id from author where name = :name";
		SqlRowSet rsAuthor = namedParameterJdbcOperations.queryForRowSet(queryAuthorId, Map.of("name", author.name()));
		long authorId;
		if (rsAuthor.next()) {
			authorId = rsAuthor.getLong("id");
		} else {
			authorId = saveAuthor(author);
		}
		return authorId;
	}

	private long saveAuthor(Author author) {
		long authorId;
		String querySaveAuthor = "insert into author(name) values(:name)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("name", author.name());
		namedParameterJdbcOperations.update(querySaveAuthor, parameters, keyHolder);
		authorId = keyHolder.getKey().longValue();
		return authorId;
	}

	private long getGenreId(Genre genre) {
		String queryGenreId = "select id from genre where type = :type";
		SqlRowSet rsGenre = namedParameterJdbcOperations.queryForRowSet(queryGenreId, Map.of("type",genre.type()));
		rsGenre.next();
		return rsGenre.getLong("id");
	}

	@Override
	public void deleteBy(long id) throws LibraryDaoException {
		String query = "update book set is_deleted = 1 where book.id = :id";
		try {
			namedParameterJdbcOperations.update(query, Map.of("id", id));
		} catch (Exception e) {
			throw new LibraryDaoException("Возникла непредвиденная ошибка при удалении книги", e);
		}
	}

	@Override
	public List<Genre> findAllGenres() throws LibraryDaoException {
		try {
			String query = "select type from genre";
			List<Genre> genres = namedParameterJdbcOperations.query(query, Map.of(), new GenreMapper());
			return genres;
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
			String query = "select count(1) from genre where type = :type";
			Integer count = namedParameterJdbcOperations.queryForObject(query, Map.of("type", genre.type()), Integer.class);
			return count != null && count > 0;
		} catch (Exception e) {
			throw new LibraryDaoException("Возникла непредвиденная ошибка во вермя проверки наличия литературного жанра " + genre.type() , e);
		}
	}

	private static class BookMapper implements RowMapper<Book> {
		@Override
		public Book mapRow(ResultSet rs, int i) throws SQLException {
			Genre genre = new Genre(rs.getString("type"));
			Author author = new Author(rs.getString("name"));
			BookTitle title = new BookTitle(rs.getString("title"));
			long id = rs.getLong("id");
			return new Book(id, title, author, genre);
		}
	}

	private static class GenreMapper implements RowMapper<Genre> {
		@Override
		public Genre mapRow(ResultSet rs, int i) throws SQLException {
			return new Genre(rs.getString("type"));
		}
	}
}