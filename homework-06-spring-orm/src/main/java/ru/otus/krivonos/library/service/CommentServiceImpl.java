package ru.otus.krivonos.library.service;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.krivonos.library.dao.BookDao;
import ru.otus.krivonos.library.dao.CommentDao;
import ru.otus.krivonos.library.exception.BookDaoException;
import ru.otus.krivonos.library.exception.CommentDaoException;
import ru.otus.krivonos.library.exception.CommentServiceException;
import ru.otus.krivonos.library.model.Book;
import ru.otus.krivonos.library.model.Comment;

import java.util.Optional;

@AllArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {
	public static final Logger LOG = LoggerFactory.getLogger(CommentServiceImpl.class);

	private final CommentDao commentDao;
	private final BookDao bookDao;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void addBookComment(long bookId, String text) throws CommentServiceException {
		if (text == null || "".equals(text.trim())) {
			throw new CommentServiceException("Не задан комментарий для сохранения");
		}
		try {
			long startTime = System.currentTimeMillis();
			LOG.debug("method=addBookComment action=\"сохранение комментария к книге\" bookId={}", bookId);
			LOG.trace("method=addBookComment action=\"сохранение комментария к книге\" bookId={} text={}", bookId, text);

			Optional<Book> optionalBook = bookDao.findBy(bookId);
			if (optionalBook.isPresent()) {
				Comment comment = new Comment(bookId, text);
				commentDao.addBookComment(comment);
			} else {
				throw new CommentServiceException("Не найдена книга для добавления комментария");
			}

			long endTime = System.currentTimeMillis();
			LOG.debug("method=addBookComment action=\"комментарий сохранен\" bookId={} time={}ms", bookId, endTime - startTime);
		} catch (CommentDaoException | BookDaoException e) {
			throw new CommentServiceException("Возникла непредвиденная ошибка при добавлении комментария к книге", e);
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteCommentById(long id) throws CommentServiceException {
		try {
			long startTime = System.currentTimeMillis();
			LOG.debug("method=deleteCommentById action=\"удаление комментария\" id={}", id);

			if (commentDao.isExist(id)) {
				commentDao.deleteCommentById(id);
			} else {
				throw new CommentServiceException("Отсутствует комментарий для удаления");
			}

			long endTime = System.currentTimeMillis();
			LOG.debug("method=deleteCommentById action=\"комментарий удален\" id={} time={}ms", id, endTime - startTime);
		} catch (CommentDaoException e) {
			throw new CommentServiceException("Возникла непредвиденная ошибка при удалении комментария", e);
		}
	}
}