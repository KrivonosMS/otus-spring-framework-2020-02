package ru.otus.krivonos.library.service;

import ru.otus.krivonos.library.exception.OutputServiceException;

public interface OutputService {
	void printText(String message) throws OutputServiceException;
}