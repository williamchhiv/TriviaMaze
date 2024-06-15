package test;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class MazeTest {

    private Maze maze;

    @BeforeEach
    public void setUp() {
        // Ensure the table exists
        DatabaseManager.createTable();

        // Setup maze
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

        maze = new Maze(grid, roomDirections);
    }

    @Test
    public void testInitialMazeState() {
        assertNull(maze.getCurrentQuestion(), "Initial question should be null");
        assertEquals(1, maze.getCurrentRoomNumber(), "Initial room should be 1");
    }

    @Test
    public void testValidMove() {
        int targetRoomNumber = 2; // Assuming room 2 is to the right of room 1
        assertFalse(maze.attemptMove(targetRoomNumber), "Should need to answer a question to move to room 2");

        // Simulate answering the question correctly
        maze.move(targetRoomNumber, true);

        assertEquals(2, maze.getCurrentRoomNumber(), "Should be able to move to room 2 after answering correctly");
    }

    @Test
    public void testInvalidMove() {
        int targetRoomNumber = 26; // Invalid room number outside the maze
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            maze.getDirectionFromRoomNumber(targetRoomNumber);
        });
        assertEquals("Invalid room number", exception.getMessage());
    }

    @Test
    public void testMoveIncorrectAnswer() {
        int targetRoomNumber = 2; // Assuming room 2 is to the right of room 1
        maze.attemptMove(targetRoomNumber);
        maze.move(targetRoomNumber, false); // Incorrect answer
        assertEquals(1, maze.getCurrentRoomNumber(), "Room number should still be 1 after incorrect answer");
    }

    @Test
    public void testIsPathToEnd() {
        assertTrue(maze.isPathToEnd(), "Initially, there should be a path to the end");
    }

    @Test
    public void testLockCurrentDoor() {
        int targetRoomNumber = 2; // Assuming room 2 is to the right of room 1
        maze.lockCurrentDoor("RIGHT");
        assertTrue(maze.isDirectionBlocked("RIGHT"), "Right direction should be blocked after locking the door");
    }

    @Test
    public void testNoPathToEnd() {
        maze.lockCurrentDoor("DOWN");
        maze.lockCurrentDoor("RIGHT");
        maze.lockCurrentDoor("UP");
        maze.lockCurrentDoor("LEFT");
        assertFalse(maze.isPathToEnd(), "There should be no path to the end after blocking all directions");
    }

    @Test
    public void testGetTargetRoomNumber() {
        assertEquals(6, maze.getTargetRoomNumber("DOWN"), "Target room number should be 6 when moving down from room 1");
        assertEquals(2, maze.getTargetRoomNumber("RIGHT"), "Target room number should be 2 when moving right from room 1");
    }
}
