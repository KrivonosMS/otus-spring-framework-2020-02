package ru.otus.krivonos.library.bee.changelog;

import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;
import com.mongodb.DBRef;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@ChangeLog
public class DatabaseChangelog {
	@ChangeSet(order = "001", id = "drop-db", author = "krivonos_ms", runAlways = true)
	public void dropDb(MongoDatabase mongoDatabase) {
		mongoDatabase.drop();
	}

	@ChangeSet(order = "002", id = "add-genres", author = "krivonos_ms", runAlways = true)
	public void insertGenres(MongoDatabase mongoDatabase) {
		MongoCollection<Document> collection = mongoDatabase.getCollection("genre");
		List<List<String>> genres =
			Arrays.asList(
				Arrays.asList("1", "Классическая проза"),
				Arrays.asList("2", "Литература 19 века"),
				Arrays.asList("3", "Русская классика"),
				Arrays.asList("4", "Древнерусская литература")
			);

		genres.stream()
			.forEach(genre -> collection.insertOne(
				new Document("_id", genre.get(0))
					.append("type", genre.get(1))
				)
			);
	}

	@ChangeSet(order = "003", id = "add-books", author = "krivonos_ms", runAlways = true)
	public void insertBooks(MongoDatabase mongoDatabase) {
		MongoCollection<Document> collection = mongoDatabase.getCollection("book");
		List<List<String>> books =
			Arrays.asList(
				Arrays.asList("1", "Повести Белкина (сборник)", "Александр Пушкин", "2", "комментарий 1,комментарий 2"),
				Arrays.asList("2", "Евгений Онегин", "Александр Пушкин", "1", "комментарий 1,комментарий 2,комментарий 3"),
				Arrays.asList("3", "Сказка о царе Салтане", "Александр Пушкин", "4", "комментарий 1"),
				Arrays.asList("4", "Вечера на хуторе близ Диканьки", "Николай Гоголь", "1", "комментарий 1,комментарий 2"),
				Arrays.asList("5", "Петербургские повести", "Николай Гоголь", "3", ""),
				Arrays.asList("6", "Федор Тютчев: Стихи", "Фёдор Тютчев", "1", ""),
				Arrays.asList("7", "Федор Тютчев: Стихи детям", "Фёдор Тютчев", "1", "комментарий 1")
			);

		books.stream()
			.forEach(book -> collection.insertOne(
				new Document("_id", book.get(0))
					.append("title", book.get(1))
					.append("author", book.get(2))
					.append("genre", new DBRef("genre", book.get(3)))
					.append("comments", Arrays.stream(book.get(4).split(",")).map(c -> new Document("text", c)).collect(Collectors.toList()))
				)
			);
	}
}