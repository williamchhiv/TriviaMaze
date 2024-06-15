package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {

    private static final String DB_URL = "jdbc:sqlite:questions.db";

    /**
     * Establishes a connection to the database.
     *
     * @return a Connection object to the database, or null if a connection could not be established.
     */
    public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DB_URL);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    /**
     * Creates the questions table if it does not already exist.
     * The table contains columns for id, type, question, choices, and answer.
     */
    public static void createTable() {
        final String sql = """
            CREATE TABLE IF NOT EXISTS questions (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                type TEXT NOT NULL,
                question TEXT NOT NULL,
                choices TEXT,
                answer TEXT NOT NULL
            );
            """;

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table created successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Drops the questions table if it exists.
     */
    public static void dropTable() {
        final String sql = "DROP TABLE IF EXISTS questions";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table dropped successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
