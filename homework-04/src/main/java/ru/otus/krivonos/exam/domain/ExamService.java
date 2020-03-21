package ru.otus.krivonos.exam.domain;

public interface ExamService {
	void startExam(String username) throws ExamServiceException;
}
