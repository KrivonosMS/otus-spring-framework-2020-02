package ru.otus.krivonos.exam.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.otus.krivonos.exam.domain.DomainException;
import ru.otus.krivonos.exam.domain.ExamRepository;
import ru.otus.krivonos.exam.domain.ExamService;
import ru.otus.krivonos.exam.domain.model.*;
import ru.otus.krivonos.exam.infrastructore.MessagePrinter;
import ru.otus.krivonos.exam.infrastructore.ScanReader;

import java.util.HashMap;
import java.util.Map;

@Service
public class ApplicationService {
	public static final Logger LOG = LoggerFactory.getLogger(ApplicationService.class);

	private final ExamRepository repository;
	private final ScanReader scanReader;
	private final MessagePrinter messagePrinter;

	public ApplicationService(ExamRepository repository, ScanReader scanReader, MessagePrinter messagePrinter) {
		this.repository = repository;
		this.scanReader = scanReader;
		this.messagePrinter = messagePrinter;
	}

	public void startTest() throws ApplicationServiceException {
		try {
			LOG.debug("method=startTest action=\"проведение тестирование\"");

			ExamService service = new ExamService(repository, scanReader);
			messagePrinter.printGreeting();
			messagePrinter.printAskName();
			String username = scanReader.nextLine();
			Result result = service.startExam(username);
			if (result.isSuccess()) {
				messagePrinter.printSuccessResult(result);
			} else {
				messagePrinter.printBadResult(result);
			}

			LOG.debug("method=startTest action=\"завершение проведение тестирование\" username={} result={}", username, result);
		} catch (DomainException e) {
			throw new ApplicationServiceException(e.getInfo(), e);
		} finally {
			scanReader.close();
		}
	}
}
