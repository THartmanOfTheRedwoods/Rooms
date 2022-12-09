import java.util.ArrayList;
import java.util.List;

public class Room {
    private List<Door> doors;
    private final String name;

    public Room(String name) {
        doors = new ArrayList<>();
        this.name = name;
    }

    public void connect(Room r, Door door) {
        this.addDoor(door);
        r.addDoor(door);
        door.setRooms(this, r);
    }

    public void addDoor(Door door) {
        this.doors.add(door);
    }

    public List<Door> getDoors() {
        return this.doors;
    }
    public List<Door> getUnlockedDoors() {
        return this.doors.stream().filter(d -> !d.isLocked()).toList();
    }

    public Room goThrough(Door door) {
        // Verify this is a door we can go through.
        if(doors.contains(door)) {
            return door.goThrough(this);
        }
        return null;
    }

    @Override
    public String toString() {
        return String.format("%s", name);
    }
}
