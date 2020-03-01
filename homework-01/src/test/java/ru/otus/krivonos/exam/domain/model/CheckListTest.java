package ru.otus.krivonos.exam.domain.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CheckListTest {
    @Test
    void creationCheckListFromRowsWhenOk() throws Exception {
        List<String[]> rows = new ArrayList<>();
        rows.add(new String[]{"question1", "answer1"});
        rows.add(new String[]{"question2", "answer2"});

        CheckList checkList = CheckList.from(rows);

        Map<QuestionNumber, Question> questions = checkList.questions();
        assertEquals(2, questions.size());
        assertEquals("question1", questions.get(QuestionNumber.from(1)).question());
        assertEquals(1, questions.get(QuestionNumber.from(1)).questionNumber().number());
        assertEquals("question2", questions.get(QuestionNumber.from(2)).question());
        assertEquals(2, questions.get(QuestionNumber.from(2)).questionNumber().number());
        Map<QuestionNumber, CorrectTestAnswer> answers = checkList.answers();
        assertEquals("answer1", answers.get(QuestionNumber.from(1)).answer());
        assertEquals(1, answers.get(QuestionNumber.from(1)).questionNumber().number());
        assertEquals("answer2", answers.get(QuestionNumber.from(2)).answer());
        assertEquals(2, answers.get(QuestionNumber.from(2)).questionNumber().number());
    }

    @Test
    void creationCheckListFromRowsWhenAnswerIsNullThenException() {
        List<String[]> rows = new ArrayList<>();
        rows.add(new String[]{"question1", "answer1"});
        rows.add(new String[]{"question2", null});

        CheckListCreationException exception = Assertions.assertThrows(CheckListCreationException.class, () -> {
            CheckList.from(rows);
        });

        assertEquals("Ошибка при создании ответа из строки № 2", exception.getMessage());
        assertEquals("Отсутствует ответ для вопроса теста", exception.getCause().getMessage());
    }

    @Test
    void creationCheckListFromRowsWhenAnswerIsEmptyThenException() {
        List<String[]> rows = new ArrayList<>();
        rows.add(new String[]{"question1", "answer1"});
        rows.add(new String[]{"question2", ""});

        CheckListCreationException exception = Assertions.assertThrows(CheckListCreationException.class, () -> {
            CheckList.from(rows);
        });

        assertEquals("Ошибка при создании ответа из строки № 2", exception.getMessage());
        assertEquals("Отсутствует ответ для вопроса теста", exception.getCause().getMessage());
    }

    @Test
    void creationCheckListFromRowsWhenQuestionIsNullThenException() {
        List<String[]> rows = new ArrayList<>();
        rows.add(new String[]{"question1", "answer1"});
        rows.add(new String[]{null, "answer2"});

        CheckListCreationException exception = Assertions.assertThrows(CheckListCreationException.class, () -> {
            CheckList.from(rows);
        });

        assertEquals("Ошибка при создании вопроса из строки № 2", exception.getMessage());
        assertEquals("Отсутствует вопрос теста", exception.getCause().getMessage());
    }

    @Test
    void creationCheckListFromRowsWhenQuestionIsEmptyThenException() {
        List<String[]> rows = new ArrayList<>();
        rows.add(new String[]{"question1", "answer1"});
        rows.add(new String[]{"", "answer2"});

        CheckListCreationException exception = Assertions.assertThrows(CheckListCreationException.class, () -> {
            CheckList.from(rows);
        });

        assertEquals("Ошибка при создании вопроса из строки № 2", exception.getMessage());
        assertEquals("Отсутствует вопрос теста", exception.getCause().getMessage());
    }

    @Test
    void calculateResultWhenNotCorrectAnswer() throws Exception {
        List<String[]> rows = new ArrayList<>();
        rows.add(new String[]{"question1", "answer1"});
        rows.add(new String[]{"question2", "answer2"});
        rows.add(new String[]{"question3", "answer3"});
        CheckList checkListTest = CheckList.from(rows);
        Map<QuestionNumber, String> answers = new HashMap<>();
        answers.put(QuestionNumber.from(1), "answer1");
        answers.put(QuestionNumber.from(3), "answer3");
        answers.put(QuestionNumber.from(2), "not_correct_answer");
        PersonAnswers personAnswers = PersonAnswers.from(answers, "test_user");

        double result = checkListTest.calculateResult(personAnswers);

        assertEquals((double) 2/3, result);
    }

    @Test
    void calculateResultWhenNAnswerIsNull() throws Exception {
        List<String[]> rows = new ArrayList<>();
        rows.add(new String[]{"question1", "answer1"});
        rows.add(new String[]{"question2", "answer2"});
        rows.add(new String[]{"question3", "answer3"});
        CheckList checkListTest = CheckList.from(rows);
        Map<QuestionNumber, String> answers = new HashMap<>();
        answers.put(QuestionNumber.from(1), "answer1");
        answers.put(QuestionNumber.from(3), "answer3");
        answers.put(QuestionNumber.from(2), null);
        PersonAnswers personAnswers = PersonAnswers.from(answers, "test_user");

        double result = checkListTest.calculateResult(personAnswers);

        assertEquals((double) 2/3, result);
    }

    @Test
    void calculateResultWhenNAnswerIsEmpty() throws Exception {
        List<String[]> rows = new ArrayList<>();
        rows.add(new String[]{"question1", "answer1"});
        rows.add(new String[]{"question2", "answer2"});
        rows.add(new String[]{"question3", "answer3"});
        CheckList checkListTest = CheckList.from(rows);
        Map<QuestionNumber, String> answers = new HashMap<>();
        answers.put(QuestionNumber.from(1), "answer1");
        answers.put(QuestionNumber.from(3), "answer3");
        answers.put(QuestionNumber.from(2), "");
        PersonAnswers personAnswers = PersonAnswers.from(answers, "test_user");

        double result = checkListTest.calculateResult(personAnswers);

        assertEquals((double) 2/3, result);
    }
}