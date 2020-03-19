package ru.otus.krivonos.exam.domain.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class QuestionTest {
    @Test
    void checkCreationQuestionWhenOk() throws Exception {
        Question question = Question.createInstanceFrom("question", QuestionNumber.createInstanceFrom(1));

        assertEquals("question", question.question());
        assertEquals(QuestionNumber.createInstanceFrom(1), question.questionNumber());
    }

    @Test
    void checkCreationQuestionWhenQuestionIsNullThenException() {
        QuestionCreationException exception = Assertions.assertThrows(QuestionCreationException.class, () -> {
            Question.createInstanceFrom(null, QuestionNumber.createInstanceFrom(1));
        });

        assertEquals("Отсутствует вопрос теста", exception.getMessage());
    }

     @Test
    void checkCreationQuestionWhenQuestionIsEmptyThenException() {
        QuestionCreationException exception = Assertions.assertThrows(QuestionCreationException.class, () -> {
            Question.createInstanceFrom("", QuestionNumber.createInstanceFrom(1));
        });

        assertEquals("Отсутствует вопрос теста", exception.getMessage());
    }
}