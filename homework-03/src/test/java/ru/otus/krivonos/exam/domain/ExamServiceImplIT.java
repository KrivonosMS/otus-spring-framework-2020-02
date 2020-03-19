package ru.otus.krivonos.exam.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.krivonos.exam.domain.model.ExamRepository;
import ru.otus.krivonos.exam.domain.model.Result;

import static org.mockito.Mockito.*;

@SpringBootTest
@DisplayName("проверяет сервис проведения экзамена")
class ExamServiceImplIT {
	@Autowired
	private ExamRepository examRepository;
	@MockBean
	private IOService scanReader;
	@MockBean
	private MessageRepository messageRepository;

	@Test
	@DisplayName("тест должен быть пройден успешно пройден с результатом 60%")
	void shouldGetSuccessfulMessageAfterTesting() throws Exception {
		when(scanReader.readMessage())
			.thenReturn("Миша")
			.thenReturn("Москва")
			.thenReturn("Луна")
			.thenReturn("Гагарин")
			.thenReturn("ф")
			.thenReturn("е");
		ExamServiceImpl examService = new ExamServiceImpl(examRepository, messageRepository,scanReader,50);

		examService.startExam();

		verify(messageRepository, times(1)).successResultMessage(Result.createInstanceFrom("Миша", 50, (double) 3/5));
	}
}