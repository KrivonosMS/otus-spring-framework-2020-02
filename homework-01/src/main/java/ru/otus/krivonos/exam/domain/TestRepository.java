package ru.otus.krivonos.exam.domain;

import ru.otus.krivonos.exam.domain.model.CheckList;

public interface TestRepository {
	CheckList obtainTest() throws TestRepositoryException;
}
