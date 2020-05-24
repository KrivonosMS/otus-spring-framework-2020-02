package ru.otus.krivonos.library.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Document(collection = "book")
@Data
@NoArgsConstructor
@ToString
public class Book {
	@Id
	private String id;
	@Field
	private String title;
	@Field
	private Author author;
	@DBRef
	private Genre genre;

	public Book(String id, String title, Author author, Genre genre) {
		this.id = id;
		this.title = title;
		this.author = author;
		this.genre = genre;
	}
}
