package ru.otus.krivonos.library.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.otus.krivonos.library.exception.MainException;
import ru.otus.krivonos.library.model.Book;
import ru.otus.krivonos.library.model.Genre;
import ru.otus.krivonos.library.service.BookService;
import ru.otus.krivonos.library.service.GenreService;

import javax.validation.constraints.NotNull;
import java.util.List;

@Controller
@RequestMapping("/library/book")
@RequiredArgsConstructor
public class BookController {
	public static final Logger LOG = LoggerFactory.getLogger(BookController.class);

	private final BookService bookService;
	private final GenreService genreService;

	@GetMapping("/all")
	public String allBooks(Model model) {
		LOG.debug("method=allBooks \"запрос на получение всех книг\"");

		List<Book> books = bookService.findAllBooks();
		model.addAttribute("books", books);

		LOG.debug("method=allBooks \"получен список со всеми книгами библиотеки\" size={}", books.size());

		return "books";
	}

	@GetMapping("/{id}/delete")
	public String deleteBookPage(Model model, @PathVariable("id") @NotNull Long id) {
		LOG.debug("method=deleteBookPage \"запрос на получение страницы удаления книги с id={}\"", id);

		Book book = bookService.findBookBy(id);
		model.addAttribute("book", book);

		LOG.debug("method=deleteBook \"получена страница удаления книги с id={}\"", id);

		return "deleteBook";
	}

	@PostMapping("/{id}/delete")
	public String deleteBook(Model model, @PathVariable("id") @NotNull Long id) {
		LOG.debug("method=deleteBook \"запрос на удаление книги с id={}\"", id);

		bookService.deleteBookBy(id);

		LOG.debug("method=deleteBook \"удалена книга с id={}\"", id);

		return "redirect:/library/book/all";
	}

	@GetMapping("/add")
	public String createBookPage(Model model) {
		LOG.debug("method=createBookPage \"запрос на получение страницы добавление новой книги\"");

		List<Genre> genres = genreService.findAllGenres();
		model.addAttribute("genres", genres);

		LOG.debug("method=createBookPage \"получена страница добавление новой книги\" genreSize={}", genres.size());

		return "createBook";
	}

	@PostMapping("/add")
	public String createBook(
		@RequestParam("title") String bookTitle,
		@RequestParam("author") String authorName,
		@RequestParam("genre") String genreType
	) {
		LOG.debug("method=createBook \"добавление новой книги\"");

		bookService.createBook(bookTitle, authorName, genreType);

		LOG.debug("method=createBook \"добавлена новая книга\"");

		return "redirect:/library/book/all";
	}

	@ExceptionHandler(MainException.class)
	public String handleMainException(Model model, MainException ex) {
		LOG.debug("method=handleMainException", ex);

		model.addAttribute("message", ex.getInfo());
		return "error";
	}

	@ExceptionHandler(Exception.class)
	public String handleException(Model model, Exception ex) {
		LOG.debug("method=handleException", ex);

		model.addAttribute("message", "Возникла непредвиденная ошибка");
		return "error";
	}


//	@ShellMethod(value = "Update book", key = {"update book", "update"})
//	public String updateBook(long id, String bookTitle, long authorId, String authorName, long genreId) {
//		String msg = "книга успешно обновлена";
//		try {
//			bookService.saveBook(id, bookTitle, authorId, authorName, genreId);
//		} catch (MainException e) {
//			LOG.error("Ошибка при обновлении книги с id=" + id + " " + e.getInfo(), e);
//			msg = e.getInfo();
//		} catch (Exception e) {
//			LOG.error("Непредвиденная при обновлении книги с id=" + id, e);
//			msg = "Непредвиденная ошибка";
//		}
//		return msg;
//	}
//
//	@ShellMethod(value = "Save book", key = {"save book"})
//	public String saveBook(String bookTitle, long authorId, String authorName, long genreId) {
//		String msg = "книга успешно сохранена";
//		try {
//			bookService.saveBook(bookTitle, authorId, authorName, genreId);
//		} catch (MainException e) {
//			LOG.error("Ошибка при сохранении книги " + e.getInfo(), e);
//			msg = e.getInfo();
//		} catch (Exception e) {
//			LOG.error("Непредвиденная ошибка при сохранении книги", e);
//			msg = "Непредвиденная ошибка";
//		}
//		return msg;
//	}
//
//	@ShellMethod(value = "Delete book ", key = {"delete book"})
//	public String deleteBookBy(long id) {
//		String msg = "книга успешно удалена";
//		try {
//			bookService.deleteBookBy(id);
//		} catch (MainException e) {
//			LOG.error("Ошибка при удалении книги с id=" + id + " " + e.getInfo(), e);
//			msg = e.getInfo();
//		} catch (Exception e) {
//			LOG.error("Непредвиденная ошибка при удалении книги с id=" + id, e);
//			msg = "Непредвиденная ошибка";
//		}
//		return msg;
//	}
}