package ru.otus.krivonos.library.controller.rest.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Result {
	private boolean success;
	private String message;

	public Result(boolean success, String message) {
		this.success = success;
		this.message = message;
	}

	public static Result ok() {
		return new Result(true, "");
	}

	public static Result error(String message) {
		return new Result(false, message);
	}
}