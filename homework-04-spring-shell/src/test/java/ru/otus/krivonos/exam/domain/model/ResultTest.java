package ru.otus.krivonos.exam.domain.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResultTest {
	@Test
	void createInstanceWhenResultIsNotSuccess() throws Exception {
		Result result = Result.createInstanceFrom("test_username", 50.0, 0.33);

		assertFalse(result.isSuccess());
		assertEquals("Result{username='test_username', successResultPercent=50.0, userCorrectAnswersPercent=33.0}", result.toString());
	}

	@Test
	void createInstanceWhenResultIsSuccess() throws Exception {
		Result result = Result.createInstanceFrom("test_username", 50.0, 0.63);

		assertTrue(result.isSuccess());
		assertEquals("Result{username='test_username', successResultPercent=50.0, userCorrectAnswersPercent=63.0}", result.toString());
	}

	@Test
	void createInstanceWhenUserIsNullThenException() {
		ResultCreationException exception = Assertions.assertThrows(ResultCreationException.class, () -> {
			Result.createInstanceFrom(null, 50.0, 0.33);
		});

		assertEquals("Не задан пользователь", exception.getMessage());
	}

	@Test
	void createInstanceWhenUserIsEmptyThenException() {
		ResultCreationException exception = Assertions.assertThrows(ResultCreationException.class, () -> {
			Result.createInstanceFrom("", 50.0, 0.33);
		});

		assertEquals("Не задан пользователь", exception.getMessage());
	}
}