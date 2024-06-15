package test;

import model.DatabaseManager;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseManagerTest {

    private Connection conn;

    @BeforeEach
    void setUp() {
        conn = DatabaseManager.connect();
        DatabaseManager.createTable();
    }

    @AfterEach
    void tearDown() {
        DatabaseManager.dropTable();
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testConnect() {
        assertNotNull(conn, "Connection should not be null");
    }

    @Test
    void testCreateTable() {
        final String tableCheckSql = "SELECT name FROM sqlite_master WHERE type='table' AND name='questions';";

        try (Statement stmt = conn.createStatement();
             var rs = stmt.executeQuery(tableCheckSql)) {

            assertTrue(rs.next(), "Table 'questions' should exist");
        } catch (SQLException e) {
            fail("An error occurred while checking the table existence: " + e.getMessage());
        }
    }

    @Test
    void testDropTable() {
        DatabaseManager.dropTable();
        final String tableCheckSql = "SELECT name FROM sqlite_master WHERE type='table' AND name='questions';";

        try (Statement stmt = conn.createStatement();
             var rs = stmt.executeQuery(tableCheckSql)) {

            assertFalse(rs.next(), "Table 'questions' should not exist");
        } catch (SQLException e) {
            fail("An error occurred while checking the table existence: " + e.getMessage());
        }
    }
}
