package ru.otus.krivonos.exam.infrastructore;

import au.com.bytecode.opencsv.CSVReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.krivonos.exam.domain.TestRepository;
import ru.otus.krivonos.exam.domain.TestRepositoryException;
import ru.otus.krivonos.exam.domain.model.CheckList;
import ru.otus.krivonos.exam.domain.model.CheckListCreationException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class CsvFileTestRepository implements TestRepository {
	public static final Logger LOG = LoggerFactory.getLogger(CsvFileTestRepository.class);

	private final String testCsvFilePathResource;

	public CsvFileTestRepository(String testCsvFilePathResource) {
		this.testCsvFilePathResource = testCsvFilePathResource;
	}

	@Override
	public CheckList obtainTest() throws TestRepositoryException {
		LOG.debug("method=obtainTest action=\"получение теста\"");

		try (CSVReader csvReader = new CSVReader(new InputStreamReader(getClass().getResourceAsStream(testCsvFilePathResource), StandardCharsets.UTF_8), ',')) {
			List<String[]> rows = csvReader.readAll();

			LOG.debug("method=obtainTest action=\"получение теста\" fileRowSize={}", rows.size());

			CheckList checkList = CheckList.createInstanceFrom(rows);

			LOG.debug("method=obtainTest action=\"завершение получения теста\" fileRowSize={}", rows.size());

			return checkList;
		} catch (FileNotFoundException e) {
			throw new TestRepositoryException("Не найден файл с тестовыми заданиями", e);
		} catch (IOException e) {
			throw new TestRepositoryException("Ошибка во время чтения файла с тестовыми заданиями", e);
		} catch (CheckListCreationException e) {
			throw new TestRepositoryException("Ошибка во время формирования тестовых заданий", e);
		} catch (Exception e) {
			throw new TestRepositoryException("Непредвиденная ошибка во время получения тестовых заданий", e);
		}
	}
}
