package ru.otus.krivonos.exam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.krivonos.exam.application.ApplicationService;
import ru.otus.krivonos.exam.application.ApplicationServiceException;

import java.util.Scanner;

public class Application {
	public static final Logger LOG = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
		ApplicationService applicationService = context.getBean(ApplicationService.class);
		try (Scanner scanner = new Scanner(System.in, "cp866")) {
			applicationService.startTest(scanner);
		} catch (ApplicationServiceException e) {
			LOG.error("Возникла ошибка в работе приложения", e);
		} catch (Exception e) {
			LOG.error("Возникла непредвиденная ошибка", e);
		}
	}
}
