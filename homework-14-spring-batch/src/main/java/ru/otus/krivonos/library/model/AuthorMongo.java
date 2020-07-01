package ru.otus.krivonos.library.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Id;

@Document(collection = "author")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorMongo {
	@Id
	private String id;

	@Field
	private String name;
}