package ru.otus.krivonos.exam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.krivonos.exam.application.ApplicationService;
import ru.otus.krivonos.exam.application.ApplicationServiceException;
import ru.otus.krivonos.exam.domain.model.Localization;

@ComponentScan
@Configuration
public class Application {
	public static final Logger LOG = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		AnnotationConfigApplicationContext context =
			new AnnotationConfigApplicationContext(Application.class);
		ApplicationService applicationService = context.getBean(ApplicationService.class);
		try {
			applicationService.startTest(args.length == 0 ? "ru" :args[0]);
		} catch (ApplicationServiceException e) {
			LOG.error("Возникла ошибка в работе приложения", e);
		} catch (Exception e) {
			LOG.error("Возникла непредвиденная ошибка", e);
		}
	}
}
