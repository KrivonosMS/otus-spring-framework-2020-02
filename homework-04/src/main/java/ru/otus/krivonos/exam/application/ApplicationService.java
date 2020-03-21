package ru.otus.krivonos.exam.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.otus.krivonos.exam.config.ApplicationProperties;
import ru.otus.krivonos.exam.domain.ExamServiceException;
import ru.otus.krivonos.exam.domain.ExamServiceImpl;
import ru.otus.krivonos.exam.domain.IOService;
import ru.otus.krivonos.exam.domain.MessageRepository;
import ru.otus.krivonos.exam.domain.model.ExamRepository;

@Service
public class ApplicationService {
	public static final Logger LOG = LoggerFactory.getLogger(ApplicationService.class);

	private final ExamRepository repository;
	private final MessageRepository messageRepository;
	private final IOService ioService;
	private final ApplicationProperties applicationProperties;

	public ApplicationService(ExamRepository repository, MessageRepository messageRepository, IOService ioService, ApplicationProperties applicationProperties) {
		this.repository = repository;
		this.ioService = ioService;
		this.messageRepository = messageRepository;
		this.applicationProperties = applicationProperties;
	}

	public void startTest(String username) throws ApplicationServiceException {
		try {
			LOG.debug("method=startTest action=\"проведение тестирование\"");

			ExamServiceImpl service = new ExamServiceImpl(repository, messageRepository, ioService, applicationProperties.getSuccessPercentResult());
			service.startExam(username);

			LOG.debug("method=startTest action=\"завершение проведение тестирование\"");
		} catch (ExamServiceException e) {
			throw new ApplicationServiceException("Возникла непредвиденная ошика во время проведения тестирования", e);
		}
	}
}
