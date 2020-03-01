package ru.otus.krivonos.exam.domain.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class PersonAnswersTest {
    @Test
    void createPersonAnswerWhenOk() throws Exception {
        Map<QuestionNumber, String> answers = new HashMap<>();
        answers.put(QuestionNumber.from(1), "answer1");
        answers.put(QuestionNumber.from(2), "answer2");

        PersonAnswers personAnswers = PersonAnswers.from(answers, "test_user");

        assertEquals(2, personAnswers.personAnswers().size());
        assertEquals("answer1", personAnswers.personAnswers().get(QuestionNumber.from(1)));
        assertEquals("answer1", personAnswers.personAnswers().get(QuestionNumber.from(1)));
        assertEquals("answer2", personAnswers.personAnswers().get(QuestionNumber.from(2)));
        assertEquals("test_user", personAnswers.username());
    }

    @Test
    void createPersonAnswerWhenAnswerIsEmpty() throws Exception {
        Map<QuestionNumber, String> answers = new HashMap<>();
        answers.put(QuestionNumber.from(1), "answer1");
        answers.put(QuestionNumber.from(2), "");

        PersonAnswers personAnswers = PersonAnswers.from(answers, "test_user");

        assertEquals(2, personAnswers.personAnswers().size());
        assertEquals("answer1", personAnswers.personAnswers().get(QuestionNumber.from(1)));
        assertEquals("", personAnswers.personAnswers().get(QuestionNumber.from(2)));
    }

    @Test
    void createPersonAnswerWhenAnswerIsNull() throws Exception {
        Map<QuestionNumber, String> answers = new HashMap<>();
        answers.put(QuestionNumber.from(1), "answer1");
        answers.put(QuestionNumber.from(2), null);

        PersonAnswers personAnswers = PersonAnswers.from(answers, "test_user");

        assertEquals(2, personAnswers.personAnswers().size());
        assertEquals("answer1", personAnswers.personAnswers().get(QuestionNumber.from(1)));
        assertNull(personAnswers.personAnswers().get(QuestionNumber.from(2)));
    }

    @Test
    void createPersonAnswerWhenAnswerMapIsNullThenException() {
        PersonAnswersCreationException exception = Assertions.assertThrows(PersonAnswersCreationException.class, () -> {
            PersonAnswers.from(null, "test_user");
        });

        assertEquals("Отсутствуют ответы тестируемого пользователя", exception.getMessage());
    }

    @Test
    void createPersonAnswerWhenUserIsNullThenException() {
        PersonAnswersCreationException exception = Assertions.assertThrows(PersonAnswersCreationException.class, () -> {
            PersonAnswers.from(new HashMap<>(), null);
        });

        assertEquals("Отсутствует имя пользователя", exception.getMessage());
    }

    @Test
    void createPersonAnswerWhenUserIsEmptyThenException() {
        PersonAnswersCreationException exception = Assertions.assertThrows(PersonAnswersCreationException.class, () -> {
            PersonAnswers.from(new HashMap<>(), null);
        });

        assertEquals("Отсутствует имя пользователя", exception.getMessage());
    }
}