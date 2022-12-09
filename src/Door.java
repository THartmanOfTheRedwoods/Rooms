public class Door {

    private boolean isLocked;
    private final int id;
    // A Door always connects 2 rooms.
    private Room r1;
    private Room r2;

    public Door(int id, boolean isLocked) {
        this.id = id;
        this.isLocked = isLocked;
    }

    public Door(int id, Room r1, Room r2, boolean isLocked) {
        this.id = id;
        this.r1 = r1;
        this.r2 = r2;
        this.isLocked = isLocked;
    }

    public void setRooms(Room r1, Room r2) {
        this.r1 = r1;
        this.r2 = r2;
    }

    /* Walk through the door of the current room into the connected room */
    public Room goThrough(Room current) {
        return (current.equals(r1)) ? r2 : r1;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    @Override
    public String toString() {
        return "Door{" + "id=" + id + '}';
    }
}
