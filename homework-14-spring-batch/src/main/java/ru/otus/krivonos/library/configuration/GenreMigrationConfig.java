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
import ru.otus.krivonos.library.dao.GenreRepository;
import ru.otus.krivonos.library.model.GenreJPA;
import ru.otus.krivonos.library.model.GenreMongo;

import java.util.HashMap;
import java.util.List;

@Configuration
public class GenreMigrationConfig {
	private final static int CHUNK_SIZE = 10;
	public static final Logger LOG = LoggerFactory.getLogger(GenreMigrationConfig.class);

	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@StepScope
	@Bean
	ItemReader<GenreJPA> genreReader(GenreRepository genreRepository) {
		return new RepositoryItemReaderBuilder<GenreJPA>()
			.name("genreReader")
			.sorts(new HashMap<>())
			.repository(genreRepository)
			.methodName("findAll")
			.build();
	}

	@StepScope
	@Bean
	ItemProcessor<GenreJPA, GenreMongo> genreProcessor() {
		return GenreJPA::convert;
	}

	@StepScope
	@Bean
	ItemWriter<GenreMongo> genreWriter() {
		return new MongoItemWriterBuilder<GenreMongo>()
			.collection("genre")
			.template(mongoTemplate)
			.build();
	}

	@Bean
	public Step genreMigration(ItemReader<GenreJPA> genreReader, ItemProcessor<GenreJPA, GenreMongo> genreProcessor, ItemWriter<GenreMongo> genreWriter) {
		return stepBuilderFactory
			.get("genreMigration")
			.<GenreJPA, GenreMongo>chunk(CHUNK_SIZE)
			.reader(genreReader)
			.processor(genreProcessor)
			.writer(genreWriter)
			.listener(new ItemReadListener() {
				public void beforeRead() {
					LOG.debug("Начало чтения литературного жанра");
				}

				public void afterRead(Object o) {
					LOG.debug("Конец чтения литературного жанра");
				}

				public void onReadError(Exception e) {
					LOG.warn("Ошибка чтения литературного жанра", e);
				}
			})
			.listener(new ItemWriteListener() {
				public void beforeWrite(List list) {
					LOG.debug("Начало записи литературного жанра");
				}

				public void afterWrite(List list) {
					LOG.debug("Конец записи литературного жанра");
				}

				public void onWriteError(Exception e, List list) {
					LOG.warn("Ошибка записи литературного жанра", e);
				}
			})
			.listener(new ItemProcessListener<GenreJPA, GenreMongo>() {
				public void beforeProcess(GenreJPA o) {
					LOG.debug("Начало обработки литературного жанра");
				}

				public void afterProcess(GenreJPA o, GenreMongo o2) {
					LOG.debug("Конец обработки литературного жанра");
				}

				public void onProcessError(GenreJPA o, Exception e) {
					LOG.warn("Ошбка обработки литературного жанра", e);
				}
			})
			.listener(new ChunkListener() {
				public void beforeChunk(ChunkContext chunkContext) {
					LOG.debug("Начало пачки литературных жанров");
				}

				public void afterChunk(ChunkContext chunkContext) {
					LOG.debug("Конец пачки литературных жанров");
				}

				public void afterChunkError(ChunkContext chunkContext) {
					LOG.warn("Ошибка пачки литературных жанров");
				}
			})
			.build();
	}
}