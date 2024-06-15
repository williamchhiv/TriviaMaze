package test;
import model.Question;
import model.QuestionFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class QuestionFactoryTest {
    @Test
    public void testCreateQuestion() {
        // Test Data
        final String questionText = "What is Scooby Doo's favorite snack?";
        final String[] choices = {"Scooby Snacks", "M&M", "Lays", "Dog Food"};
        final String answer = "Scooby Snacks";
        final String type = "multipleChoice";

        // Create the question
        final Question question = QuestionFactory.createQuestion(questionText, choices, answer, type);

        // Verify that the question is not null
        assertNotNull(question);

        // Verify question properties
        assertEquals(questionText, question.getQuestion());
        assertArrayEquals(choices, question.getChoices());
        assertEquals(answer, question.getAnswer());
        assertEquals(Question.QUESTIONTYPE.multipleChoice, question.getType());
    }
}
