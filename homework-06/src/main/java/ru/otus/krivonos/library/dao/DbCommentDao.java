package ru.otus.krivonos.library.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.krivonos.library.domain.Comment;
import ru.otus.krivonos.library.exception.CommentDaoException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class DbCommentDao implements CommentDao {
	@PersistenceContext
	private EntityManager em;

	@Override
	@Transactional(rollbackFor = CommentDaoException.class)
	public long addBookComment(Comment comment) throws CommentDaoException {
		if (comment == null) {
			throw new CommentDaoException("Не задан комментарий для сохранения");
		}
		try {
			em.persist(comment);
			return comment.getId();
		} catch (Exception e) {
			throw new CommentDaoException("Возникла непредвиденная ошибка при сохранении комментария", e);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<Comment> findAllCommentsBy(long bookId) throws CommentDaoException {
		try {
			TypedQuery<Comment> query = em.createQuery("select c from Comment c where c.bookId = :bookId", Comment.class);
			query.setParameter("bookId", bookId);
			return query.getResultList();
		} catch (Exception e) {
			throw new CommentDaoException("Возникла непредвиденная ошибка при получении списка комментариев для книги с id=" + bookId, e);
		}
	}

	@Override
	@Transactional(rollbackFor = CommentDaoException.class)
	public void deleteCommentById(long id) throws CommentDaoException {
		try {
			Query query = em.createQuery("delete from Comment c where c.id = :id");
			query.setParameter("id", id);
			query.executeUpdate();
		} catch (Exception e) {
			throw new CommentDaoException("Возникла непредвиденная ошибка при удалении комментария с id=" + id, e);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public boolean isExist(long id) throws CommentDaoException {
		try {
			Query query = em.createQuery("select c from Comment c where c.id = :id", Comment.class);
			query.setParameter("id", id);
			return !query.getResultList().isEmpty();
		} catch (Exception e) {
			throw new CommentDaoException("Возникла непредвиденная ошибка проверки наличия комментария с id=" + id, e);
		}
	}
}