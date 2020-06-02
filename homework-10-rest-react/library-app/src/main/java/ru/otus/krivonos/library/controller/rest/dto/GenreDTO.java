package ru.otus.krivonos.library.controller.rest.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.krivonos.library.model.Genre;

@Data
@NoArgsConstructor
public class GenreDTO {
	private long id;
	private String type;

	private GenreDTO(long id, String type) {
		this.id = id;
		this.type = type;
	}

	public static GenreDTO toDto(Genre genre) {
		return new GenreDTO(genre.getId(), genre.getType());
	}
}