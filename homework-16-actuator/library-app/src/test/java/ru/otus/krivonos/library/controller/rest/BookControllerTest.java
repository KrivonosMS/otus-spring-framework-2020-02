package ru.otus.krivonos.library.controller.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.krivonos.library.model.Author;
import ru.otus.krivonos.library.model.Book;
import ru.otus.krivonos.library.model.Comment;
import ru.otus.krivonos.library.model.Genre;
import ru.otus.krivonos.library.service.BookService;
import ru.otus.krivonos.library.service.CommentService;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser(
	username = "admin",
	authorities = {"ROLE_ADMIN"}
)
@WebMvcTest(BookController.class)
class BookControllerTest {
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private BookService bookService;
	@MockBean
	private CommentService commentService;

	@Test
	void shouldReturnListBooks() throws Exception {
		Book book = new Book(1, "TestTitle", new Author("AuthorName"), new Genre("GenreType"));
		when(bookService.findAllBooks()).thenReturn(Arrays.asList(book));

		mockMvc.perform(get("/library/book/all"))
			.andExpect(status().isOk())
			.andExpect(content().string(containsString("TestTitle")))
			.andExpect(content().string(containsString("AuthorName")))
			.andExpect(content().string(containsString("GenreType")));
	}

	@Test
	void shouldReturnBook() throws Exception {
		Comment comment1 = new Comment(null, "test_comment1");
		Comment comment2 = new Comment(null, "test_comment2");
		Book book = new Book(1, "TestTitle", new Author("AuthorName"), new Genre("GenreType"), Arrays.asList(comment1, comment2));
		when(bookService.findBookBy(2)).thenReturn(book);

		mockMvc.perform(get("/library/book/2"))
			.andExpect(status().isOk())
			.andExpect(content().string(containsString("2")))
			.andExpect(content().string(containsString("TestTitle")))
			.andExpect(content().string(containsString("AuthorName")))
			.andExpect(content().string(containsString("test_comment1")))
			.andExpect(content().string(containsString("test_comment2")));
	}

	@Test
	void shouldDeleteBook() throws Exception {
		mockMvc.perform(post("/library/book/1/delete"))
			.andExpect(status().isOk())
			.andExpect(content().string(containsString("\"success\":true")));

		verify(bookService, times(1)).deleteBookBy(1L);
	}

	@Test
	void shouldCreateBook() throws Exception {
		mockMvc.perform(post("/library/book/add")
			.param("title", "TestTitle")
			.param("author", "AuthorName")
			.param("genreId", "1"))
			.andExpect(status().isOk())
			.andExpect(content().string(containsString("\"success\":true")));

		verify(bookService, times(1)).createBook("TestTitle", "AuthorName", 1L);
	}

	@Test
	void shouldEditBook() throws Exception {
		mockMvc.perform(post("/library/book/2/edit")
			.param("title", "TestTitle")
			.param("author", "AuthorName")
			.param("genreId", "1"))
			.andExpect(status().isOk())
			.andExpect(content().string(containsString("\"success\":true")));

		verify(bookService, times(1)).updateBook(2, "TestTitle", "AuthorName", 1L);
	}

	@Test
	void shouldAddComment() throws Exception {
		when(commentService.addBookComment(2, "TestComment")).thenReturn(new Comment(2, "TestComment", LocalDateTime.of(2020,11, 15, 10, 12), new Book()));

		mockMvc.perform(post("/library/book/2/add/comment")
			.param("text", "TestComment"))
			.andExpect(status().isOk())
			.andExpect(content().string(equalTo("{\"id\":2,\"text\":\"TestComment\",\"creationDate\":\"15-11-2020 10:12\"}")));

		verify(commentService, times(1)).addBookComment(2, "TestComment");
	}

	@Test
	void shouldDeleteComment() throws Exception {
		mockMvc.perform(post("/library/book/delete/comment/2"))
			.andExpect(status().isOk())
			.andExpect(content().string(containsString("\"success\":true")));

		verify(commentService, times(1)).deleteCommentById(2);
	}
}