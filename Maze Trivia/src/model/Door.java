package model;

import java.io.Serial;
import java.io.Serializable;

public class Door implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private boolean myLocked;

    /**
     * Constructs a Door object that is initially unlocked.
     */
    public Door() {
        this.myLocked = false;
    }

    /**
     * Checks if the door is unlocked.
     *
     * @return true if the door is unlocked, false otherwise.
     */
    public boolean isUnlocked() {
        return !myLocked;
    }

    /**
     * Locks the door.
     */
    public void lock() {
        this.myLocked = true;
    }
}
