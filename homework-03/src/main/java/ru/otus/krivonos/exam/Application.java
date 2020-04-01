package ru.otus.krivonos.exam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.otus.krivonos.exam.application.ApplicationService;
import ru.otus.krivonos.exam.application.ApplicationServiceException;

@SpringBootApplication
public class Application {
	public static final Logger LOG = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		try {
			ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
			ApplicationService applicationService = context.getBean(ApplicationService.class);
			applicationService.startTest();
		} catch (ApplicationServiceException e) {
			LOG.error("Возникла ошибка в работе приложения", e);
		} catch (Exception e) {
			LOG.error("Возникла непредвиденная ошибка", e);
		}
	}
}
