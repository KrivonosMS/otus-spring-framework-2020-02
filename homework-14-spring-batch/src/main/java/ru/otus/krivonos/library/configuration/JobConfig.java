package ru.otus.krivonos.library.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JobConfig {
	public static final Logger LOG = LoggerFactory.getLogger(JobConfig.class);
	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Bean
	public Job migrationJob(Step genreMigration, Step authorMigration, Step bookMigration) {
		return jobBuilderFactory.get("migration_to_mongodb")
			.incrementer(new RunIdIncrementer())
			.start(genreMigration)
			.next(authorMigration)
			.next(bookMigration)
			.listener(new JobExecutionListener() {
				@Override
				public void beforeJob(JobExecution jobExecution) {
					LOG.info("Начало job");
				}

				@Override
				public void afterJob(JobExecution jobExecution) {
					LOG.info("Конец job");
				}
			})
			.build();
	}
}