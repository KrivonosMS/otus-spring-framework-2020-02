package ru.otus.krivonos.exam.domain;

import ru.otus.krivonos.exam.domain.model.Result;

public interface ExamService {
	Result startExam(String username) throws ExamServiceException;
}
