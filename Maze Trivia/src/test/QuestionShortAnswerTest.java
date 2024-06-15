package test;

import model.Question;
import model.QuestionShortAnswer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuestionShortAnswerTest {

    private QuestionShortAnswer question;

    @BeforeEach
    void setUp() {
        question = new QuestionShortAnswer("What is the capital of France?", new String[]{}, "Paris");
    }

    @Test
    void testGetType() {
        assertEquals(Question.QUESTIONTYPE.shortAnswer, question.getType());
    }

    @Test
    void testGetQuestion() {
        assertEquals("What is the capital of France?", question.getQuestion());
    }

    @Test
    void testGetChoices() {
        assertArrayEquals(new String[]{}, question.getChoices());
    }

    @Test
    void testGetAnswer() {
        assertEquals("Paris", question.getAnswer());
    }
}
