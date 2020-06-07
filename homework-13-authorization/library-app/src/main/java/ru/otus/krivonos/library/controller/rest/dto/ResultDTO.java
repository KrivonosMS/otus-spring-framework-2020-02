package ru.otus.krivonos.library.controller.rest.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResultDTO {
	private boolean success;
	private String message;

	public ResultDTO(boolean success, String message) {
		this.success = success;
		this.message = message;
	}

	public static ResultDTO ok() {
		return new ResultDTO(true, "");
	}

	public static ResultDTO error(String message) {
		return new ResultDTO(false, message);
	}
}