package ru.otus.krivonos.library.controller.rest;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.otus.krivonos.library.controller.rest.dto.BookDTO;
import ru.otus.krivonos.library.controller.rest.dto.ErrorDTO;
import ru.otus.krivonos.library.controller.rest.dto.GenreDTO;
import ru.otus.krivonos.library.exception.MainException;
import ru.otus.krivonos.library.model.Book;
import ru.otus.krivonos.library.model.Genre;
import ru.otus.krivonos.library.service.BookService;
import ru.otus.krivonos.library.service.GenreService;

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
	private final GenreService genreService;

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

	@GetMapping("/genre/all")
	public List<GenreDTO> allGenres(Model model) {
		LOG.debug("method=allGenres \"запрос на получение списка вссх литературных жанров\"");

		List<Genre> genres = genreService.findAllGenres();
		List<GenreDTO> genreDTOList = genres
			.stream()
			.map(GenreDTO::toDto)
			.collect(Collectors.toList());

		LOG.debug("method=allGenres \"получен список со всеми литературными жанрами\" genreDTOListSize={}", genreDTOList.size());

		return genreDTOList;
	}

	@PostMapping("/book/{id}/delete")
	public void deleteBook(
		HttpServletResponse response,
		Model model,
		@PathVariable("id") @NotNull Long id
	) throws IOException {
		LOG.debug("method=deleteBook \"запрос на удаление книги с id={}\"", id);

		bookService.deleteBookBy(id);

		LOG.debug("method=deleteBook \"удалена книга с id={}\"", id);

		response.sendRedirect("/library/");
	}

	@PostMapping("/book/add")
	public void createBook(
		HttpServletResponse response,
		@RequestParam("title") String bookTitle,
		@RequestParam("author") String authorName,
		@RequestParam("genreId") @NotNull Long genreId
	) throws IOException {
		LOG.debug("method=createBook \"добавление новой книги\" bookTitle={} authorName={} genreId={}", bookTitle, authorName, genreId);

		bookService.createBook(bookTitle, authorName, genreId);

		LOG.debug("method=createBook \"добавлена новая книга\"");

		response.sendRedirect("/library/");
	}

	@PostMapping("/book/{id}/edit")
	public void editBook(
		HttpServletResponse response,
		@PathVariable("id") @NotNull Long bookId,
		@RequestParam("title") String bookTitle,
		@RequestParam("author") String authorName,
		@RequestParam("genreId") @NotNull Long genreId
	) throws IOException {
		LOG.debug("method=editBook \"обновление книги с id={}\" bookTitle={} authorName={} genreId={}", bookId, bookTitle, authorName, genreId);

		bookService.updateBook(bookId, bookTitle, authorName, genreId);

		LOG.debug("method=editBook \"обновлеа книга с bookId={}\"", bookId);

		response.sendRedirect("/library/");
	}

	@ExceptionHandler(MainException.class)
	public ErrorDTO handleMainException(Model model, MainException ex) {
		LOG.debug("method=handleMainException", ex);

		return new ErrorDTO(ex.getInfo());
	}

	@ExceptionHandler(Exception.class)
	public ErrorDTO handleException(Model model, Exception ex) {
		LOG.debug("method=handleException", ex);

		return new ErrorDTO("Возникла непредвиденная ошибка");
	}
}