package ru.otus.krivonos.exam.infrastructure;

public interface ScanReader {
	String nextLine();

	void close();
}