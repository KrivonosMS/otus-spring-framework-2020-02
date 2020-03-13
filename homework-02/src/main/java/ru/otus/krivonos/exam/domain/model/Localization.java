package ru.otus.krivonos.exam.domain.model;

import java.util.Locale;

public enum Localization {
	EN("en", Locale.ENGLISH),
	RU("ru", new Locale("ru"));

	private Locale locale;
	private String questionCatalog;

	private Localization(String questionCatalog, Locale locale) {
		this.questionCatalog = questionCatalog;
		this.locale = locale;
	}

	public String questionCatalog() {
		return this.questionCatalog;
	}

	public Locale locale() {
		return this.locale;
	}

	@Override
	public String toString() {
		return "Localization{" +
			"locale=" + locale +
			", questionCatalog='" + questionCatalog + '\'' +
			'}';
	}
}
