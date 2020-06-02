package ru.otus.krivonos.library.domain;

import lombok.RequiredArgsConstructor;

import java.util.Objects;

@RequiredArgsConstructor
public class BookTitle {
	private final String title;

	public String value() {
		return title;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		BookTitle bookTitle = (BookTitle) o;
		return Objects.equals(title, bookTitle.title);
	}

	@Override
	public int hashCode() {
		return Objects.hash(title);
	}
}
