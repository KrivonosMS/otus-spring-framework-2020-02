package ru.otus.krivonos.exam.infrastructore;

import au.com.bytecode.opencsv.CSVReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.otus.krivonos.exam.config.ApplicationProperties;
import ru.otus.krivonos.exam.domain.ExamRepository;
import ru.otus.krivonos.exam.domain.ExamRepositoryException;
import ru.otus.krivonos.exam.domain.model.CheckList;
import ru.otus.krivonos.exam.domain.model.CheckListCreationException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Repository
public class CsvFileExamRepository implements ExamRepository {
	public static final Logger LOG = LoggerFactory.getLogger(CsvFileExamRepository.class);

	private final String filePath;
	private final double successPercentResult;

	public CsvFileExamRepository(ApplicationProperties applicationProperties) {
		this.filePath = applicationProperties.getQuestionFilePath();
		this.successPercentResult = applicationProperties.getSuccessPercentResult();
	}

	@Override
	public CheckList obtainTest() throws ExamRepositoryException {
		LOG.debug("method=obtainTest action=\"получение теста\"");

		try (CSVReader csvReader = new CSVReader(new InputStreamReader(getClass().getResourceAsStream(filePath), StandardCharsets.UTF_8), ',')) {
			List<String[]> rows = csvReader.readAll();

			LOG.debug("method=obtainTest action=\"получение теста\" fileRowSize={}", rows.size());

			CheckList checkList = CheckList.createInstanceFrom(rows, successPercentResult);

			LOG.debug("method=obtainTest action=\"завершение получения теста\" fileRowSize={}", rows.size());

			return checkList;
		} catch (FileNotFoundException e) {
			throw new ExamRepositoryException("Не найден файл с тестовыми заданиями", e);
		} catch (IOException e) {
			throw new ExamRepositoryException("Ошибка во время чтения файла с тестовыми заданиями", e);
		} catch (CheckListCreationException e) {
			throw new ExamRepositoryException("Ошибка во время формирования тестовых заданий", e);
		} catch (Exception e) {
			throw new ExamRepositoryException("Непредвиденная ошибка во время получения тестовых заданий", e);
		}
	}
}
