package ru.otus.krivonos.library.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
//@AllArgsConstructor
@Entity
@Table(name = "BOOK")
public class Book {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(name = "TITLE", nullable = false)
	private String title;
	@OneToOne(targetEntity = Author.class, cascade = {CascadeType.DETACH, CascadeType.REFRESH})
	@JoinColumn(name = "AUTHOR_ID")
	private Author author;
	@OneToOne(targetEntity = Genre.class, cascade = {CascadeType.DETACH, CascadeType.REFRESH})
	@JoinColumn(name = "GENRE_ID" )
	private Genre genre;

	public Book(String title, Author author, Genre genre) {
		this.title = title;
		this.author = author;
		this.genre = genre;
	}

	public Book(long id, String title, Author author, Genre genre) {
		this.id = id;
		this.title = title;
		this.author = author;
		this.genre = genre;
	}

	@Override
	public String toString() {
		return "Книга{" +
			"id=" + id +
			", название='" + title + "'" +
			", автор=" + author +
			", жанр=" + genre +
			'}';
	}
}
