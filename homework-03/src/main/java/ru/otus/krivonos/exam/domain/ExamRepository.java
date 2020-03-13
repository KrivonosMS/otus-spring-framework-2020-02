package ru.otus.krivonos.exam.domain;

import ru.otus.krivonos.exam.domain.model.CheckList;

public interface ExamRepository {
	CheckList obtainTest() throws ExamRepositoryException;
}
