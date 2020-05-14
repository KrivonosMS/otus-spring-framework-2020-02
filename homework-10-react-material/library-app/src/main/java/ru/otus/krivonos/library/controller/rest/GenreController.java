package ru.otus.krivonos.library.controller.rest;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.otus.krivonos.library.controller.rest.dto.GenreDTO;
import ru.otus.krivonos.library.controller.rest.dto.ResultDTO;
import ru.otus.krivonos.library.exception.MainException;
import ru.otus.krivonos.library.model.Genre;
import ru.otus.krivonos.library.service.GenreService;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/library/genre")
@RequiredArgsConstructor
public class GenreController {
    public static final Logger LOG = LoggerFactory.getLogger(GenreController.class);

    private final GenreService genreService;

    @GetMapping("/all")
    public List<GenreDTO> allGenres() {
        LOG.debug("method=allGenres \"запрос на получение списка вссх литературных жанров\"");

        List<Genre> genres = genreService.findAllGenres();
        List<GenreDTO> genreDTOList = genres
                .stream()
                .map(GenreDTO::toDto)
                .collect(Collectors.toList());

        LOG.debug("method=allGenres \"получен список со всеми литературными жанрами\" genreDTOListSize={}", genreDTOList.size());

        return genreDTOList;
    }

    @PostMapping("/add")
    public ResultDTO addGenre(
        @RequestParam("genreType") @NotNull String genreType
    ) {
        LOG.debug("method=addGenre \"добавление нового литературного жанра\" genreType={}", genreType);

        genreService.createGenre(genreType);

        LOG.debug("method=addGenre \"добавлен новый литературный жанр\" genreType={}", genreType);

        return ResultDTO.ok();
    }

    @PostMapping("/{id}/edit")
    public ResultDTO editGenre(
        @RequestParam("genreType") @NotNull String genreType,
        @PathVariable("id") @NotNull long id
    ) {
        LOG.debug("method=editGenre \"обновление литературного жанра\" id={} genreType={}", id, genreType);

        genreService.updateGenre(id, genreType);

        LOG.debug("method=editGenre \"обновлен литературный жанр\" id={} genreType={}", id, genreType);

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
