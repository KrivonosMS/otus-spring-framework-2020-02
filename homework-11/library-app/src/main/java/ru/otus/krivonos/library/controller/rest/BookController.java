package ru.otus.krivonos.library.controller.rest;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.krivonos.library.controller.rest.dto.BookDTO;
import ru.otus.krivonos.library.controller.rest.dto.Result;
import ru.otus.krivonos.library.dao.BookRepository;
import ru.otus.krivonos.library.exception.MainException;
import ru.otus.krivonos.library.model.Book;
import ru.otus.krivonos.library.service.BookService;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/library")
@RequiredArgsConstructor
public class BookController {
	public static final Logger LOG = LoggerFactory.getLogger(BookController.class);

	private final BookRepository bookRepository;

	private final BookService bookService;

	@GetMapping("/book/all")
	public Flux<Book> allBooks() {
		LOG.debug("method=allBooks \"запрос на получение всех книг\"");

		return bookRepository.findAll();

	}

	@PostMapping("/book/{id}/delete")
	public Mono<Result> deleteBook(
		@PathVariable("id") @NotNull String id
	) {
		LOG.debug("method=deleteBook \"запрос на удаление книги с id={}\"", id);

		Mono<Void> result = bookRepository.deleteById(id);
		return result.then(Mono.just(Result.ok()));
	}

	@PostMapping(value = "/book/add", consumes = MediaType.APPLICATION_JSON_VALUE)
	public Mono<Result> createBook(
		@RequestBody BookDTO bookDTO
	) {
		String bookTitle = bookDTO.getTitle();
		String authorName = bookDTO.getAuthor();
		String genreId = bookDTO.getGenreId();
		String genreType = bookDTO.getGenreType();

		LOG.debug("method=editBook \"добавление новой книги\" bookTitle={} authorName={} genreId={} genreType={}", bookTitle, authorName, genreId, genreType);

		Mono<Book> result = bookService.createBook(bookTitle, authorName, genreId, genreType);
		return result.then(Mono.just(Result.ok()));
	}

	@PostMapping(value = "/book/{id}/edit", consumes = MediaType.APPLICATION_JSON_VALUE)
	public Mono<Result> editBook(
		@PathVariable("id") @NotNull String bookId,
		@RequestBody BookDTO bookDTO
	) {
		String bookTitle = bookDTO.getTitle();
		String authorName = bookDTO.getAuthor();
		String genreId = bookDTO.getGenreId();
		String genreType = bookDTO.getGenreType();

		LOG.debug("method=editBook \"обновление книги с id={}\" bookTitle={} authorName={} genreId={} genreType={}", bookId, bookTitle, authorName, genreId, genreType);

		Mono<Book> result = bookService.updateBook(bookId, bookTitle, authorName, genreId, genreType);
		return result.then(Mono.just(Result.ok()));
	}

	@ExceptionHandler(MainException.class)
	public Mono<Result> handleMainException(MainException exc) {
		LOG.debug("method=handleMainException", exc);

		return Mono.just(Result.error(exc.getMessage()));
	}

	@ExceptionHandler(Exception.class)
	public Mono<Result> handleException(Exception exc) {
		LOG.debug("method=handleException", exc);

		return Mono.just(Result.error("Непредвиденная ошибка"));
	}
}