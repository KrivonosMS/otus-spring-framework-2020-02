package ru.otus.krivonos.exam.infrastructure;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.otus.krivonos.exam.domain.model.CheckList;
import ru.otus.krivonos.exam.domain.model.Question;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@DisplayName("репозиторий экзаминационных вопросов должен")
public class CsvFileExamRepositoryIT {
	@Autowired
	private CsvFileExamRepository fileExamRepository;

	@Test
	@DisplayName("получить количество вопросов равное количеству вопросов в файле")
	void shouldObtainSizeEqualsQuestionAmountFromCsvFile() throws Exception {
		CheckList checkList = fileExamRepository.obtainTest();

		assertEquals(5, checkList.unmodifiableQuestions().size());
	}

	@Test
	@DisplayName("получить все оригинальные вопросы из файла")
	void shouldObtainAllQuestionFromCsvFile() throws Exception {
		CheckList checkList = fileExamRepository.obtainTest();

		List<Question> questions = checkList.unmodifiableQuestions().values().stream().collect(Collectors.toList());
		assertEquals("Столица Российской Федерации?", questions.get(0).question());
		assertEquals("Как называется природный спутник Земли?", questions.get(1).question());
		assertEquals("Фамилия первого космонавта?", questions.get(2).question());
		assertEquals("Самая длинная река в мире?", questions.get(3).question());
		assertEquals("Единица измерения силы тока?", questions.get(4).question());
	}

	@Configuration
	@EnableConfigurationProperties
	@ComponentScan({"ru.otus.krivonos.exam.infrastructure", "ru.otus.krivonos.exam.config"})
	public static class TestContextConfig {
	}
}