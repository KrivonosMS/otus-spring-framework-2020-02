package ru.otus.krivonos.library.controller.rest.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.krivonos.library.model.Book;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class BookAndCommentsDTO {
	private long id;
	private String title;
	private AuthorDTO author;
	private GenreDTO genre;
	private List<CommentDTO> comments;

	public BookAndCommentsDTO(long id, String title, AuthorDTO author, GenreDTO genre, List<CommentDTO> comments) {
		this.id = id;
		this.title = title;
		this.author = author;
		this.genre = genre;
		this.comments = comments;
	}

	public static BookAndCommentsDTO toDto(Book book) {
		return new BookAndCommentsDTO(
			book.getId(),
			book.getTitle(),
			AuthorDTO.toDto(book.getAuthor()),
			GenreDTO.toDto(book.getGenre()),
			book.getComments().stream().map(CommentDTO::toDto).collect(Collectors.toList())
		);
	}
}
