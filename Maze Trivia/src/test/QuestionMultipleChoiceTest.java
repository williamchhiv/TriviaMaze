package test;

import model.Question;
import model.QuestionMultipleChoice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuestionMultipleChoiceTest {

    private QuestionMultipleChoice question;

    @BeforeEach
    void setUp() {
        question = new QuestionMultipleChoice("What is 2+2?", new String[]{"3", "4", "5"}, "4");
    }

    @Test
    void testGetType() {
        assertEquals(Question.QUESTIONTYPE.multipleChoice, question.getType());
    }

    @Test
    void testGetQuestion() {
        assertEquals("What is 2+2?", question.getQuestion());
    }

    @Test
    void testGetChoices() {
        assertArrayEquals(new String[]{"3", "4", "5"}, question.getChoices());
    }

    @Test
    void testGetAnswer() {
        assertEquals("4", question.getAnswer());
    }
}
