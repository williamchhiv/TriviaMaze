package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * The QuestionImporter class is responsible for importing questions from a file
 * and inserting them into a database.
 */
public class QuestionImporter {

    private static final String INSERT_SQL = """
        INSERT INTO questions(type, question, choices, answer) VALUES(?, ?, ?, ?)
        """;

    /**
     * Imports questions from the specified file path and inserts them into the database.
     *
     * @param theFilePath The path to the file containing the questions.
     */
    public static void importQuestions(final String theFilePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(theFilePath));
             Connection conn = DatabaseManager.connect();
             PreparedStatement prep = conn.prepareStatement(INSERT_SQL)) {

            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                final String type = extractValue(line, "type");
                final String question = extractValue(br.readLine(), "question");
                final String choices = extractValue(br.readLine(), "choices");
                final String answer = extractValue(br.readLine(), "answer");

                prep.setString(1, type);
                prep.setString(2, question);
                prep.setString(3, choices);
                prep.setString(4, answer);

                prep.addBatch();

                // Skip the empty line between questions
                br.readLine();
            }
            prep.executeBatch();
            System.out.println("Questions imported successfully.");
        } catch (IOException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Extracts the value for the specified key from the provided line.
     *
     * @param theLine       The line containing the key-value pair.
     * @param theExpectedKey The expected key to be found in the line.
     * @return The value associated with the key.
     * @throws IOException If the line is invalid or does not contain the expected key.
     */
    private static String extractValue(final String theLine, final String theExpectedKey) throws IOException {
        if (theLine == null || !theLine.startsWith(theExpectedKey + "=")) {
            throw new IOException("Invalid or missing line for " + theExpectedKey + ": " + theLine);
        }
        final String[] parts = theLine.split("=", 2);
        if (parts.length < 2) {
            throw new IOException("Invalid format for " + theExpectedKey + ": " + theLine);
        }
        return parts[1];
    }
}
