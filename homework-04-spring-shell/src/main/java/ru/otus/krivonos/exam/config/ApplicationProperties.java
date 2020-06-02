package ru.otus.krivonos.exam.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "application")
public class ApplicationProperties {
	private String questionFilePath;
	private double successThresholdPercent;

	public String getQuestionFilePath() {
		return questionFilePath;
	}

	public double getSuccessPercentResult() {
		return successThresholdPercent;
	}

	public void setQuestionFilePath(String questionFilePath) {
		this.questionFilePath = questionFilePath;
	}

	public void setSuccessThresholdPercent(double successThresholdPercent) {
		this.successThresholdPercent = successThresholdPercent;
	}
}
