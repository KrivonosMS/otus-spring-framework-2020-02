package ru.otus.krivonos.library.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.krivonos.library.domain.Comment;
import ru.otus.krivonos.library.exception.CommentDaoException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import(DbCommentDao.class)
class DbCommentDaoTest {
	@Autowired
	private CommentDao commentDao;
	@Autowired
	private TestEntityManager em;

	@Test
	void shouldThrow() {
		CommentDaoException exception = Assertions.assertThrows(CommentDaoException.class, () -> {
			commentDao.addBookComment(null);
		});

		assertEquals("Не задан комментарий для сохранения", exception.getMessage());
	}

	@Test
	void shouldSaveComment() throws Exception {
		Comment expectedComment = new Comment(1, "новый комментарий");

		commentDao.addBookComment(expectedComment);

		em.detach(expectedComment);
		assertThat(expectedComment.getId()).isGreaterThan(0);
		Comment actual = em.find(Comment.class, expectedComment.getId());
		assertThat(actual)
			.isNotNull()
			.isEqualToIgnoringGivenFields(expectedComment, "creationDate");
	}

	@Test
	void shouldReturnTrueIfCommentExist() throws Exception {
		boolean isCommentExist = commentDao.isExist(1l);

		assertThat(isCommentExist).isTrue();
	}

	@Test
	void shouldReturnFalseIfCommentExist() throws Exception {
		boolean isCommentExist = commentDao.isExist(-1l);

		assertThat(isCommentExist).isFalse();
	}

	@Test
	void shouldReturnEmptyCommentList() throws Exception {
		List<Comment> comments = commentDao.findAllCommentsBy(-2l);

		assertThat(comments).isEmpty();
	}

	@Test
	void shouldDeleteComment() throws Exception {
		Comment comment = em.find(Comment.class, 3l);

		commentDao.deleteCommentById(comment.getId());

		em.detach(comment);
		assertThat(em.find(Comment.class, comment.getId())).isNull();
	}
}