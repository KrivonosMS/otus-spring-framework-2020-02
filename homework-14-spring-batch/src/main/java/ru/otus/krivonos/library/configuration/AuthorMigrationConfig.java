package ru.otus.krivonos.library.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.builder.MongoItemWriterBuilder;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.krivonos.library.dao.AuthorRepository;
import ru.otus.krivonos.library.model.AuthorJPA;
import ru.otus.krivonos.library.model.AuthorMongo;

import java.util.HashMap;
import java.util.List;

@Configuration
public class AuthorMigrationConfig {
	private final static int CHUNK_SIZE = 10;
	public static final Logger LOG = LoggerFactory.getLogger(AuthorMigrationConfig.class);

	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@StepScope
	@Bean
	ItemReader<AuthorJPA> authorReader(AuthorRepository authorRepository) {
		return new RepositoryItemReaderBuilder<AuthorJPA>()
			.name("authorReader")
			.sorts(new HashMap<>())
			.repository(authorRepository)
			.methodName("findAll")
			.build();
	}

	@StepScope
	@Bean
	ItemProcessor<AuthorJPA, AuthorMongo> authorProcessor() {
		return AuthorJPA::convert;
	}

	@StepScope
	@Bean
	ItemWriter<AuthorMongo> authorWriter() {
		return new MongoItemWriterBuilder<AuthorMongo>()
			.collection("author")
			.template(mongoTemplate)
			.build();
	}

	@Bean
	public Step authorMigration(ItemReader<AuthorJPA> authorReader, ItemProcessor<AuthorJPA, AuthorMongo> authorProcessor, ItemWriter<AuthorMongo> authorWriter) {
		return stepBuilderFactory
			.get("authorMigration")
			.<AuthorJPA, AuthorMongo>chunk(CHUNK_SIZE)
			.reader(authorReader)
			.processor(authorProcessor)
			.writer(authorWriter)
			.listener(new ItemReadListener() {
				public void beforeRead() {
					LOG.debug("Начало чтения автора");
				}

				public void afterRead(Object o) {
					LOG.debug("Конец чтения автора");
				}

				public void onReadError(Exception e) {
					LOG.warn("Ошибка чтения автора", e);
				}
			})
			.listener(new ItemWriteListener() {
				public void beforeWrite(List list) {
					LOG.debug("Начало записи автора");
				}

				public void afterWrite(List list) {
					LOG.debug("Конец записи автора");
				}

				public void onWriteError(Exception e, List list) {
					LOG.warn("Ошибка записи автора", e);
				}
			})
			.listener(new ItemProcessListener<AuthorJPA, AuthorMongo>() {
				public void beforeProcess(AuthorJPA o) {
					LOG.debug("Начало обработки автора");
				}

				public void afterProcess(AuthorJPA o, AuthorMongo o2) {
					LOG.debug("Конец обработки автора");
				}

				public void onProcessError(AuthorJPA o, Exception e) {
					LOG.warn("Ошбка обработки автора", e);
				}
			})
			.listener(new ChunkListener() {
				public void beforeChunk(ChunkContext chunkContext) {
					LOG.debug("Начало пачки авторов");
				}

				public void afterChunk(ChunkContext chunkContext) {
					LOG.debug("Конец пачки авторов");
				}

				public void afterChunkError(ChunkContext chunkContext) {
					LOG.warn("Ошибка пачки авторов");
				}
			})
			.build();
	}
}