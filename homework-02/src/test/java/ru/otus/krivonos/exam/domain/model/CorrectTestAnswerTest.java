package ru.otus.krivonos.exam.domain.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CorrectTestAnswerTest {
    @Test
    void checkCreationCorrectTestAnswerWhenOk() throws Exception {
        String[] row = new String[]{"question", "answer"};

        CorrectTestAnswer answer = CorrectTestAnswer.createInstanceFrom(row, QuestionNumber.createInstanceFrom(1));

        assertEquals("answer", answer.answer());
    }

    @Test
    void checkCreationCorrectTestAnswerWhenQuestionIsNullThenException() {
        String[] row = new String[]{"questiom", null};

        AnswerCreationException exception = Assertions.assertThrows(AnswerCreationException.class, () -> {
            CorrectTestAnswer.createInstanceFrom(row, QuestionNumber.createInstanceFrom(1));
        });

        assertEquals("Отсутствует ответ для вопроса теста", exception.getMessage());
    }

    @Test
    void checkCreationCorrectTestAnswerWhenQuestionIsEmptyThenException() {
        String[] row = new String[]{"questiom", ""};

        AnswerCreationException exception = Assertions.assertThrows(AnswerCreationException.class, () -> {
            CorrectTestAnswer.createInstanceFrom(row, QuestionNumber.createInstanceFrom(1));
        });

        assertEquals("Отсутствует ответ для вопроса теста", exception.getMessage());
    }
}