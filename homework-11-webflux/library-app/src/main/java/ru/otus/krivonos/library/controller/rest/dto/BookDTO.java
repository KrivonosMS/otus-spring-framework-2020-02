package ru.otus.krivonos.library.controller.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookDTO {
	private String title;
	private String author;
	private String genreId;
	private String genreType;
}
