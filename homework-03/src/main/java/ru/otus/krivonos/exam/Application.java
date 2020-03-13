package ru.otus.krivonos.exam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import ru.otus.krivonos.exam.application.ApplicationService;
import ru.otus.krivonos.exam.application.ApplicationServiceException;
import ru.otus.krivonos.exam.config.ApplicationProperties;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@SpringBootApplication
public class Application {
	public static final Logger LOG = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		try {
			ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
			ApplicationService applicationService = context.getBean(ApplicationService.class);
			ApplicationProperties applicationProperties = context.getBean(ApplicationProperties.class);
			setLocale(applicationProperties.getLocalization());
			applicationService.startTest();
		} catch (ApplicationServiceException e) {
			LOG.error("Возникла ошибка в работе приложения", e);
		} catch (Exception e) {
			LOG.error("Возникла непредвиденная ошибка", e);
		}
	}

	private static void setLocale(String locale) {
		List<String> localeList = Arrays.asList(locale.split("_"));
		if(!localeList.isEmpty() && localeList.size() > 1) {
			Locale.setDefault(new Locale(localeList.get(0), localeList.get(1)));
		} else {
			Locale.setDefault(new Locale("ru", "RU"));
		}
	}
}
