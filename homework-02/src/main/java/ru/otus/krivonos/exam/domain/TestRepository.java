package ru.otus.krivonos.exam.domain;

import ru.otus.krivonos.exam.domain.model.CheckList;
import ru.otus.krivonos.exam.domain.model.Localization;

public interface TestRepository {
	CheckList obtainTest(Localization localizationType) throws TestRepositoryException;
}
