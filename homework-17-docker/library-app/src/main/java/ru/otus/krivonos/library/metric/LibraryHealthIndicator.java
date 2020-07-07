package ru.otus.krivonos.library.metric;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;
import ru.otus.krivonos.library.dao.BookRepository;

@RequiredArgsConstructor
@Component
public class LibraryHealthIndicator implements HealthIndicator {

	private final BookRepository bookRepository;

	@Override
	public Health health() {
		int count = bookRepository.findAll().size();
		if(count > 0) {
			return Health.up().status(Status.UP).build();
		} else {
			return Health.down().status(Status.DOWN).build();
		}
	}
}