package model;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Maze implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final Room[][] myGrid;
    private final Map<String, Integer[]> myRoomDirections;
    private int myCurrentRoomRow;
    private int myCurrentRoomCol;
    private final Set<String> myVisitedDoors;
    private final Set<String> myCorrectlyAnsweredDoors;
    private Question myNewQuestion;

    /**
     * Constructs a Maze object with the specified myGrid and room directions.
     *
     * @param theGrid        The 2D array representing the maze myGrid.
     * @param theRoomDirections The map of room directions.
     */
    public Maze(final Room[][] theGrid, final Map<String, Integer[]> theRoomDirections) {
        this.myGrid = theGrid;
        this.myRoomDirections = theRoomDirections;
        this.myVisitedDoors = new HashSet<>();
        this.myCorrectlyAnsweredDoors = new HashSet<>();
        this.myCurrentRoomRow = 0;
        this.myCurrentRoomCol = 0;
        this.myNewQuestion = QuestionFactory.getRandomQuestion();
    }

    /**
     * Attempts to move to the specified room number.
     *
     * @param theRoomNumber The room number to move to.
     * @return true if the move is valid and the room was previously theVisited or answered correctly, false otherwise.
     */
    public boolean attemptMove(final int theRoomNumber) {
        final Integer[] move = getDirectionFromRoomNumber(theRoomNumber);
        final int newRow = myCurrentRoomRow + move[0];
        final int newCol = myCurrentRoomCol + move[1];

        if (isValidMove(newRow, newCol)) {
            final String forwardKey = getCurrentRoomNumber() + "-" + theRoomNumber;
            final String backwardKey = theRoomNumber + "-" + getCurrentRoomNumber();

            if (myCorrectlyAnsweredDoors.contains(forwardKey) || myCorrectlyAnsweredDoors.contains(backwardKey)) {
                return true;
            } else {
                myNewQuestion = QuestionFactory.getRandomQuestion();
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Moves to the specified room number if the answer is correct.
     *
     * @param theRoomNumber      The room number to move to.
     * @param theIsAnswerCorrect Indicates whether the answer is correct.
     */
    public void move(final int theRoomNumber, final boolean theIsAnswerCorrect) {
        if (!theIsAnswerCorrect) {
            return; // Answer was incorrect, do not move
        }

        final Integer[] move = getDirectionFromRoomNumber(theRoomNumber);
        final int newRow = myCurrentRoomRow + move[0];
        final int newCol = myCurrentRoomCol + move[1];

        if (isValidMove(newRow, newCol)) {
            final String forwardKey = getCurrentRoomNumber() + "-" + theRoomNumber;
            final String backwardKey = theRoomNumber + "-" + getCurrentRoomNumber();

            myCurrentRoomRow = newRow;
            myCurrentRoomCol = newCol;
            myVisitedDoors.add(forwardKey);
            myVisitedDoors.add(backwardKey);
            myCorrectlyAnsweredDoors.add(forwardKey);
            myNewQuestion = QuestionFactory.getRandomQuestion(); // Generate new question
        }
    }

    /**
     * Checks if the specified move is valid within the maze myGrid.
     *
     * @param theRow The row index of the move.
     * @param theCol The column index of the move.
     * @return true if the move is valid, false otherwise.
     */
    private boolean isValidMove(final int theRow, final int theCol) {
        return theRow >= 0 && theRow < 5 && theCol >= 0 && theCol < 5;
    }

    /**
     * Gets the door corresponding to the specified move.
     *
     * @param theMove The array representing the move direction.
     * @return The Door object corresponding to the move.
     */
    private Door getDoor(final Integer[] theMove) {
        final Room currentRoom = myGrid[myCurrentRoomRow][myCurrentRoomCol];
        if (theMove[0] == -1 && theMove[1] == 0) return currentRoom.getDoors()[0]; // UP
        if (theMove[0] == 1 && theMove[1] == 0) return currentRoom.getDoors()[1]; // DOWN
        if (theMove[0] == 0 && theMove[1] == -1) return currentRoom.getDoors()[2]; // LEFT
        if (theMove[0] == 0 && theMove[1] == 1) return currentRoom.getDoors()[3]; // RIGHT
        throw new IllegalArgumentException("Invalid move");
    }

    /**
     * Gets the direction from the current room number to the specified room number.
     *
     * @param theRoomNumber The room number to get the direction to.
     * @return The array representing the direction.
     */
    public Integer[] getDirectionFromRoomNumber(final int theRoomNumber) {
        final int currentRoomNumber = getCurrentRoomNumber();
        if (theRoomNumber == currentRoomNumber - 5) return new Integer[]{-1, 0}; // UP
        if (theRoomNumber == currentRoomNumber + 5) return new Integer[]{1, 0}; // DOWN
        if (theRoomNumber == currentRoomNumber - 1) return new Integer[]{0, -1}; // LEFT
        if (theRoomNumber == currentRoomNumber + 1) return new Integer[]{0, 1}; // RIGHT
        throw new IllegalArgumentException("Invalid room number");
    }

    /**
     * Gets the current room number.
     *
     * @return The current room number.
     */
    public int getCurrentRoomNumber() {
        return myCurrentRoomRow * 5 + myCurrentRoomCol + 1;
    }

    /**
     * Gets the current question.
     *
     * @return The current Question object.
     */
    public Question getCurrentQuestion() {
        return myNewQuestion;
    }

    /**
     * Checks if the current room is the exit room.
     *
     * @return true if the current room is the exit room, false otherwise.
     */
    public boolean isAtExit() {
        return myCurrentRoomRow == 4 && myCurrentRoomCol == 4;
    }

    /**
     * Checks if there is a path to the end of the maze.
     *
     * @return true if there is a path to the end, false otherwise.
     */
    public boolean isPathToEnd() {
        final boolean[][] visited = new boolean[5][5];
        return dfs(0, 0, visited); // Start from room 1 (coordinates 0, 0)
    }

    /**
     * Depth-First Search (DFS) to check for a path to the end of the maze.
     *
     * @param theRow     The current row index.
     * @param theCol     The current column index.
     * @param theVisited The 2D array tracking theVisited rooms.
     * @return true if a path to the end is found, false otherwise.
     */
    private boolean dfs(final int theRow, final int theCol, final boolean[][] theVisited) {
        if (theRow == 4 && theCol == 4) {
            return true;
        }
        if (theRow < 0 || theCol < 0 || theRow >= 5 || theCol >= 5 || theVisited[theRow][theCol]) {
            return false;
        }

        theVisited[theRow][theCol] = true;

        for (String direction : myRoomDirections.keySet()) {
            final Integer[] move = myRoomDirections.get(direction);
            final int newRow = theRow + move[0];
            final int newCol = theCol + move[1];
            if (isDirectionAccessible(theRow, theCol, direction) && dfs(newRow, newCol, theVisited)) return true;
        }

        theVisited[theRow][theCol] = false;
        return false;
    }

    /**
     * Checks if the specified direction is accessible from the current room.
     *
     * @param theRow       The current row index.
     * @param theCol       The current column index.
     * @param theDirection The direction to check.
     * @return true if the direction is accessible, false otherwise.
     */
    private boolean isDirectionAccessible(final int theRow, final int theCol, final String theDirection) {
        Room currentRoom = myGrid[theRow][theCol];
        final int doorIndex = switch (theDirection) {
            case "UP" -> 0;
            case "DOWN" -> 1;
            case "LEFT" -> 2;
            case "RIGHT" -> 3;
            default -> throw new IllegalArgumentException("Invalid direction");
        };
        final Door door = currentRoom.getDoors()[doorIndex];
        return door.isUnlocked();
    }

    /**
     * Checks if the player can move up from the current room.
     *
     * @return true if the player can move up, false otherwise.
     */
    public boolean canMoveUp() {
        return myCurrentRoomRow > 0 && (!isDirectionBlocked("UP") || isVisitedDirection("UP"));
    }

    /**
     * Checks if the player can move down from the current room.
     *
     * @return true if the player can move down, false otherwise.
     */
    public boolean canMoveDown() {
        return myCurrentRoomRow < 4 && (!isDirectionBlocked("DOWN") || isVisitedDirection("DOWN"));
    }

    /**
     * Checks if the player can move left from the current room.
     *
     * @return true if the player can move left, false otherwise.
     */
    public boolean canMoveLeft() {
        return myCurrentRoomCol > 0 && (!isDirectionBlocked("LEFT") || isVisitedDirection("LEFT"));
    }

    /**
     * Checks if the player can move right from the current room.
     *
     * @return true if the player can move right, false otherwise.
     */
    public boolean canMoveRight() {
        return myCurrentRoomCol < 4 && (!isDirectionBlocked("RIGHT") || isVisitedDirection("RIGHT"));
    }

    /**
     * Checks if the specified direction has been visited from the current room.
     *
     * @param theDirection The direction to check.
     * @return true if the direction has been visited, false otherwise.
     */
    public boolean isVisitedDirection(final String theDirection) {
        final Integer[] move = myRoomDirections.get(theDirection);
        final int newRow = myCurrentRoomRow + move[0];
        final int newCol = myCurrentRoomCol + move[1];
        final int newRoomNumber = (newRow * 5) + newCol + 1;
        final String forwardKey = getCurrentRoomNumber() + "-" + newRoomNumber;
        final String backwardKey = newRoomNumber + "-" + getCurrentRoomNumber();
        return myVisitedDoors.contains(forwardKey) || myVisitedDoors.contains(backwardKey);
    }

    /**
     * Checks if the specified direction is blocked from the current room.
     *
     * @param theDirection The direction to check.
     * @return true if the direction is blocked, false otherwise.
     */
    public boolean isDirectionBlocked(final String theDirection) {
        final Integer[] move = myRoomDirections.get(theDirection);
        final int newRow = myCurrentRoomRow + move[0];
        final int newCol = myCurrentRoomCol + move[1];
        final int newRoomNumber = (newRow * 5) + newCol + 1;
        final String forwardKey = getCurrentRoomNumber() + "-" + newRoomNumber;
        final String backwardKey = newRoomNumber + "-" + getCurrentRoomNumber();
        return (myVisitedDoors.contains(forwardKey) && !myCorrectlyAnsweredDoors.contains(forwardKey)) || (myVisitedDoors.contains(backwardKey) && !myCorrectlyAnsweredDoors.contains(backwardKey));
    }

    /**
     * Locks the door in the specified direction from the current room.
     *
     * @param theDirection The direction to lock the door.
     */
    public void lockCurrentDoor(final String theDirection) {
        final Integer[] move = myRoomDirections.get(theDirection);
        final Door door = getDoor(move);
        door.lock(); // Lock the door
        final String forwardKey = getCurrentRoomNumber() + "-" + getRoomNumberFromMove(move);
        myVisitedDoors.add(forwardKey);
    }

    /**
     * Gets the room number from the specified move direction.
     *
     * @param theMove The array representing the move direction.
     * @return The room number corresponding to the move direction.
     */
    private int getRoomNumberFromMove(final Integer[] theMove) {
        final int newRow = myCurrentRoomRow + theMove[0];
        final int newCol = myCurrentRoomCol + theMove[1];
        return (newRow * 5) + newCol + 1;
    }

    /**
     * Gets the target room number for the specified direction.
     *
     * @param theDirection The direction to get the target room number.
     * @return The target room number.
     */
    public int getTargetRoomNumber(final String theDirection) {
        final int currentRoomNumber = getCurrentRoomNumber();
        return switch (theDirection) {
            case "UP" -> currentRoomNumber - 5;
            case "DOWN" -> currentRoomNumber + 5;
            case "LEFT" -> currentRoomNumber - 1;
            case "RIGHT" -> currentRoomNumber + 1;
            default -> throw new IllegalArgumentException("Invalid direction");
        };
    }
}
