package ru.otus.krivonos.integration.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.mail.MailHeaders;
import org.springframework.integration.mail.dsl.Mail;
import org.springframework.integration.scheduling.PollerMetadata;
import org.springframework.messaging.MessageChannel;
import ru.otus.krivonos.integration.mail.MailData;
import ru.otus.krivonos.integration.mail.StringToMimeTransformer;

@Configuration
public class MailConfig {
	private StringToMimeTransformer toMimeTransformer;
	private MailData mailData;

	public MailConfig(StringToMimeTransformer toMimeTransformer, MailData mailData) {
		this.toMimeTransformer = toMimeTransformer;
		this.mailData = mailData;
	}

	@Bean
	public MessageChannel sendMailChannel() {
		return MessageChannels.queue().datatype(String.class).get();
	}

	@Bean(name = PollerMetadata.DEFAULT_POLLER)
	@Primary
	public PollerMetadata poller() {
		return Pollers.fixedRate(500).get();
	}

	@Bean
	IntegrationFlow sendMailFlow() {
		return IntegrationFlows.from("sendMailChannel")
			.enrichHeaders(h -> h.header(MailHeaders.FROM, mailData.getFrom()))
			.transform(toMimeTransformer)
			.handle(Mail.outboundAdapter(mailData.getHost())
					.port(mailData.getPort())
					.protocol(mailData.getProtocol())
					.credentials(mailData.getUser(), mailData.getPassword())
					.defaultEncoding(mailData.getEncoding())
					.javaMailProperties(p -> {
						p.put("mail.smtp.auth", mailData.isSmtpAuth());
						p.put("mail.smtp.starttls.enable", mailData.isSmtpStartTlsEnable());
						p.put("mail.debug", mailData.isDebug());
					}),
				e -> e.id("sendMailEndpoint")).get();
	}
}