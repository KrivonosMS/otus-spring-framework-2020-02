package ru.otus.krivonos.exam.domain.model;

public interface ExamRepository {
	CheckList obtainTest() throws ExamRepositoryException;
}
