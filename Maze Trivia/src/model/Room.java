package model;

import java.io.Serial;
import java.io.Serializable;

/**
 * The Room class represents a room in a maze, which contains an array of Door objects.
 * The room is initialized with five myDoors.
 */
public class Room implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final Door[] myDoors;

    /**
     * Constructs a Room object and initializes it with five Door objects.
     */
    public Room() {
        myDoors = new Door[5];
        initializeDoors();
    }

    /**
     * Initializes the myDoors array with new Door objects.
     */
    private void initializeDoors() {
        for (int i = 0; i < myDoors.length; i++) {
            myDoors[i] = new Door();
        }
    }

    /**
     * Returns the array of Door objects contained in the room.
     *
     * @return an array of Door objects
     */
    public Door[] getDoors() {
        return myDoors;
    }
}
