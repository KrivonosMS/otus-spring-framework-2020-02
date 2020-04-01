package ru.otus.krivonos.library.domain;

import lombok.RequiredArgsConstructor;

import java.util.Objects;

@RequiredArgsConstructor
public class Author {
	private final String name;

	public String name() {
		return name;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Author author = (Author) o;
		return Objects.equals(name, author.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}
}
