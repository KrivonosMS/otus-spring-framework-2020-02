package ru.otus.krivonos.exam.domain.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CorrectTestAnswerTest {
    @Test
    void checkCreationCorrectTestAnswerWhenOk() throws Exception {
        CorrectTestAnswer answer = CorrectTestAnswer.createInstanceFrom("answer", QuestionNumber.createInstanceFrom(1));

        assertEquals("answer", answer.answer());
    }

    @Test
    void checkCreationCorrectTestAnswerWhenQuestionIsNullThenException() {
        AnswerCreationException exception = Assertions.assertThrows(AnswerCreationException.class, () -> {
            CorrectTestAnswer.createInstanceFrom(null, QuestionNumber.createInstanceFrom(1));
        });

        assertEquals("Отсутствует ответ для вопроса теста", exception.getMessage());
    }

    @Test
    void checkCreationCorrectTestAnswerWhenQuestionIsEmptyThenException() {
        AnswerCreationException exception = Assertions.assertThrows(AnswerCreationException.class, () -> {
            CorrectTestAnswer.createInstanceFrom("", QuestionNumber.createInstanceFrom(1));
        });

        assertEquals("Отсутствует ответ для вопроса теста", exception.getMessage());
    }
}