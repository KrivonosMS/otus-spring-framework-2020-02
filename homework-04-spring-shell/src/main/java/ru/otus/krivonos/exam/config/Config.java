package ru.otus.krivonos.exam.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import ru.otus.krivonos.exam.domain.ExamService;
import ru.otus.krivonos.exam.domain.ExamServiceImpl;
import ru.otus.krivonos.exam.domain.IOService;
import ru.otus.krivonos.exam.domain.MessageRepository;
import ru.otus.krivonos.exam.domain.model.ExamRepository;

@Configuration
public class Config {
	@Bean
	public PropertySourcesPlaceholderConfigurer propertyConfig() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Bean
	public ExamService examService(ExamRepository examRepository, MessageRepository messageRepository, IOService ioService, ApplicationProperties applicationProperties) {
		return new ExamServiceImpl(examRepository, messageRepository, ioService);
	}
}