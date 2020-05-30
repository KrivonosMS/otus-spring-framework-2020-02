package ru.otus.krivonos.library.controller.rest;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.krivonos.library.controller.rest.dto.BookAndCommentsDTO;
import ru.otus.krivonos.library.controller.rest.dto.BookDTO;
import ru.otus.krivonos.library.controller.rest.dto.CommentDTO;
import ru.otus.krivonos.library.controller.rest.dto.ResultDTO;
import ru.otus.krivonos.library.exception.MainException;
import ru.otus.krivonos.library.model.Book;
import ru.otus.krivonos.library.model.Comment;
import ru.otus.krivonos.library.service.BookService;
import ru.otus.krivonos.library.service.CommentService;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/library/book")
@RequiredArgsConstructor
public class BookController {
	public static final Logger LOG = LoggerFactory.getLogger(BookController.class);

	private final BookService bookService;
	private final CommentService commentService;

	@GetMapping("/all")
	public List<BookDTO> allBooks() {
		LOG.debug("method=allBooks \"запрос на получение всех книг\"");

		List<Book> books = bookService.findAllBooks();
		List<BookDTO> bookDTOList = books
			.stream()
			.map(BookDTO::toDto)
			.collect(Collectors.toList());

		LOG.debug("method=allBooks \"получен список со всеми книгами библиотеки\" bookDTOListSize={}", bookDTOList.size());

		return bookDTOList;
	}

	@PostMapping("/{id}/delete")
	public ResultDTO deleteBook(
		@PathVariable("id") @NotNull Long id
	) {
		LOG.debug("method=deleteBook \"запрос на удаление книги с id={}\"", id);

		bookService.deleteBookBy(id);

		LOG.debug("method=deleteBook \"удалена книга с id={}\"", id);

		return ResultDTO.ok();
	}

	@PostMapping("/add")
	public ResultDTO createBook(
		@RequestParam("title") String bookTitle,
		@RequestParam("author") String authorName,
		@RequestParam("genreId") @NotNull Long genreId
	) throws IOException {
		LOG.debug("method=createBook \"добавление новой книги\" bookTitle={} authorName={} genreId={}", bookTitle, authorName, genreId);

		bookService.createBook(bookTitle, authorName, genreId);

		LOG.debug("method=createBook \"добавлена новая книга\"");

		return ResultDTO.ok();
	}

	@PostMapping("/{id}/edit")
	public ResultDTO editBook(
		@PathVariable("id") @NotNull Long bookId,
		@RequestParam("title") String bookTitle,
		@RequestParam("author") String authorName,
		@RequestParam("genreId") @NotNull Long genreId
	) {
		LOG.debug("method=editBook \"обновление книги с id={}\" bookTitle={} authorName={} genreId={}", bookId, bookTitle, authorName, genreId);

		bookService.updateBook(bookId, bookTitle, authorName, genreId);

		LOG.debug("method=editBook \"обновлеа книга с bookId={}\"", bookId);

		return ResultDTO.ok();
	}

	@GetMapping("/{id}")
	public BookAndCommentsDTO getBook(
		@PathVariable("id") @NotNull Long bookId
	) {
		LOG.debug("method=getBook \"получение книги по id={}\"", bookId);

		Book book = bookService.findBookBy(bookId);

		LOG.debug("method=getBook \"получена книги по id={}\"", bookId);

		return BookAndCommentsDTO.toDto(book);
	}


	@PostMapping("/{id}/add/comment")
	public CommentDTO addComment(
		@PathVariable("id") @NotNull Long bookId,
		@RequestParam("text") String commentText
	) {
		LOG.debug("method=addComment \"добавить комментарий для книги с id={}\" comment={}", bookId, commentText);

		Comment comment = commentService.addBookComment(bookId, commentText);
		CommentDTO commentDTO = CommentDTO.toDto(comment);

		LOG.debug("method=addComment \"добавлен комментарий для книги с id={}\"", bookId);

		return commentDTO;
	}

	@PostMapping("/delete/comment/{id}")
	public ResultDTO deleteComment(
		@PathVariable("id") @NotNull Long commentId
	) {
		LOG.debug("method=addComment \"удаление комментария с id={}\"", commentId);

		commentService.deleteCommentById(commentId);

		LOG.debug("method=addComment \"удален комментарий с id={}\"", commentId);

		return ResultDTO.ok();
	}

	@ExceptionHandler(MainException.class)
	public ResultDTO handleMainException(MainException ex) {
		LOG.debug("method=handleMainException", ex);

		return ResultDTO.error(ex.getInfo());
	}

	@ExceptionHandler(Exception.class)
	public ResultDTO handleException(Exception ex) {
		LOG.debug("method=handleException", ex);

		return ResultDTO.error("Возникла непредвиденная ошибка");
	}
}