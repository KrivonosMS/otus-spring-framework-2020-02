package ru.otus.krivonos.exam.domain;

public interface ExamService {
	void startExam(String username, double s) throws ExamServiceException;
}
