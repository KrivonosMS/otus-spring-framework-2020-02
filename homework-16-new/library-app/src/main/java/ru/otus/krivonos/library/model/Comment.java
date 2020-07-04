package ru.otus.krivonos.library.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "COMMENT")
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(name = "COMMENT", nullable = false)
	private String text;
	@Column(name = "CREATION_DATE", nullable = false)
	private LocalDateTime creationDate;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BOOK_ID",  nullable = false)
	private Book book;

	public Comment(Book book, String text) {
		this.book = book;
		this.text = text;
		this.creationDate = LocalDateTime.now();
	}
}
