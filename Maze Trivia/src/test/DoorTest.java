package test;

import model.Door;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DoorTest {

    @Test
    public void testDoorInitialState() {
        Door door = new Door();
        assertTrue(door.isUnlocked(), "Door should be unlocked initially");
    }

    @Test
    public void testLockDoor() {
        Door door = new Door();
        door.lock();
        assertFalse(door.isUnlocked(), "Door should be locked after calling lock()");
    }
}
