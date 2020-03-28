package ru.otus.krivonos.library.service;

import ru.otus.krivonos.library.domain.Book;

public interface LibraryService {
	void save(String bookTitle, String authorName, String genreType) throws LibraryServiceException;
}
