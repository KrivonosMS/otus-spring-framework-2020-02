package ru.otus.krivonos.library.configuration;

import lombok.RequiredArgsConstructor;
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
import ru.otus.krivonos.library.dao.BookRepository;
import ru.otus.krivonos.library.model.BookJPA;
import ru.otus.krivonos.library.model.BookMongo;

import java.util.HashMap;
import java.util.List;


@Configuration
public class BookMigrationConfig {
	private final static int CHUNK_SIZE = 10;
	public static final Logger LOG = LoggerFactory.getLogger(BookMigrationConfig.class);

	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@StepScope
	@Bean
	ItemReader<BookJPA> bookReader(BookRepository bookRepository) {
		return new RepositoryItemReaderBuilder<BookJPA>()
			.name("bookReader")
			.sorts(new HashMap<>())
			.repository(bookRepository)
			.methodName("findAll")
			.build();
	}

	@StepScope
	@Bean
	ItemProcessor<BookJPA, BookMongo> bookProcessor() {
		return BookJPA::convert;
	}

	@StepScope
	@Bean
	ItemWriter<BookMongo> bookWriter() {
		return new MongoItemWriterBuilder<BookMongo>()
			.collection("book")
			.template(mongoTemplate)
			.build();
	}

	@Bean
	public Step bookMigration(ItemReader<BookJPA> bookReader, ItemProcessor<BookJPA, BookMongo> bookProcessor, ItemWriter<BookMongo> bookWriter) {
		return stepBuilderFactory
			.get("genreMigration")
			.<BookJPA, BookMongo>chunk(CHUNK_SIZE)
			.reader(bookReader)
			.processor(bookProcessor)
			.writer(bookWriter)
			.listener(new ItemReadListener() {
				public void beforeRead() {
					LOG.debug("Начало чтения книги");
				}

				public void afterRead(Object o) {
					LOG.debug("Конец чтения книги");
				}

				public void onReadError(Exception e) {
					LOG.warn("Ошибка чтения книги", e);
				}
			})
			.listener(new ItemWriteListener() {
				public void beforeWrite(List list) {
					LOG.debug("Начало записи книги");
				}

				public void afterWrite(List list) {
					LOG.debug("Конец записи книги");
				}

				public void onWriteError(Exception e, List list) {
					LOG.warn("Ошибка записи книги", e);
				}
			})
			.listener(new ItemProcessListener<BookJPA, BookMongo>() {
				public void beforeProcess(BookJPA o) {
					LOG.debug("Начало обработки книги");
				}

				public void afterProcess(BookJPA o, BookMongo o2) {
					LOG.debug("Конец обработки книги");
				}

				public void onProcessError(BookJPA o, Exception e) {
					LOG.warn("Ошбка обработки книги", e);
				}
			})
			.listener(new ChunkListener() {
				public void beforeChunk(ChunkContext chunkContext) {
					LOG.debug("Начало пачки книг");
				}

				public void afterChunk(ChunkContext chunkContext) {
					LOG.debug("Конец пачки книг");
				}

				public void afterChunkError(ChunkContext chunkContext) {
					LOG.warn("Ошибка пачки книг");
				}
			})
			.build();
	}
}