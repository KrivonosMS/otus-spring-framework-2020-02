package ru.otus.krivonos.library.controller.rest.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ErrorDTO {
	private boolean success = false;
	private String message;

	public ErrorDTO(String message) {
		this.message = message;
	}
}
