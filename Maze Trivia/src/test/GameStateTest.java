package test;

import model.GameState;
import model.Maze;
import model.Room;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class GameStateTest {

    private static final String TEST_FILE_PATH = "test_game_state.ser";
    private GameState testGameState;

    @BeforeEach
    public void setUp() {
        // Create a test maze for serialization
        Room[][] grid = new Room[5][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                grid[i][j] = new Room();
            }
        }
        Map<String, Integer[]> roomDirections = new HashMap<>();
        roomDirections.put("UP", new Integer[]{-1, 0});
        roomDirections.put("DOWN", new Integer[]{1, 0});
        roomDirections.put("LEFT", new Integer[]{0, -1});
        roomDirections.put("RIGHT", new Integer[]{0, 1});
        Maze testMaze = new Maze(grid, roomDirections);
        testGameState = new GameState(testMaze);
    }

    @AfterEach
    public void tearDown() {
        // Clean up the test file if it exists
        File testFile = new File(TEST_FILE_PATH);
        if (testFile.exists()) {
            assertTrue(testFile.delete(), "Test file should be deleted");
        }
    }

    @Test
    public void testSaveGame() {
        assertDoesNotThrow(() -> GameState.saveGame(testGameState, TEST_FILE_PATH), "Saving game state should not throw an exception");

        File savedFile = new File(TEST_FILE_PATH);
        assertTrue(savedFile.exists(), "Saved file should exist");
        assertTrue(savedFile.isFile(), "Saved file should be a file");
        assertTrue(savedFile.length() > 0, "Saved file should not be empty");
    }

    @Test
    public void testLoadGame() {
        assertDoesNotThrow(() -> GameState.saveGame(testGameState, TEST_FILE_PATH), "Saving game state should not throw an exception");

        GameState loadedGameState = null;
        try {
            loadedGameState = GameState.loadGame(TEST_FILE_PATH);
        } catch (IOException | ClassNotFoundException e) {
            fail("Loading game state should not throw an exception: " + e.getMessage());
        }

        assertNotNull(loadedGameState, "Loaded game state should not be null");
    }

    @Test
    public void testLoadGameWithInvalidFile() {
        assertThrows(IOException.class, () -> GameState.loadGame("non_existent_file.ser"), "Loading non-existent file should throw an IOException");
    }
}
