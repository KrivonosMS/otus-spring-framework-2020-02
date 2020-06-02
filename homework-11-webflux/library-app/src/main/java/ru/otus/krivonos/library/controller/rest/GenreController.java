package ru.otus.krivonos.library.controller.rest;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import ru.otus.krivonos.library.dao.GenreRepository;
import ru.otus.krivonos.library.model.Genre;

@RestController
@RequestMapping("/library")
@RequiredArgsConstructor
public class GenreController {
	public static final Logger LOG = LoggerFactory.getLogger(GenreController.class);

	private final GenreRepository genreRepository;

	@GetMapping("/genre/all")
	public Flux<Genre> allGenres() {
		LOG.debug("method=allGenres \"запрос на получение списка вссх литературных жанров\"");

		return genreRepository.findAll();
	}
}