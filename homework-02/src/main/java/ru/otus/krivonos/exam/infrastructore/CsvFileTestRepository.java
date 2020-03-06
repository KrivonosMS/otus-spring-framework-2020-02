package ru.otus.krivonos.exam.infrastructore;

import au.com.bytecode.opencsv.CSVReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Repository;
import ru.otus.krivonos.exam.domain.TestRepository;
import ru.otus.krivonos.exam.domain.TestRepositoryException;
import ru.otus.krivonos.exam.domain.model.CheckList;
import ru.otus.krivonos.exam.domain.model.CheckListCreationException;
import ru.otus.krivonos.exam.domain.model.Localization;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

@PropertySource("classpath:application.properties")
@Repository
public class CsvFileTestRepository implements TestRepository {
	public static final Logger LOG = LoggerFactory.getLogger(CsvFileTestRepository.class);

	private final String testCsvFilePathResource;

	private final double successResultPercent;

	public CsvFileTestRepository(@Value("${file.path.questions}") String testCsvFilePathResource,  @Value("${success.percent.result}") double successResultPercent) {
		this.testCsvFilePathResource = testCsvFilePathResource;
		this.successResultPercent = successResultPercent;
	}

	@Override
	public CheckList obtainTest(Localization localizationType) throws TestRepositoryException {
		LOG.debug("method=obtainTest action=\"получение теста\" localizationType={}", localizationType);

		try (CSVReader csvReader = new CSVReader(new InputStreamReader(getClass().getResourceAsStream(testCsvFilePathResource.replace("{catalog}", localizationType.questionCatalog())), StandardCharsets.UTF_8), ',')) {
			List<String[]> rows = csvReader.readAll();

			LOG.debug("method=obtainTest action=\"получение теста\" fileRowSize={}", rows.size());

			CheckList checkList = CheckList.createInstanceFrom(rows, successResultPercent);

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
