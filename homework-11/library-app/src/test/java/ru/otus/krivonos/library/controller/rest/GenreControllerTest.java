package ru.otus.krivonos.library.controller.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest
@AutoConfigureWebTestClient
class GenreControllerTest {
	@Autowired
	private WebTestClient webClient;

	@Test
	void shouldReturnAllGenres() {
		webClient.get()
			.uri("/library/genre/all")
			.exchange()
			.expectStatus()
			.isOk()
			.expectBody()
			.json("[{\"id\":\"1\",\"type\":\"Классическая проза\"},{\"id\":\"2\",\"type\":\"Литература 19 века\"},{\"id\":\"3\",\"type\":\"Русская классика\"},{\"id\":\"4\",\"type\":\"Древнерусская литература\"}]");
	}
}