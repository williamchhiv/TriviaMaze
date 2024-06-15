package test;

import model.Question;
import model.QuestionTrueFalse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuestionTrueFalseTest {

    private QuestionTrueFalse question;

    @BeforeEach
    void setUp() {
        question = new QuestionTrueFalse("The earth is flat.", new String[]{"True", "False"}, "False");
    }

    @Test
    void testGetType() {
        assertEquals(Question.QUESTIONTYPE.trueFalse, question.getType());
    }

    @Test
    void testGetQuestion() {
        assertEquals("The earth is flat.", question.getQuestion());
    }

    @Test
    void testGetChoices() {
        assertArrayEquals(new String[]{"True", "False"}, question.getChoices());
    }

    @Test
    void testGetAnswer() {
        assertEquals("False", question.getAnswer());
    }
}
