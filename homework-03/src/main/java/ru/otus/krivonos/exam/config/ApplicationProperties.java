package ru.otus.krivonos.exam.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "application")
public class ApplicationProperties {
	private String filePathQuestions;
	private double successPercentResult;
	private String localization;

	public String getFilePathQuestions() {
		return filePathQuestions;
	}

	public double getSuccessPercentResult() {
		return successPercentResult;
	}

	public String getLocalization() {
		return localization;
	}

	public void setFilePathQuestions(String filePathQuestions) {
		this.filePathQuestions = filePathQuestions;
	}

	public void setSuccessPercentResult(double successPercentResult) {
		this.successPercentResult = successPercentResult;
	}

	public void setLocalization(String locale) {
		this.localization = locale;
	}
}
