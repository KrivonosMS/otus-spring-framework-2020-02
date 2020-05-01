package ru.otus.krivonos.library.controller.page;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.otus.krivonos.library.model.Book;
import ru.otus.krivonos.library.service.BookService;

import javax.validation.constraints.NotNull;

@Controller
@RequestMapping("/library")
@RequiredArgsConstructor
public class LibraryPageController {
	public static final Logger LOG = LoggerFactory.getLogger(LibraryPageController.class);

	private final BookService bookService;

	@GetMapping("/")
	public String allBooks(Model model) {
		LOG.debug("method=allBooks \"запрос на получение страницы для всех книг\"");

		return "books";
	}

	@GetMapping("/book/{id}/delete")
	public String deleteBookPage(Model model, @PathVariable("id") @NotNull Long bookId) {
		LOG.debug("method=deleteBookPage \"запрос на получение страницы удаления книги с id={}\"", bookId);

		Book book = bookService.findBookBy(bookId);
		model.addAttribute("book", book);

		return "deleteBook";
	}

	@GetMapping("/book/add")
	public String createBookPage(Model model) {
		LOG.debug("method=createBookPage \"запрос на получение страницы добавление новой книги\"");

		return "createBook";
	}

	@GetMapping("/book/{id}/edit")
	public String editBookPage(Model model, @PathVariable("id") @NotNull Long bookId) {
		LOG.debug("method=editBookPage \"запрос на получение страницы редактирования книги с id={}\"", bookId);

		Book book = bookService.findBookBy(bookId);
		model.addAttribute("book", book);

		LOG.debug("method=editBookPage \"получена страница редактирования книгис id={}\"", bookId);

		return "editBook";
	}

	@ExceptionHandler(Exception.class)
	public String handleException(Model model, Exception ex) {
		LOG.debug("method=handleException", ex);

		model.addAttribute("message", "Возникла непредвиденная ошибка");
		return "error";
	}
}