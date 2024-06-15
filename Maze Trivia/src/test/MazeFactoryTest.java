package test;

import model.Maze;
import model.MazeFactory;
import model.Room;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class MazeFactoryTest {

    @Test
    public void testCreateMaze() {
        Maze maze = MazeFactory.createMaze();

        // Check that the maze is not null
        assertNotNull(maze, "Maze should not be null");

        // Check that the grid is 5x5
        Room[][] grid = getMazeGrid(maze);
        assertEquals(5, grid.length, "Grid should have 5 rows");
        for (Room[] row : grid) {
            assertEquals(5, row.length, "Each row in the grid should have 5 columns");
        }

        // Check that each room in the grid is initialized
        for (Room[] row : grid) {
            for (Room room : row) {
                assertNotNull(room, "Each room in the grid should be initialized");
            }
        }

        // Check the room directions
        Map<String, Integer[]> roomDirections = getRoomDirections(maze);
        assertNotNull(roomDirections, "Room directions should not be null");
        assertEquals(4, roomDirections.size(), "There should be 4 room directions");

        // Verify each direction
        Integer[] upDirection = roomDirections.get("UP");
        assertNotNull(upDirection, "UP direction should not be null");
        assertArrayEquals(new Integer[]{-1, 0}, upDirection, "UP direction should be [-1, 0]");

        Integer[] downDirection = roomDirections.get("DOWN");
        assertNotNull(downDirection, "DOWN direction should not be null");
        assertArrayEquals(new Integer[]{1, 0}, downDirection, "DOWN direction should be [1, 0]");

        Integer[] leftDirection = roomDirections.get("LEFT");
        assertNotNull(leftDirection, "LEFT direction should not be null");
        assertArrayEquals(new Integer[]{0, -1}, leftDirection, "LEFT direction should be [0, -1]");

        Integer[] rightDirection = roomDirections.get("RIGHT");
        assertNotNull(rightDirection, "RIGHT direction should not be null");
        assertArrayEquals(new Integer[]{0, 1}, rightDirection, "RIGHT direction should be [0, 1]");
    }

    private Room[][] getMazeGrid(Maze maze) {
        try {
            java.lang.reflect.Field field = Maze.class.getDeclaredField("myGrid");
            field.setAccessible(true);
            return (Room[][]) field.get(maze);
        } catch (Exception e) {
            throw new RuntimeException("Failed to access the grid field", e);
        }
    }

    private Map<String, Integer[]> getRoomDirections(Maze maze) {
        try {
            java.lang.reflect.Field field = Maze.class.getDeclaredField("myRoomDirections");
            field.setAccessible(true);
            return (Map<String, Integer[]>) field.get(maze);
        } catch (Exception e) {
            throw new RuntimeException("Failed to access the room directions field", e);
        }
    }
}
