package ru.otus.krivonos.library.controller.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.krivonos.library.model.Genre;
import ru.otus.krivonos.library.service.GenreService;

import java.util.Arrays;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
}