package ru.otus.krivonos.exam.domain.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class QuestionTest {
    @Test
    void checkCreationQuestionWhenOk() throws Exception {
        String[] row = new String[]{"question", "answer"};

        Question question = Question.from(row, QuestionNumber.from(1));

        assertEquals("question", question.question());
        assertEquals(QuestionNumber.from(1), question.questionNumber());
    }

    @Test
    void checkCreationQuestionWhenQuestionIsNullThenException() {
        String[] row = new String[]{null, "answer"};

        QuestionCreationException exception = Assertions.assertThrows(QuestionCreationException.class, () -> {
            Question.from(row, QuestionNumber.from(1));
        });

        assertEquals("Отсутствует вопрос теста", exception.getMessage());
    }

     @Test
    void checkCreationQuestionWhenQuestionIsEmptyThenException() {
        String[] row = new String[]{"", "answer"};

        QuestionCreationException exception = Assertions.assertThrows(QuestionCreationException.class, () -> {
            Question.from(row, QuestionNumber.from(1));
        });

        assertEquals("Отсутствует вопрос теста", exception.getMessage());
    }
}