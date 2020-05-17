package ru.otus.krivonos.library.service;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.krivonos.library.dao.BookRepository;
import ru.otus.krivonos.library.dao.CommentRepository;
import ru.otus.krivonos.library.exception.CommentServiceException;
import ru.otus.krivonos.library.model.Book;
import ru.otus.krivonos.library.model.Comment;

@AllArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {
    public static final Logger LOG = LoggerFactory.getLogger(CommentServiceImpl.class);

    private final CommentRepository commentRepository;
    private final BookRepository bookRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Comment addBookComment(long bookId, String text) {
        if (text == null || "".equals(text.trim())) {
            throw new CommentServiceException("Не задан комментарий для сохранения");
        }
        long startTime = System.currentTimeMillis();
        LOG.debug("method=addBookComment action=\"сохранение комментария к книге\" bookId={}", bookId);
        LOG.trace("method=addBookComment action=\"сохранение комментария к книге\" bookId={} text={}", bookId, text);

			Book book = bookRepository.findById(bookId).orElseThrow(() -> new CommentServiceException("Не найдена книга для добавления комментария"));
			Comment comment = new Comment(book, text);
			commentRepository.save(comment);

        long endTime = System.currentTimeMillis();
        LOG.debug("method=addBookComment action=\"комментарий сохранен\" bookId={} time={}ms", bookId, endTime - startTime);

        return comment;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteCommentById(long id) {
        long startTime = System.currentTimeMillis();
        LOG.debug("method=deleteCommentById action=\"удаление комментария\" id={}", id);

        if (commentRepository.existsById(id)) {
            commentRepository.deleteById(id);
        } else {
            throw new CommentServiceException("Отсутствует комментарий для удаления");
        }

        long endTime = System.currentTimeMillis();
        LOG.debug("method=deleteCommentById action=\"комментарий удален\" id={} time={}ms", id, endTime - startTime);
    }
}