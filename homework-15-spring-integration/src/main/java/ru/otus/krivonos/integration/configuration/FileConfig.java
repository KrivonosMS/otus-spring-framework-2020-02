package ru.otus.krivonos.integration.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.file.FileReadingMessageSource;
import org.springframework.integration.file.dsl.Files;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.messaging.MessageChannel;

import java.io.File;

@Configuration
@ConfigurationProperties(prefix = "directory")
public class FileConfig {
	private String inputFolder;

	@Bean
	public JavaMailSenderImpl mailSender() {
		return new JavaMailSenderImpl();
	}

	@Bean
	@InboundChannelAdapter(value = "fileInputChannel", poller = @Poller(fixedDelay = "1000"))
	public FileReadingMessageSource fileReadingMessageSource() {
		FileReadingMessageSource reader = new FileReadingMessageSource();
		reader.setDirectory(new File(inputFolder));
		return reader;
	}

	@Bean
	public MessageChannel fileInboundResultChannel() {
		return MessageChannels.direct().get();
	}

	@Bean
	public IntegrationFlow processDownloaded() {
		return IntegrationFlows
			.from("fileInputChannel")
			.transform(Files.toStringTransformer())
			.channel("fileInboundResultChannel")
			.get();
	}

	@Bean
	public IntegrationFlow showOnConsole() {
		return f -> f.channel("fileInboundResultChannel")
			.enrichHeaders(h -> h.<String>headerFunction("target", m -> m.getPayload().split(":")[0]))
			.enrichHeaders(h -> h.<String>headerFunction("subject", m -> m.getPayload().split(":")[1]))
			.<String, String>transform(s -> s.substring(s.lastIndexOf(":")))
			.channel("sendMailChannel");
	}

	public void setInputFolder(String inputFolder) {
		this.inputFolder = inputFolder;
	}
}