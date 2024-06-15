package model;

import java.util.HashMap;
import java.util.Map;

public class MazeFactory {

    /**
     * Creates a new Maze instance with a 5x5 grid of rooms and predefined room directions.
     *
     * @return A new Maze object.
     */
    public static Maze createMaze() {
        final Room[][] grid = new Room[5][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                grid[i][j] = new Room();
            }
        }

        final Map<String, Integer[]> roomDirections = new HashMap<>();
        roomDirections.put("UP", new Integer[]{-1, 0});
        roomDirections.put("DOWN", new Integer[]{1, 0});
        roomDirections.put("LEFT", new Integer[]{0, -1});
        roomDirections.put("RIGHT", new Integer[]{0, 1});

        return new Maze(grid, roomDirections);
    }
}
