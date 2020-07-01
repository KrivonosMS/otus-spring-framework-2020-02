package ru.otus.krivonos.library.controller.rest.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.krivonos.library.model.Author;

@Data
@NoArgsConstructor
public class AuthorDTO {
	private long id;
	private String name;

	private AuthorDTO(long id, String name) {
		this.id = id;
		this.name = name;
	}

	public static AuthorDTO toDto(Author author) {
		return new AuthorDTO(author.getId(), author.getName());
	}
}