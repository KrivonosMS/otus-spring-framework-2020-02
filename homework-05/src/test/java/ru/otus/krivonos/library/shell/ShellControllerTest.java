package ru.otus.krivonos.library.shell;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.shell.Input;
import org.springframework.shell.Shell;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.krivonos.library.service.OutputService;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
class ShellControllerTest {
	@Autowired
	private Shell shell;

	@MockBean
	private OutputService outputService;

	@DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
	@Test
	void shouldPrintAllBooks() throws Exception {
		String res = (String) shell.evaluate(() -> "all books");

		assertThat(res).isEqualTo("");
		verify(outputService, times(7)).printText(any());
		verify(outputService, times(1)).printText("Книга{id=1, название='Повести Белкина (сборник)', автор='Александр Пушкин', жанр='Литература 19 века'}");
		verify(outputService, times(1)).printText("Книга{id=2, название='Евгений Онегин', автор='Александр Пушкин', жанр='Классическая проза'}");
		verify(outputService, times(1)).printText("Книга{id=3, название='Сказка о царе Салтане', автор='Александр Пушкин', жанр='Древнерусская литература'}");
		verify(outputService, times(1)).printText("Книга{id=4, название='Вечера на хуторе близ Диканьки', автор='Николай Гоголь', жанр='Классическая проза'}");
		verify(outputService, times(1)).printText("Книга{id=5, название='Петербургские повести', автор='Николай Гоголь', жанр='Русская классика'}");
		verify(outputService, times(1)).printText("Книга{id=6, название='Федор Тютчев: Стихи', автор='Фёдор Тютчев', жанр='Классическая проза'}");
		verify(outputService, times(1)).printText("Книга{id=7, название='Федор Тютчев: Стихи детям', автор='Фёдор Тютчев', жанр='Классическая проза'}");
	}

	@DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
	@Test
	void shouldPrintAllGenres() throws Exception {
		String res = (String) shell.evaluate(new Input() {
			@Override
			public String rawText() {
				return "all genres";
			}

			@Override
			public List<String> words() {
				List<String> l = new ArrayList<>();
				l.add("all");
				l.add("genres");
				return l;
			}
		});

		assertThat(res).isEqualTo("");
		verify(outputService, times(4)).printText(any());
		verify(outputService, times(1)).printText("'Древнерусская литература'");
		verify(outputService, times(1)).printText("'Классическая проза'");
		verify(outputService, times(1)).printText("'Литература 19 века'");
		verify(outputService, times(1)).printText("'Русская классика'");
	}

	@Test
	void shouldPrintBookByID() throws Exception {
		String res = (String) shell.evaluate(new Input() {
			@Override
			public String rawText() {
				return "book 1";
			}

			@Override
			public List<String> words() {
				List<String> l = new ArrayList<>();
				l.add("book");
				l.add("1");
				return l;
			}
		});

		assertThat(res).isEqualTo("");
		verify(outputService, times(1)).printText("Книга{id=1, название='Повести Белкина (сборник)', автор='Александр Пушкин', жанр='Литература 19 века'}");
	}

	@Test
	void shouldReturnSuccessUpdateBookMessage() {
		String res = (String) shell.evaluate(new Input() {
			@Override
			public String rawText() {
				return "update book 1 новое_название_книги новый_автор Русская классика";
			}

			@Override
			public List<String> words() {
				List<String> l = new ArrayList<>();
				l.add("update");
				l.add("book");
				l.add("1");
				l.add("новое_название_книги");
				l.add("новый_автор");
				l.add("Русская классика");
				return l;
			}
		});

		assertThat(res).isEqualTo("книга успешно обновлена");
	}

	@Test
	void shouldReturnSuccessSaveBookMessage() {
		String res = (String) shell.evaluate(new Input() {
			@Override
			public String rawText() {
				return "save book название_книги автор_книги Русская классика";
			}

			@Override
			public List<String> words() {
				List<String> l = new ArrayList<>();
				l.add("save");
				l.add("book");
				l.add("название_книги");
				l.add("автор_книги");
				l.add("Русская классика");
				return l;
			}
		});

		assertThat(res).isEqualTo("книга успешно сохранена");
	}

	@Test
	void shouldReturnSuccessBookDeleteMessage() {
		String res = (String) shell.evaluate(new Input() {
			@Override
			public String rawText() {
				return "delete book 2";
			}

			@Override
			public List<String> words() {
				List<String> l = new ArrayList<>();
				l.add("delete");
				l.add("book");
				l.add("2");
				return l;
			}
		});

		assertThat(res).isEqualTo("книга успешно удалена");
	}

	@Test
	void saveReturnSuccessSaveGenreMessage() {
		String res = (String) shell.evaluate(new Input() {
			@Override
			public String rawText() {
				return "save genre новый_литературный_жанр";
			}

			@Override
			public List<String> words() {
				List<String> l = new ArrayList<>();
				l.add("save");
				l.add("genre");
				l.add("новый_литературный_жанр");
				return l;
			}
		});

		assertThat(res).isEqualTo("литературный жанр 'новый_литературный_жанр' успешно сохранен");
	}
}