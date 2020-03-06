package ru.otus.krivonos.exam.infrastructore;

import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class WindowsConsoleScanReader implements ScanReader {
	private static final String CHAR_SET = "UTF-8";
	private final Scanner scanner = new Scanner(System.in, CHAR_SET);

	@Override
	public String nextLine() {
		return scanner.nextLine();
	}

	@Override
	public void close() {
		scanner.close();
	}
}
