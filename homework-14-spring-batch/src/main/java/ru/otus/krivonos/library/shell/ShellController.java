package ru.otus.krivonos.library.shell;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
@RequiredArgsConstructor
public class ShellController {
	private final Job migrationJob;
	private final JobLauncher jobLauncher;

	@SneakyThrows
	@ShellMethod(value = "start_migration-to-mongo-db", key = "start migration")
	public void startMigrationJobWithJobLauncher() {
		JobExecution execution = jobLauncher.run(migrationJob, new JobParametersBuilder().toJobParameters());
		System.out.println(execution);
	}
}