package ru.otus.krivonos.library.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "book")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BookMongo {
	@Id
	private String id;
	@Field
	private String title;
	@DBRef
	private AuthorMongo author;
	@DBRef
	private GenreMongo genre;
}
