package ru.otus.krivonos.library.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.krivonos.library.dao.BookRepository;
import ru.otus.krivonos.library.dao.CommentRepository;
import ru.otus.krivonos.library.exception.CommentServiceException;
import ru.otus.krivonos.library.model.Book;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class CommentServiceImplTest {
	@Mock
	private BookRepository bookRepository;
	@Mock
	private CommentRepository commentRepository;
	@InjectMocks
	private CommentServiceImpl libraryService;

	@Test
	void shouldThrowServiceExceptionWhenSaveCommentWhichIsNull() {
		CommentServiceException exception = Assertions.assertThrows(CommentServiceException.class, () -> {
			libraryService.addBookComment(1, null);
		});

		assertEquals("Не задан комментарий для сохранения", exception.getInfo());
	}

	@Test
	void shouldThrowServiceExceptionWhenSaveCommentWhichIsEmpty() {
		CommentServiceException exception = Assertions.assertThrows(CommentServiceException.class, () -> {
			libraryService.addBookComment(1, "");
		});

		assertEquals("Не задан комментарий для сохранения", exception.getInfo());
	}

	@Test
	void shouldThrowServiceExceptionWhenSaveCommentAndBookIsNotExist() throws Exception {
		when(bookRepository.findById(1l)).thenReturn(Optional.empty());
		CommentServiceException exception = Assertions.assertThrows(CommentServiceException.class, () -> {
			libraryService.addBookComment(1, "тестовый комментарий");
		});

		assertThat(exception).hasMessage("Не найдена книга для добавления комментария");
		verify(commentRepository, never()).save(any());
	}

	@Test
	void shouldSaveComment() throws Exception {
		when(bookRepository.findById(1l)).thenReturn(Optional.of(mock(Book.class)));
		libraryService.addBookComment(1, "тестовый комментарий");

		verify(commentRepository, times(1)).save(any());
	}

	@Test
	void shouldThrowServiceExceptionWhenDeleteCommentWhichIsNotExist() throws Exception {
		when(commentRepository.existsById(1l)).thenReturn(false);
		CommentServiceException exception = Assertions.assertThrows(CommentServiceException.class, () -> {
			libraryService.deleteCommentById(1l);
		});

		assertThat(exception).hasMessage("Отсутствует комментарий для удаления");
		verify(commentRepository, never()).deleteById(1l);
	}

	@Test
	void shouldDeleteComment() throws Exception {
		when(commentRepository.existsById(1l)).thenReturn(true);
		libraryService.deleteCommentById(1l);

		verify(commentRepository, times(1)).deleteById(1l);
	}
}