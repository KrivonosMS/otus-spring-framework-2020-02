package ru.otus.krivonos.library.controller.rest;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.krivonos.library.controller.rest.dto.GenreDTO;
import ru.otus.krivonos.library.model.Genre;
import ru.otus.krivonos.library.service.GenreService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/library")
@RequiredArgsConstructor
public class GenreController {
    public static final Logger LOG = LoggerFactory.getLogger(GenreController.class);

    private final GenreService genreService;

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
}
