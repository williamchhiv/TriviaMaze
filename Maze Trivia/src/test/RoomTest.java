package test;

import model.Door;
import model.Room;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RoomTest {

    private Room room;

    @BeforeEach
    public void setUp() {
        room = new Room();
    }

    @Test
    public void testRoomInitialization() {
        assertNotNull(room.getDoors(), "Room should initialize doors");
        assertEquals(5, room.getDoors().length, "Room should have 5 doors");
    }

    @Test
    public void testDoorsAreUnlockedInitially() {
        for (Door door : room.getDoors()) {
            assertTrue(door.isUnlocked(), "All doors should be unlocked initially");
        }
    }

    @Test
    public void testLockDoors() {
        for (Door door : room.getDoors()) {
            door.lock();
            assertFalse(door.isUnlocked(), "Doors should be locked after calling lock method");
        }
    }

    @Test
    public void testUnlockingDoors() {
        for (Door door : room.getDoors()) {
            door.lock();
            assertFalse(door.isUnlocked(), "Doors should be locked after calling lock method");
            door.lock(); // Since we don't have an unlock method, this just keeps it locked
            assertFalse(door.isUnlocked(), "Doors should remain locked");
        }
    }
}
