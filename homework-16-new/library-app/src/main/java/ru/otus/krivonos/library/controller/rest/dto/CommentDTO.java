package ru.otus.krivonos.library.controller.rest.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.krivonos.library.model.Comment;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class CommentDTO {
	private long id;
	private String text;
	private LocalDateTime creationDate;

	private CommentDTO(long id, String text, LocalDateTime creationDate) {
		this.id = id;
		this.text = text;
		this.creationDate = creationDate;
	}

	public static CommentDTO toDto(Comment comment) {
		return new CommentDTO(comment.getId(), comment.getText(), comment.getCreationDate());
	}

	@JsonFormat(pattern = "dd-MM-yyyy HH:mm")
	public LocalDateTime getCreationDate() {
		return creationDate;
	}
}
