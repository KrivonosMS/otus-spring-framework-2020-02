package ru.otus.krivonos.library.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.krivonos.library.dao.BookDao;
import ru.otus.krivonos.library.dao.CommentDao;
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
	private BookDao bookDao;
	@Mock
	private CommentDao commentDao;
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
		when(bookDao.findBy(1l)).thenReturn(Optional.empty());
		CommentServiceException exception = Assertions.assertThrows(CommentServiceException.class, () -> {
			libraryService.addBookComment(1, "тестовый комментарий");
		});

		assertThat(exception).hasMessage("Не найдена книга для добавления комментария");
		verify(commentDao, never()).addBookComment(any());
	}

	@Test
	void shouldSaveComment() throws Exception {
		when(bookDao.findBy(1l)).thenReturn(Optional.of(mock(Book.class)));
		libraryService.addBookComment(1, "тестовый комментарий");

		verify(commentDao, times(1)).addBookComment(any());
	}

	@Test
	void shouldThrowServiceExceptionWhenDeleteCommentWhichIsNotExist() throws Exception {
		when(commentDao.isExist(1l)).thenReturn(false);
		CommentServiceException exception = Assertions.assertThrows(CommentServiceException.class, () -> {
			libraryService.deleteCommentById(1l);
		});

		assertThat(exception).hasMessage("Отсутствует комментарий для удаления");
		verify(commentDao, never()).deleteCommentById(1l);
	}

	@Test
	void shouldDeleteComment() throws Exception {
		when(commentDao.isExist(1l)).thenReturn(true);
		libraryService.deleteCommentById(1l);

		verify(commentDao, times(1)).deleteCommentById(1l);
	}
}