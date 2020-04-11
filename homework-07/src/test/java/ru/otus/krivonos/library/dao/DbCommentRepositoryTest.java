package ru.otus.krivonos.library.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.krivonos.library.model.Comment;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Transactional
class DbCommentRepositoryTest {
	@Autowired
	private CommentRepository commentRepository;
	@Autowired
	private TestEntityManager em;

	@Test
	void shouldSaveComment() {
		Comment expectedComment = new Comment(1, "новый комментарий");

		commentRepository.save(expectedComment);

		em.detach(expectedComment);
		assertThat(expectedComment.getId()).isGreaterThan(0);
		Comment actual = em.find(Comment.class, expectedComment.getId());
		assertThat(actual)
			.isNotNull()
			.isEqualToIgnoringGivenFields(expectedComment, "creationDate");
	}

	@Test
	void shouldReturnTrueIfCommentExist() {
		boolean isCommentExist = commentRepository.existsById(1l);

		assertThat(isCommentExist).isTrue();
	}

	@Test
	void shouldReturnFalseIfCommentExist() {
		boolean isCommentExist = commentRepository.existsById(-1l);

		assertThat(isCommentExist).isFalse();
	}

	@Test
	void shouldDeleteComment() {
		Comment comment = em.find(Comment.class, 3l);
		em.detach(comment);

		commentRepository.deleteById(comment.getId());

		assertThat(em.find(Comment.class, comment.getId())).isNull();
	}
}