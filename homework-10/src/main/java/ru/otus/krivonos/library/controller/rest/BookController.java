package ru.otus.krivonos.library.controller.rest;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.otus.krivonos.library.controller.rest.dto.BookDTO;
import ru.otus.krivonos.library.controller.rest.dto.ResultDTO;
import ru.otus.krivonos.library.exception.MainException;
import ru.otus.krivonos.library.model.Book;
import ru.otus.krivonos.library.service.BookService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/library")
@RequiredArgsConstructor
public class BookController {
	public static final Logger LOG = LoggerFactory.getLogger(BookController.class);

	private final BookService bookService;

	@GetMapping("/book/all")
	public List<BookDTO> allBooks(Model model) {
		LOG.debug("method=allBooks \"запрос на получение всех книг\"");

		List<Book> books = bookService.findAllBooks();
		List<BookDTO> bookDTOList = books
			.stream()
			.map(BookDTO::toDto)
			.collect(Collectors.toList());

		LOG.debug("method=allBooks \"получен список со всеми книгами библиотеки\" bookDTOListSize={}", bookDTOList.size());

		return bookDTOList;
	}

	@PostMapping("/book/{id}/delete")
	public ResultDTO deleteBook(
		HttpServletResponse response,
		Model model,
		@PathVariable("id") @NotNull Long id
	) throws IOException {
		LOG.debug("method=deleteBook \"запрос на удаление книги с id={}\"", id);

		bookService.deleteBookBy(id);

		LOG.debug("method=deleteBook \"удалена книга с id={}\"", id);

		return ResultDTO.ok();
	}

	@PostMapping("/book/add")
	public ResultDTO createBook(
		HttpServletResponse response,
		@RequestParam("title") String bookTitle,
		@RequestParam("author") String authorName,
		@RequestParam("genreId") @NotNull Long genreId
	) throws IOException {
		LOG.debug("method=createBook \"добавление новой книги\" bookTitle={} authorName={} genreId={}", bookTitle, authorName, genreId);

		bookService.createBook(bookTitle, authorName, genreId);

		LOG.debug("method=createBook \"добавлена новая книга\"");

		return ResultDTO.ok();
	}

	@PostMapping("/book/{id}/edit")
	public ResultDTO editBook(
		HttpServletResponse response,
		@PathVariable("id") @NotNull Long bookId,
		@RequestParam("title") String bookTitle,
		@RequestParam("author") String authorName,
		@RequestParam("genreId") @NotNull Long genreId
	) throws IOException {
		LOG.debug("method=editBook \"обновление книги с id={}\" bookTitle={} authorName={} genreId={}", bookId, bookTitle, authorName, genreId);

		bookService.updateBook(bookId, bookTitle, authorName, genreId);

		LOG.debug("method=editBook \"обновлеа книга с bookId={}\"", bookId);

		return ResultDTO.ok();
	}

	@ExceptionHandler(MainException.class)
	public ResultDTO handleMainException(Model model, MainException ex) {
		LOG.debug("method=handleMainException", ex);

		return ResultDTO.error(ex.getInfo());
	}

	@ExceptionHandler(Exception.class)
	public ResultDTO handleException(Model model, Exception ex) {
		LOG.debug("method=handleException", ex);

		return ResultDTO.error("Возникла непредвиденная ошибка");
	}
}