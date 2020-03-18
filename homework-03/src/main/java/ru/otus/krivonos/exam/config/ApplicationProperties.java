package ru.otus.krivonos.exam.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "application")
public class ApplicationProperties {
	private String questionFilePath;
	private double successPercentResult;
	private String localization;

	public String getQuestionFilePath() {
		return questionFilePath;
	}

	public double getSuccessPercentResult() {
		return successPercentResult;
	}

	public String getLocalization() {
		return localization;
	}

	public void setQuestionFilePath(String questionFilePath) {
		this.questionFilePath = questionFilePath;
	}

	public void setSuccessPercentResult(double successPercentResult) {
		this.successPercentResult = successPercentResult;
	}

	public void setLocalization(String locale) {
		this.localization = locale;
	}
}
