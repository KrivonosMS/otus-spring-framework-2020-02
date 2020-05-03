package ru.otus.krivonos.library.controller.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.krivonos.library.model.Author;
import ru.otus.krivonos.library.model.Book;
import ru.otus.krivonos.library.model.Genre;
import ru.otus.krivonos.library.service.BookService;

import java.util.Arrays;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
class BookControllerTest {
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private BookService bookService;

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
}