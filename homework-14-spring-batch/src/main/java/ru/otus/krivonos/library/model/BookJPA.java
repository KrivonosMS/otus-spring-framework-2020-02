package ru.otus.krivonos.library.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "BOOK")
public class BookJPA {
	@Id
	@Column(name = "ID", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(name = "TITLE", nullable = false)
	private String title;
	@ManyToOne(targetEntity = AuthorJPA.class, cascade = {CascadeType.DETACH, CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "AUTHOR_ID")
	private AuthorJPA author;
	@ManyToOne(targetEntity = GenreJPA.class, cascade = {CascadeType.DETACH, CascadeType.REFRESH})
	@JoinColumn(name = "GENRE_ID")
	private GenreJPA genre;

	public BookMongo convert() {
		return new BookMongo(
			String.valueOf(this.id),
			this.title,
			this.author.convert(),
			this.genre.convert()
		);
	}
}
