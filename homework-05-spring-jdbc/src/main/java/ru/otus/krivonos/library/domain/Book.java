package ru.otus.krivonos.library.domain;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
public class Book {
	private long id;
	private final BookTitle title;
	private final Author author;
	private final Genre genre;

	public long id() {
		return id;
	}

	public BookTitle title() {
		return title;
	}

	public Author author() {
		return author;
	}

	public Genre genre() {
		return genre;
	}

	@Override
	public String toString() {
		return "Книга{" +
			"id=" + id +
			", название='" + title.value() + "'" +
			", автор='" + author.name() + "'" +
			", жанр='" + genre.type() + "'" +
			'}';
	}
}
