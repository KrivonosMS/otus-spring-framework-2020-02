package ru.otus.krivonos.exam.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.otus.krivonos.exam.config.ApplicationProperties;
import ru.otus.krivonos.exam.domain.ExamService;
import ru.otus.krivonos.exam.domain.ExamServiceException;

@Service
public class ApplicationService {
	public static final Logger LOG = LoggerFactory.getLogger(ApplicationService.class);

	private final ExamService examService;
	private final ApplicationProperties applicationProperties;

	public ApplicationService(ExamService examService, ApplicationProperties applicationProperties) {
		this.examService = examService;
		this.applicationProperties = applicationProperties;
	}

	public void startTest(String username) throws ApplicationServiceException {
		try {
			LOG.debug("method=startTest action=\"проведение тестирование\"");

			examService.startExam(username, applicationProperties.getSuccessPercentResult());

			LOG.debug("method=startTest action=\"завершение проведение тестирование\"");
		} catch (ExamServiceException e) {
			throw new ApplicationServiceException("Возникла непредвиденная ошика во время проведения тестирования", e);
		}
	}
}
