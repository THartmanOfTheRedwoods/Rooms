import java.util.ArrayList;

public class WalkThrough {
    public static void main(String[] args) {
        ArrayList<Room> rooms = new ArrayList<>();
        rooms.add(new Room("Outside"));
        rooms.add(new Room("One"));
        rooms.add(new Room("Two"));
        rooms.add(new Room("Three"));
        rooms.add(new Room("Four"));
        rooms.add(new Room("Five"));

        Door[] doors = new Door[16];
        for(int i=0; i<doors.length; i++) {
            doors[i] = new Door(i+1, false);
        }

        rooms.get(0).connect(rooms.get(1), doors[0]);
        rooms.get(0).connect(rooms.get(2), doors[1]);
        rooms.get(0).connect(rooms.get(1), doors[2]);
        rooms.get(1).connect(rooms.get(2), doors[3]);
        rooms.get(0).connect(rooms.get(2), doors[4]);
        rooms.get(1).connect(rooms.get(3), doors[5]);
        rooms.get(1).connect(rooms.get(4), doors[6]);
        rooms.get(2).connect(rooms.get(4), doors[7]);
        rooms.get(2).connect(rooms.get(5), doors[8]);
        rooms.get(0).connect(rooms.get(3), doors[9]);
        rooms.get(3).connect(rooms.get(4), doors[10]);
        rooms.get(4).connect(rooms.get(5), doors[11]);
        rooms.get(0).connect(rooms.get(5), doors[12]);
        rooms.get(0).connect(rooms.get(3), doors[13]);
        rooms.get(0).connect(rooms.get(4), doors[14]);
        rooms.get(0).connect(rooms.get(5), doors[15]);

        for(Room r : rooms) {
            System.out.printf("Starting walk in room %s with %d unlocked doors%n", r, r.getUnlockedDoors().size());
            walk(r, new ArrayList<>(), doors.length);
        }
    }

    public static void walk(Room current, ArrayList<Door> path, int totalDoors) {
        if(current.getUnlockedDoors().size() == 0) {
            //System.out.println(path);
            if(path.size() >= totalDoors) {
                System.out.println(path);
            }
            return;
        }
        for(Door d : current.getUnlockedDoors()) {
            Room r = d.goThrough(current);
            d.setLocked(true); // Lock it before recursive call.
            path.add(d);
            walk(r, path, totalDoors);
            path.remove(d);
            d.setLocked(false); // Unlock it on the way back.
        }
    }
}
