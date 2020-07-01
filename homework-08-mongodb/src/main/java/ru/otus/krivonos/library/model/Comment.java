package ru.otus.krivonos.library.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
	private String text;
	private LocalDateTime creationDate;

	public Comment(String text) {
		this.text = text;
		this.creationDate = LocalDateTime.now();
	}
}
