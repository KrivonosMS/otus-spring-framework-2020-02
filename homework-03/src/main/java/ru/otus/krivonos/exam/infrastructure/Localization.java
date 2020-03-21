package ru.otus.krivonos.exam.infrastructure;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Component
@ConfigurationProperties(prefix = "application")
public class Localization {
	private String localization;

	public Locale locale() {
		Locale locale = new Locale("ru", "rU");
		List<String> localeList = Arrays.asList(localization.split("_"));
		if(!localeList.isEmpty() && localeList.size() > 1) {
			locale = new Locale(localeList.get(0), localeList.get(1));
		}
		return locale;
	}

	public String getLocalization() {
		return localization;
	}

	public void setLocalization(String localization) {
		this.localization = localization;
	}
}
