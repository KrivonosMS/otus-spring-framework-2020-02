package ru.otus.krivonos.library.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.krivonos.library.model.Comment;
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
	public long addBookComment(Comment comment) throws CommentDaoException {
		try {
			em.persist(comment);
			return comment.getId();
		} catch (Exception e) {
			throw new CommentDaoException("Возникла непредвиденная ошибка при сохранении комментария", e);
		}
	}

	@Override
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