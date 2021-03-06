package ru.otus.krivonos.library.shell;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.krivonos.library.exception.MainException;
import ru.otus.krivonos.library.model.Book;
import ru.otus.krivonos.library.model.Comment;
import ru.otus.krivonos.library.model.Genre;
import ru.otus.krivonos.library.service.BookService;
import ru.otus.krivonos.library.service.GenreService;
import ru.otus.krivonos.library.service.OutputService;

import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class ShellController {
	public static final Logger LOG = LoggerFactory.getLogger(ShellController.class);

	private final BookService bookService;
	private final GenreService genreService;
	private final OutputService outputService;

	@ShellMethod(value = "Print all books", key = {"all books", "books"})
	public String allBooks() {
		String msg = "";
		try {
			List<Book> books = bookService.findAllBooks();
			for (Book book : books) {
				outputService.printText(String.format("Книга{id=%s, название='%s', автор='%s', жанр='%s'}", book.getId(), book.getTitle(), book.getAuthor().getName(), book.getGenre().getType()));
			}
		} catch (MainException e) {
			LOG.error("Ошибка при обработке запроса на получение вссех книг " + e.getInfo(), e);
			msg = e.getInfo();
		} catch (Exception e) {
			LOG.error("Непредвиденная ошибка при обработке запроса на получение всех книг", e);
			msg = "Непредвиденная ошибка";
		}
		return msg;
	}

	@ShellMethod(value = "Print all genres", key = {"all genres", "genres"})
	public String allGenres() {
		String msg = "";
		try {
			List<Genre> genres = genreService.findAllGenres();
			for (Genre genre : genres) {
				outputService.printText("'" + genre.getType() + "'");
			}
		} catch (MainException e) {
			LOG.error("Ошибка при обработке запроса на получение списка всех литературных жанров " + e.getInfo(), e);
			msg = e.getInfo();
		} catch (Exception e) {
			LOG.error("Непредвиденная ошибка при обработке запроса на получение всех книг", e);
			msg = "Непредвиденная ошибка";
		}
		return msg;
	}

	@ShellMethod(value = "Print book", key = {"book"})
	public String book(String id) {
		String msg = "";
		try {
			Book book = bookService.findBookBy(id);
			outputService.printText(String.format("Книга{id=%s, название='%s', автор='%s', жанр='%s'}", book.getId(), book.getTitle(), book.getAuthor().getName(), book.getGenre().getType()));
			for (Comment comment : book.getComments()) {
				outputService.printText(comment.getText());
			}
		} catch (MainException e) {
			LOG.error("Ошибка при обработке запроса на получении книги с id=" + id + " " + e.getInfo(), e);
			msg = e.getInfo();
		} catch (Exception e) {
			LOG.error("Непредвиденная ошибка при обработке запроса на получении книги с id=" + id, e);
			msg = "Непредвиденная ошибка";
		}
		return msg;
	}

	@ShellMethod(value = "Update book", key = {"update book", "update"})
	public String updateBook(String id, String bookTitle, String authorName, String genreId) {
		String msg = "книга успешно обновлена";
		try {
			bookService.saveBook(id, bookTitle, authorName, genreId);
		} catch (MainException e) {
			LOG.error("Ошибка при обновлении книги с id=" + id + " " + e.getInfo(), e);
			msg = e.getInfo();
		} catch (Exception e) {
			LOG.error("Непредвиденная при обновлении книги с id=" + id, e);
			msg = "Непредвиденная ошибка";
		}
		return msg;
	}

	@ShellMethod(value = "Save book", key = {"save book"})
	public String saveBook(String bookTitle, String authorName, String genreId) {
		String msg = "книга успешно сохранена";
		try {
			bookService.saveBook(bookTitle, authorName, genreId);
		} catch (MainException e) {
			LOG.error("Ошибка при сохранении книги " + e.getInfo(), e);
			msg = e.getInfo();
		} catch (Exception e) {
			LOG.error("Непредвиденная ошибка при сохранении книги", e);
			msg = "Непредвиденная ошибка";
		}
		return msg;
	}

	@ShellMethod(value = "Delete book ", key = {"delete book"})
	public String deleteBookBy(String id) {
		String msg = "книга успешно удалена";
		try {
			bookService.deleteBookBy(id);
		} catch (MainException e) {
			LOG.error("Ошибка при удалении книги с id=" + id + " " + e.getInfo(), e);
			msg = e.getInfo();
		} catch (Exception e) {
			LOG.error("Непредвиденная ошибка при удалении книги с id=" + id, e);
			msg = "Непредвиденная ошибка";
		}
		return msg;
	}

	@ShellMethod(value = "Save genre", key = {"save genre"})
	public String saveGenre(String type) {
		String msg = "литературный жанр '" + type + "' успешно сохранен";
		try {
			genreService.saveGenre(type);
		} catch (MainException e) {
			LOG.error("Ошибка при добавление литературного жанра '" + type + "'" + " " + e.getInfo(), e);
			msg = e.getInfo();
		} catch (Exception e) {
			LOG.error("Непредвиденная ошибка при добавление литературного жанра '" + type + "'", e);
			msg = "Непредвиденная ошибка";
		}
		return msg;
	}
}