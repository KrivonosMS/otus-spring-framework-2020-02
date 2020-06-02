package ru.otus.krivonos.exam.domain;

public class UsernameNotAvailable extends Exception {
	UsernameNotAvailable(String message) {
		super(message);
	}
}
