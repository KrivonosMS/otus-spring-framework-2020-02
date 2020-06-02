package ru.otus.krivonos.library.controller.rest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import ru.otus.krivonos.library.controller.rest.dto.BookDTO;

@SpringBootTest
@AutoConfigureWebTestClient
public class BookControllerTest {
	@Autowired
	private WebTestClient webClient;

	@Test
	void shouldReturnAllBooks() {
		webClient.get()
			.uri("/library/book/all")
			.exchange()
			.expectStatus()
			.isOk()
			.expectBody()
			.json("[{\"id\":\"1\",\"title\":\"Повести Белкина (сборник)\",\"author\":{\"name\":\"Александр Пушкин\"},\"genre\":{\"id\":\"2\",\"type\":\"Литература 19 века\"}},{\"id\":\"2\",\"title\":\"Евгений Онегин\",\"author\":{\"name\":\"Александр Пушкин\"},\"genre\":{\"id\":\"1\",\"type\":\"Классическая проза\"}},{\"id\":\"3\",\"title\":\"Сказка о царе Салтане\",\"author\":{\"name\":\"Александр Пушкин\"},\"genre\":{\"id\":\"4\",\"type\":\"Древнерусская литература\"}},{\"id\":\"4\",\"title\":\"Вечера на хуторе близ Диканьки\",\"author\":{\"name\":\"Николай Гоголь\"},\"genre\":{\"id\":\"1\",\"type\":\"Классическая проза\"}},{\"id\":\"5\",\"title\":\"Петербургские повести\",\"author\":{\"name\":\"Николай Гоголь\"},\"genre\":{\"id\":\"3\",\"type\":\"Русская классика\"}},{\"id\":\"6\",\"title\":\"Федор Тютчев: Стихи\",\"author\":{\"name\":\"Фёдор Тютчев\"},\"genre\":{\"id\":\"1\",\"type\":\"Классическая проза\"}},{\"id\":\"7\",\"title\":\"Федор Тютчев: Стихи детям\",\"author\":{\"name\":\"Фёдор Тютчев\"},\"genre\":{\"id\":\"1\",\"type\":\"Классическая проза\"}}]");
	}

	@Test
	void shouldReturnOkWhenAddBook() {
		BookDTO bookDTO = new BookDTO("title", "author", "2", "Литература 19 века");

		webClient.post()
			.uri("/library/book/add")
			.contentType(MediaType.APPLICATION_JSON)
			.body(BodyInserters.fromObject(bookDTO))
			.exchange()
			.expectStatus()
			.isOk()
			.expectBody()
			.json("{\"success\":true,\"message\":\"\"}");
	}

	@Test
	void shouldReturnOkWhenUpdateBook() {
		BookDTO bookDTO = new BookDTO("title", "author", "2", "Литература 19 века");

		webClient.post()
			.uri("/library/book/1/edit")
			.contentType(MediaType.APPLICATION_JSON)
			.body(BodyInserters.fromObject(bookDTO))
			.exchange()
			.expectStatus()
			.isOk()
			.expectBody()
			.json("{\"success\":true,\"message\":\"\"}");
	}

	@Test
	void shouldReturnOkWhenDeleteBook() {
		webClient.post()
			.uri("/library/book/1/delete")
			.exchange()
			.expectStatus()
			.isOk()
			.expectBody()
			.json("{\"success\":true,\"message\":\"\"}");
	}
}