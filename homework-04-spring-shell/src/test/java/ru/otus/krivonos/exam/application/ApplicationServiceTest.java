package ru.otus.krivonos.exam.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import ru.otus.krivonos.exam.config.ApplicationProperties;
import ru.otus.krivonos.exam.domain.ExamService;
import ru.otus.krivonos.exam.domain.ExamServiceImpl;
import ru.otus.krivonos.exam.domain.IOService;
import ru.otus.krivonos.exam.domain.MessageRepository;
import ru.otus.krivonos.exam.domain.model.CheckList;
import ru.otus.krivonos.exam.domain.model.ExamRepository;
import ru.otus.krivonos.exam.domain.model.Result;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

class ApplicationServiceTest {
	@Mock
	private ExamRepository repository;
	@Mock
	private MessageRepository messageRepository;
	@Mock
	private IOService scanReader;
	@InjectMocks
	private ExamServiceImpl examService;

	@BeforeEach
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void startTestWhenSuccessResult() throws Exception {
		List<String[]> rows = new ArrayList<>();
		rows.add(new String[]{"question1", "answer1"});
		rows.add(new String[]{"question2", "answer2"});
		rows.add(new String[]{"question3", "answer3"});
		CheckList checkList = CheckList.createInstanceFrom(rows);
		when(repository.obtainTest()).thenReturn(checkList);
		when(scanReader.readMessage())
			.thenReturn("answer1")
			.thenReturn("ans")
			.thenReturn("answer3");

		examService.startExam("test_user", 50.0);

		Result result = Result.createInstanceFrom("test_user", 50, (double) 2 / 3);
		verify(messageRepository, times(1)).successResultMessage(result);
	}

	@Test
	void startTestWhenBadResult() throws Exception {
		List<String[]> rows = new ArrayList<>();
		rows.add(new String[]{"question1", "answer1"});
		rows.add(new String[]{"question2", "answer2"});
		rows.add(new String[]{"question3", "answer3"});
		CheckList checkList = CheckList.createInstanceFrom(rows);
		when(repository.obtainTest()).thenReturn(checkList);
		when(scanReader.readMessage())
			.thenReturn("answer1")
			.thenReturn("ans")
			.thenReturn("ans");

		examService.startExam("test_user", 50.0);

		Result result = Result.createInstanceFrom("test_user", 50, (double) 1 / 3);
		verify(messageRepository, times(1)).badResultMessage(result);
	}
}