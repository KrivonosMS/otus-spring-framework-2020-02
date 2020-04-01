package ru.otus.krivonos.exam.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import ru.otus.krivonos.exam.domain.TestRepository;
import ru.otus.krivonos.exam.domain.model.CheckList;
import ru.otus.krivonos.exam.domain.model.Localization;
import ru.otus.krivonos.exam.domain.model.Result;
import ru.otus.krivonos.exam.infrastructure.MessagePrinter;
import ru.otus.krivonos.exam.infrastructure.ScanReader;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

class ApplicationServiceTest {
	@Mock
	private TestRepository repository;
	@Mock
	private ScanReader scanReader;
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
		when(repository.obtainTest(any())).thenReturn(checkList);
		when(scanReader.nextLine())
			.thenReturn("test_user")
			.thenReturn("answer1")
			.thenReturn("ans")
			.thenReturn("answer3");

		applicationService.startTest("ru");

		Result result = Result.createInstanceFrom("test_user", 50, (double) 2/3);
		verify(messagePrinter, times(1)).printSuccessResult(Localization.RU, result);
	}

}