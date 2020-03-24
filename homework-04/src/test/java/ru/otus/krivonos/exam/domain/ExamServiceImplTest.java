package ru.otus.krivonos.exam.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.krivonos.exam.domain.model.ExamRepository;
import ru.otus.krivonos.exam.domain.model.Result;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@DisplayName("проверяет сервис проведения экзамена")
class ExamServiceImplTest {
	@Autowired
	private ExamRepository examRepository;
	@MockBean
	private IOService scanReader;
	@MockBean
	private MessageRepository messageRepository;

	@Autowired
	private ExamService examService;

	@Test
	@DisplayName("тест должен быть пройден успешно пройден с результатом 60%")
	void shouldGetSuccessfulMessageAfterTesting() throws Exception {
		when(scanReader.readMessage())
			.thenReturn("Москва")
			.thenReturn("Луна")
			.thenReturn("Гагарин")
			.thenReturn("ф")
			.thenReturn("е");

		examService.startExam("Миша", 50.0);

		verify(messageRepository, times(1)).successResultMessage(Result.createInstanceFrom("Миша", 50, (double) 3 / 5));
	}

	@Test
	@DisplayName("должно бросаться доменное исключение, если имя пользователя null")
	void shouldThrowExamServiceExceptionWhenUsernameIsNull() throws Exception {
		ExamServiceException exception = Assertions.assertThrows(ExamServiceException.class, () -> {
			examService.startExam(null, 10);
		});

		assertEquals("Не задано имя экзаминуемого", exception.getMessage());
	}

	@Test
	@DisplayName("должно бросаться доменное исключение, если имя пользователя пусто")
	void shouldThrowExamServiceExceptionWhenUsernameIsEmpty() throws Exception {
		ExamServiceException exception = Assertions.assertThrows(ExamServiceException.class, () -> {
			examService.startExam("", 10);
		});

		assertEquals("Не задано имя экзаминуемого", exception.getMessage());
	}
}