package ru.otus.krivonos.integration.mail;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "mail")
public class MailData {
	private String host;
	private int port;
	private String protocol;
	private String user;
	private String password;
	private String from;
	private String encoding;
	private boolean smtpAuth;
	private boolean smtpStartTlsEnable;
	private boolean debug;

	public void setHost(String host) {
		this.host = host;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public void setSmtpAuth(boolean smtpAuth) {
		this.smtpAuth = smtpAuth;
	}

	public void setSmtpStartTlsEnable(boolean smtpStartTlsEnable) {
		this.smtpStartTlsEnable = smtpStartTlsEnable;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	public String getHost() {
		return host;
	}

	public int getPort() {
		return port;
	}

	public String getProtocol() {
		return protocol;
	}

	public String getUser() {
		return user;
	}

	public String getPassword() {
		return password;
	}

	public String getFrom() {
		return from;
	}

	public String getEncoding() {
		return encoding;
	}

	public boolean isSmtpAuth() {
		return smtpAuth;
	}

	public boolean isSmtpStartTlsEnable() {
		return smtpStartTlsEnable;
	}

	public boolean isDebug() {
		return debug;
	}
}