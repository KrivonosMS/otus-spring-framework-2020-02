package ru.otus.krivonos.exam.domain.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CorrectTestAnswerTest {
    @Test
    void checkCreationCorrectTestAnswerWhenOk() throws Exception {
        String[] row = new String[]{"question", "answer"};

        CorrectTestAnswer answer = CorrectTestAnswer.from(row, QuestionNumber.from(1));

        assertEquals("answer", answer.answer());
        assertEquals(QuestionNumber.from(1), answer.questionNumber());
    }

    @Test
    void checkCreationCorrectTestAnswerWhenQuestionIsNullThenException() {
        String[] row = new String[]{"questiom", null};

        AnswerCreationException exception = Assertions.assertThrows(AnswerCreationException.class, () -> {
            CorrectTestAnswer.from(row, QuestionNumber.from(1));
        });

        assertEquals("Отсутствует ответ для вопроса теста", exception.getMessage());
    }

    @Test
    void checkCreationCorrectTestAnswerWhenQuestionIsEmptyThenException() {
        String[] row = new String[]{"questiom", ""};

        AnswerCreationException exception = Assertions.assertThrows(AnswerCreationException.class, () -> {
            CorrectTestAnswer.from(row, QuestionNumber.from(1));
        });

        assertEquals("Отсутствует ответ для вопроса теста", exception.getMessage());
    }
}