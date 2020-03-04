package ru.otus.krivonos.exam.infrastructore;

import java.util.Scanner;

public class WindowsConsoleScanReader implements ScanReader {
	private static final String CHAR_SET = "cp866";
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
