package ru.otus.krivonos.library.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "genre")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GenreMongo {
	@Id
	private String id;
	@Field
	private String type;
}