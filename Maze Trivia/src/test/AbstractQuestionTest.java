package test;

import model.AbstractQuestion;
import model.Question;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AbstractQuestionTest {

    private AbstractQuestion question;

    @BeforeEach
    void setUp() {
        question = new AbstractQuestion(Question.QUESTIONTYPE.multipleChoice, "What is 2+2?", new String[]{"3", "4", "5"}, "4") {
        };
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
