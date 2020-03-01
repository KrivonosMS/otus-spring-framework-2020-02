package ru.otus.krivonos.exam.infrastructore;

public interface ScanReader {
	String nextLine();

	void close();
}