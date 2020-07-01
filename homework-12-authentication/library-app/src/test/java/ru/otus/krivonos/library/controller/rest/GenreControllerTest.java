package ru.otus.krivonos.library.controller.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.krivonos.library.model.Genre;
import ru.otus.krivonos.library.service.GenreService;

import java.util.Arrays;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser(
	username = "admin",
	authorities = {"AdminRole"}
)
@WebMvcTest(GenreController.class)
public class GenreControllerTest {
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private GenreService genreService;

	@Test
	void shouldReturnListGenres() throws Exception {
		Genre genre = new Genre("GenreType");
		when(genreService.findAllGenres()).thenReturn(Arrays.asList(genre));

		mockMvc.perform(get("/library/genre/all"))
			.andExpect(status().isOk())
			.andExpect(content().string(containsString("GenreType")));
	}

	@Test
	void shouldCreateBook() throws Exception {
		mockMvc.perform(post("/library/genre/add")
			.param("genreType", "genre"))
			.andExpect(status().isOk())
			.andExpect(content().string(containsString("\"success\":true")));

		verify(genreService, times(1)).createGenre("genre");
	}

	@Test
	void shouldEditBook() throws Exception {
		mockMvc.perform(post("/library/genre/2/edit")
			.param("genreType", "genre"))
			.andExpect(status().isOk())
			.andExpect(content().string(containsString("\"success\":true")));

		verify(genreService, times(1)).updateGenre(2, "genre");
	}
}