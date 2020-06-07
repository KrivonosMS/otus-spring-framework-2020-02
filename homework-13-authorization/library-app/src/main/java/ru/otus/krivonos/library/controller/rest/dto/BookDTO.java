package ru.otus.krivonos.library.controller.rest.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.krivonos.library.model.Book;

import java.util.List;

@Data
@NoArgsConstructor
public class BookDTO {
	private long id;
	private String title;
	private AuthorDTO author;
	private GenreDTO genre;
	private List<CommentDTO> comments;

	public BookDTO(long id, String title, AuthorDTO author, GenreDTO genre) {
		this.id = id;
		this.title = title;
		this.author = author;
		this.genre = genre;
	}

	public static BookDTO toDto(Book book) {
		return new BookDTO(
			book.getId(),
			book.getTitle(),
			AuthorDTO.toDto(book.getAuthor()),
			GenreDTO.toDto(book.getGenre())
		);
	}
}