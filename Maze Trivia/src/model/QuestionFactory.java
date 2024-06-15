package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The QuestionFactory class is responsible for creating Question objects
 * and retrieving random questions from the database.
 */
public class QuestionFactory {

    /**
     * Creates a new Question object based on the specified type.
     *
     * @param theQuestion The question text.
     * @param theChoices  The possible choices for the question.
     * @param theAnswer   The correct answer for the question.
     * @param type        The type of the question (e.g., "multipleChoice", "trueFalse", "shortAnswer").
     * @return A new Question object of the specified type.
     */
    public static Question createQuestion(final String theQuestion, final String[] theChoices, final String theAnswer, final String type) {
        return switch (type) {
            case "multipleChoice" -> new QuestionMultipleChoice(theQuestion, theChoices, theAnswer) {};
            case "trueFalse" -> new QuestionTrueFalse(theQuestion, theChoices, theAnswer) {};
            case "shortAnswer" -> new QuestionShortAnswer(theQuestion, theChoices, theAnswer) {};
            default -> throw new IllegalStateException("Unexpected value: " + type);
        };
    }

    /**
     * Retrieves a random question from the database.
     *
     * @return A random Question object, or null if no question is found.
     */
    public static Question getRandomQuestion() {
        final String sql = "SELECT * FROM questions ORDER BY RANDOM() LIMIT 1";

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            final ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                final String type = rs.getString("type");
                final String questionText = rs.getString("question");
                final String choicesStr = rs.getString("choices");
                final String answer = rs.getString("answer");

                final String[] choices = choicesStr.isEmpty() ? new String[0] : choicesStr.split(",");

                return createQuestion(questionText, choices, answer, type);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
