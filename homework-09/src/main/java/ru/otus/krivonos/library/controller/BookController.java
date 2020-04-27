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
		@RequestParam("genreId") @NotNull Long genreId
	) {
		LOG.debug("method=createBook \"добавление новой книги\" bookTitle={} authorName={} genreId={}", bookTitle, authorName, genreId);

		bookService.updateBook(bookTitle, authorName, genreId);

		LOG.debug("method=createBook \"добавлена новая книга\"");

		return "redirect:/library/book/all";
	}

	@GetMapping("/edit")
	public String editBookPage(Model model, @RequestParam("id") @NotNull Long bookId) {
		LOG.debug("method=editBookPage \"запрос на получение страницы редактирования книги с id={}\"", bookId);

		List<Genre> genres = genreService.findAllGenres();
		model.addAttribute("genres", genres);
		Book book = bookService.findBookBy(bookId);
		model.addAttribute("book", book);

		LOG.debug("method=editBookPage \"получена страница редактирования книгис id={}\" genreSize={}", bookId, genres.size());

		return "editBook";
	}

	@PostMapping("/edit")
	public String editBook(
		@RequestParam("id") @NotNull Long bookId,
		@RequestParam("title") String bookTitle,
		@RequestParam("author") String authorName,
		@RequestParam("genreId") @NotNull Long genreId
	) {
		LOG.debug("method=editBook \"обновление книги с id={}\" bookTitle={} authorName={} genreId={}", bookId, bookTitle, authorName, genreId);

		bookService.updateBook(bookId, bookTitle, authorName, genreId);

		LOG.debug("method=editBook \"добавлена новая книга с bookId={}\"", bookId);

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
}