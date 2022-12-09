import java.util.ArrayList;

public class WalkThrough {
    public static void main(String[] args) {
        Room outside = new Room("Outside");
        Room r1 = new Room("One");
        Room r2 = new Room("Two");
        Room r3 = new Room("Three");
        Room r4 = new Room("Four");
        Room r5 = new Room("Five");
        Room[] rooms = {outside, r1, r2, r3, r4, r5};

        Door[] doors = new Door[16];
        for(int i=0; i<doors.length; i++) {
            doors[i] = new Door(i+1, false);
        }

        outside.connect(r1, doors[0]);
        outside.connect(r2, doors[1]);
        outside.connect(r1, doors[2]);
        r1.connect(r2, doors[3]);
        outside.connect(r2, doors[4]);
        r1.connect(r3, doors[5]);
        r1.connect(r4, doors[6]);
        r2.connect(r4, doors[7]);
        r2.connect(r5, doors[8]);
        outside.connect(r3, doors[9]);
        r3.connect(r4, doors[10]);
        r4.connect(r5, doors[11]);
        outside.connect(r5, doors[12]);
        outside.connect(r3, doors[13]);
        outside.connect(r4, doors[14]);
        outside.connect(r5, doors[15]);

        for(Room r : rooms) {
            System.out.printf("Starting walk in room %s with %d unlocked doors%n", r, r.getUnlockedDoors().size());
            walk(r, new ArrayList<Door>(), doors.length);
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
