package ru.otus.krivonos.exam.domain;

public interface IOService {
	void printMessage(String message) throws IOServiceException;
	String readMessage() throws IOServiceException;
}
