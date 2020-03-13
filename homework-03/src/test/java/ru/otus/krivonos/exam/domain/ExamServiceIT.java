package ru.otus.krivonos.exam.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.krivonos.exam.domain.model.Result;
import ru.otus.krivonos.exam.infrastructore.ScanReader;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest()
class ExamServiceIT {
	@Autowired
	private ExamRepository examRepository;
	@MockBean
	private ScanReader scanReader;

	@Test
	void startTestWhenRussianLocale() throws Exception {
		Locale.setDefault(Locale.ENGLISH);
		when(scanReader.nextLine())
			.thenReturn("Москва")
			.thenReturn("Луна")
			.thenReturn("Гагарин")
			.thenReturn("ф")
			.thenReturn("е");
		ExamService examService = new ExamService(examRepository, scanReader);

		Result result = examService.startExam("test_use");

		assertTrue(result.isSuccess());
		assertEquals(60.0, result.userPercentResult());
	}

	@Test
	void startTestWhenEnglishLocale() throws Exception {
		Locale.setDefault(Locale.ENGLISH);
		when(scanReader.nextLine())
			.thenReturn("Moscow")
			.thenReturn("a")
			.thenReturn("a")
			.thenReturn("a")
			.thenReturn("d");
		ExamService examService = new ExamService(examRepository, scanReader);

		Result result = examService.startExam("test_use");

		assertFalse(result.isSuccess());
		assertEquals(20.0, result.userPercentResult());
	}
}