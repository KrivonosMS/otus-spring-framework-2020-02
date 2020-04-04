package ru.otus.krivonos.library.shell;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.krivonos.library.domain.Book;
import ru.otus.krivonos.library.domain.Genre;
import ru.otus.krivonos.library.service.*;

import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class ShellController {
	public static final Logger LOG = LoggerFactory.getLogger(ShellController.class);

	private final LibraryService libraryService;
	private final OutputService outputService;

	@ShellMethod(value = "Print all books", key = {"all books", "books"})
	public String allBooks() {
		String msg = "";
		try {
			List<Book> books = libraryService.findAllBooks();
			for (Book book : books) {
				outputService.printText(String.format( "Книга{id=%s, название='%s', автор='%s', жанр='%s'}", book.getId(), book.getTitle(), book.getAuthor().getName(), book.getGenre().getType()));
			}
		} catch (LibraryServiceException e) {
			LOG.error("Ошибка при получении списка всех книг", e);
			msg = "Возникла непредвиденная ошика";
		} catch (OutputServiceException e) {
			LOG.error("Ошибка при выводе информации о книгах", e);
			msg = "Возникла непредвиденная ошика";
		}
		return msg;
	}

	@ShellMethod(value = "Print all genres", key = {"all genres", "genres"})
	public String allGenres() {
		String msg = "";
		try {
			List<Genre> genres = libraryService.findAllGenres();
			for (Genre genre : genres) {
				outputService.printText("'" + genre.getType() + "'");
			}
		} catch (LibraryServiceException e) {
			LOG.error("Ошибка при получении списка всех литературных жанров", e);
			msg = "Возникла непредвиденная ошика";
		} catch (OutputServiceException e) {
			LOG.error("Ошибка при выводе информации о литературных жанрах", e);
			msg = "Возникла непредвиденная ошика";
		}
		return msg;
	}

	@ShellMethod(value = "Print book", key = {"book"})
	public String book(long id) {
		String msg = "";
		try {
			Book book = libraryService.findBookBy(id);
			outputService.printText(String.format( "Книга{id=%s, название='%s', автор='%s', жанр='%s'}", book.getId(), book.getTitle(), book.getAuthor().getName(), book.getGenre().getType()));
		} catch (OutputServiceException e) {
			LOG.error("Ошибка при выводе информации о книге с di=" + id, e);
			msg = "Возникла непредвиденная ошика";
		} catch (LibraryServiceException e) {
			LOG.error("Ошибка при получении книги с id=" + id, e);
			msg = "Возникла непредвиденная ошика";
		} catch (NotValidParameterDataException e) {
			LOG.error("Ошибка при получении книги с id=" + id + " " + e.getInfo(), e);
			msg = e.getInfo();
		}
		return msg;
	}

	@ShellMethod(value = "Update book", key = {"update book", "update"})
	public String updateBook(long id, String bookTitle, String authorName, String genreType) {
		String msg = "книга успешно обновлена";
		try {
			libraryService.updateBook(id, bookTitle, authorName, genreType);
		} catch (LibraryServiceException e) {
			LOG.error("Ошибка при обновлении книги с id=" + id, e);
			msg = "Возникла непредвиденная ошика";
		} catch (NotValidParameterDataException e) {
			LOG.error("Ошибка при обновлении книги с id=" + id + " " + e.getInfo(), e);
			msg = e.getInfo();
		}
		return msg;
	}

	@ShellMethod(value = "Save book", key = {"save book"})
	public String saveBook(String bookTitle, String authorName, String genreType) {
		String msg = "книга успешно сохранена";
		try {
			libraryService.saveBook(bookTitle, authorName, genreType);
		} catch (LibraryServiceException e) {
			LOG.error("Ошибка при сохранении книги", e);
			msg = "Возникла непредвиденная ошика";
		} catch (NotValidParameterDataException e) {
			LOG.error("Ошибка при сохранении книги" + " " + e.getInfo(), e);
			msg = e.getInfo();
		}
		return msg;
	}

	@ShellMethod(value = "Delete book ", key = {"delete book"})
	public String deleteBookBy(long id) {
		String msg = "книга успешно удалена";
		try {
			libraryService.deleteBookBy(id);
		} catch (LibraryServiceException e) {
			LOG.error("Ошибка при удалении книги с id=" + id, e);
			msg = "Возникла непредвиденная ошика";
		} catch (NotValidParameterDataException e) {
			LOG.error("Ошибка при удалении книги с id=" + id + " " + e.getInfo(), e);
			msg = e.getInfo();
		}
		return msg;
	}

	@ShellMethod(value = "Save genre", key = {"save genre"})
	public String saveGenre(String type) {
		String msg = "литературный жанр '" + type + "' успешно сохранен";
		try {
			libraryService.saveGenre(type);
		} catch (LibraryServiceException e) {
			LOG.error("Ошибка при добавление литературного жанра '" + type +"'", e);
			msg = "Возникла непредвиденная ошика";
		} catch (NotValidParameterDataException e) {
			LOG.error("Ошибка при добавление литературного жанра '" + type + "'" + " " + e.getInfo(), e);
			msg = e.getInfo();
		}
		return msg;
	}
}