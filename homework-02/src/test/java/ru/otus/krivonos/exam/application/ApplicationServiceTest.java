package ru.otus.krivonos.exam.application;

import org.junit.jupiter.api.Test;
import ru.otus.krivonos.exam.domain.TestRepository;
import ru.otus.krivonos.exam.domain.model.CheckList;
import ru.otus.krivonos.exam.domain.model.Localization;
import ru.otus.krivonos.exam.domain.model.Result;
import ru.otus.krivonos.exam.infrastructore.MessagePrinter;
import ru.otus.krivonos.exam.infrastructore.ScanReader;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;


class ApplicationServiceTest {
	@Test
	void startTestWhenOk() throws Exception {
		List<String[]> rows = new ArrayList<>();
		rows.add(new String[]{"question1", "answer1"});
		rows.add(new String[]{"question2", "answer2"});
		rows.add(new String[]{"question3", "answer3"});
		CheckList checkList = CheckList.createInstanceFrom(rows, 50);
		TestRepository repository = mock(TestRepository.class);
		when(repository.obtainTest(any())).thenReturn(checkList);
		ScanReader scanReader = mock(ScanReader.class);
		when(scanReader.nextLine())
			.thenReturn("test_user")
			.thenReturn("answer1")
			.thenReturn("ans")
			.thenReturn("answer3");
		MessagePrinter messagePrinter = mock(MessagePrinter.class);
		ApplicationService applicationService = new ApplicationService(repository, scanReader, messagePrinter);

		applicationService.startTest("ru");

		Result result = Result.createInstanceFrom("test_user", 50, (double) 2/3);
		verify(messagePrinter, times(1)).printSuccessResult(Localization.RU, result);
	}

}