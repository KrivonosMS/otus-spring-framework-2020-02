package ru.otus.krivonos.library.configuration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.krivonos.library.dao.AuthorMongoRepository;
import ru.otus.krivonos.library.dao.BookMongoRepository;
import ru.otus.krivonos.library.dao.GenreMongoRepository;
import ru.otus.krivonos.library.model.BookMongo;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@SpringBatchTest
class MigrationJobTest {
	@Autowired
	private JobLauncherTestUtils jobLauncherTestUtils;

	@Autowired
	private JobRepositoryTestUtils jobRepositoryTestUtils;

	@Autowired
	private GenreMongoRepository genreMongoRepository;

	@Autowired
	private AuthorMongoRepository authorMongoRepository;

	@Autowired
	private BookMongoRepository bookMongoRepository;

	@BeforeEach
	void clearMetaData() {
		jobRepositoryTestUtils.removeJobExecutions();
	}

	@Test
	void testJob() throws Exception {
		Job job = jobLauncherTestUtils.getJob();
		assertThat(job).isNotNull()
			.extracting(Job::getName)
			.isEqualTo("migration_to_mongodb");
		JobParameters parameters = new JobParametersBuilder().toJobParameters();

		JobExecution jobExecution = jobLauncherTestUtils.launchJob(parameters);

		assertThat(jobExecution.getExitStatus().getExitCode()).isEqualTo("COMPLETED");
		assertThat(genreMongoRepository.findAll().size()).isEqualTo(4);
		assertThat(authorMongoRepository.findAll().size()).isEqualTo(3);
		assertThat(bookMongoRepository.findAll().size()).isEqualTo(7);
		Optional<BookMongo> optionalBookMongo = bookMongoRepository.findById("1");
		assertThat(optionalBookMongo)
			.isNotEmpty()
			.get()
			.matches(bookMongo -> "Александр Пушкин".equals(bookMongo.getAuthor().getName()))
			.matches(bookMongo -> "Литература 19 века".equals(bookMongo.getGenre().getType()))
			.matches(bookMongo -> "Повести Белкина (сборник)".equals(bookMongo.getTitle()));
	}
}