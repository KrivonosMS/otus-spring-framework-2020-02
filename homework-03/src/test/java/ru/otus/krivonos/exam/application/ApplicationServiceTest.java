package ru.otus.krivonos.exam.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.otus.krivonos.exam.domain.ExamRepository;
import ru.otus.krivonos.exam.domain.IOService;
import ru.otus.krivonos.exam.domain.model.CheckList;
import ru.otus.krivonos.exam.domain.model.Result;
import ru.otus.krivonos.exam.infrastructore.MessagePrinter;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

class ApplicationServiceTest {
	@Mock
	private ExamRepository repository;
	@Mock
	private IOService scanReader;
	@Mock
	private MessagePrinter messagePrinter;
	@InjectMocks
	private ApplicationService applicationService;

	@BeforeEach
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void startTestWhenOk() throws Exception {
		List<String[]> rows = new ArrayList<>();
		rows.add(new String[]{"question1", "answer1"});
		rows.add(new String[]{"question2", "answer2"});
		rows.add(new String[]{"question3", "answer3"});
		CheckList checkList = CheckList.createInstanceFrom(rows, 50);
		when(repository.obtainTest()).thenReturn(checkList);
		when(scanReader.readMessage())
			.thenReturn("test_user")
			.thenReturn("answer1")
			.thenReturn("ans")
			.thenReturn("answer3");

		applicationService.startTest();

		Result result = Result.createInstanceFrom("test_user", 50, (double) 2 / 3);
		verify(messagePrinter, times(1)).printSuccessResult(result);
	}
}