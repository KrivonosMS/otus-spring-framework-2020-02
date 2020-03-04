package ru.otus.krivonos.exam.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.krivonos.exam.domain.TestRepository;
import ru.otus.krivonos.exam.domain.TestRepositoryException;
import ru.otus.krivonos.exam.domain.model.*;
import ru.otus.krivonos.exam.infrastructore.ScanReader;

import java.util.HashMap;
import java.util.Map;

public class ApplicationService {
	public static final Logger LOG = LoggerFactory.getLogger(ApplicationService.class);

	private final TestRepository repository;
	private final ScanReader scanReader;

	public ApplicationService(TestRepository repository, ScanReader scanReader) {
		this.repository = repository;
		this.scanReader = scanReader;
	}

	public void startTest() throws ApplicationServiceException {
		try {
			LOG.debug("method=startTest action=\"проведение тестирование\"");

			CheckList checkList = repository.obtainTest();
			greeting();
			System.out.println("Пожалуйста, введите свое имя: ");
			String username = scanReader.nextLine();
			Map<QuestionNumber, Question> questions = checkList.unmodifiableQuestions();
			Map<QuestionNumber, String> answers = new HashMap<>();
			for (QuestionNumber questionNumber : questions.keySet()) {
				System.out.println(questions.get(questionNumber).question());
				answers.put(questionNumber, scanReader.nextLine());
			}
			PersonAnswers personAnswers = PersonAnswers.createInstanceFrom(answers, username);
			double result = checkList.calculateResult(personAnswers);
			System.out.printf("%s, Вы успешно ответили на %.0f%% вопросов.", personAnswers.username(), result * 100);

			LOG.debug("method=startTest action=\"завершение проведение тестирование\" username={} result={}", username, result);
		} catch (TestRepositoryException e) {
			throw new ApplicationServiceException("Произошла ошибка во время формирования тестового задания", e);
		} catch (PersonAnswersCreationException e) {
			throw new ApplicationServiceException("Произошла ошибка во время формирования объекта с пользовательскими ответами на вопросы тестирования", e);
		} catch (CalculatedResultException e) {
			throw new ApplicationServiceException("Произошла ошибка во время вычисления результатов тестирования", e);
		} finally {
			scanReader.close();
		}
	}

	private void greeting() {
		System.out.println("================================================================================");
		System.out.println("             Вас приветствует программа тестроивания студентов.");
		System.out.println("================================================================================");
	}
}
