package model;
import java.io.*;
public record GameState(Maze maze) implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Saves the game state to a file.
     *
     * @param theGameState The GameState object to be saved.
     * @param theFilePath  The file path where the game state will be saved.
     * @throws IOException If an I/O error occurs while saving the game state.
     */
    public static void saveGame(final GameState theGameState, final String theFilePath) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(theFilePath))) {
            out.writeObject(theGameState);
        }
    }

    /**
     * Loads the game state from a file.
     *
     * @param theFilePath The file path from which the game state will be loaded.
     * @return The GameState object loaded from the file.
     * @throws IOException            If an I/O error occurs while loading the game state.
     * @throws ClassNotFoundException If the class of the serialized object cannot be found.
     */
    public static GameState loadGame(final String theFilePath) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(theFilePath))) {
            return (GameState) in.readObject();
        }
    }
}
