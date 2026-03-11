import java.util.Scanner;

public class HostelRoomAllocationSystem {

    // ================= HOSTEL CONSTANTS =================
    public static final int MAX_ROOMS = 40;
    public static final int BEDS_PER_ROOM = 4;
    public static final int MAX_STUDENTS = MAX_ROOMS * BEDS_PER_ROOM;

    // ================= MAIN METHOD =================
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        Student[] students = new Student[MAX_STUDENTS];

        // ===== LOAD STUDENT DATA FROM FILE =====
        int count = DataStore.load(students);
        
        System.out.println("==========================================");
        System.out.println("     HOSTEL ROOM ALLOCATION SYSTEM");
        System.out.println("==========================================");

        while (true) {

            // ===== LOGIN SYSTEM CALL =====
            String role = LoginSystem.login();

            if (role.equalsIgnoreCase("EXIT")) {
                break;
            }
            // ===== ROLE BASED PANEL ACCESS =====
            switch (role.toLowerCase()) {

                case "owner":
                    count = OwnerPanel.ownerMenu(sc, students, count);
                    break; 

                case "reception":
                    count = ReceptionPanel.receptionMenu(sc, students, count);
                    break;
                default:
                    System.out.println("Invalid Role! Try Again.");
            }
        }
        // ===== SAVE DATA BEFORE EXIT =====
        DataStore.saveAll(students, count);

        System.out.println("==========================================");
        System.out.println("       System Closed Successfully!        ");
        System.out.println("         Thank You For Visiting           ");
        System.out.println("==========================================");

        sc.close();
    }
    // ================= DISPLAY ALL STUDENTS =================
    public static void displayStudents(Student[] students, int count) {
        if (count == 0) {
            System.out.println("No Students Found!");
            return;
        }
        for (int i = 0; i < count; i++) {
            displaySingle(students[i]);
        }
    }
    // ================= DISPLAY SINGLE STUDENT =================
    public static void displaySingle(Student s) {

        String floorName = getFloorName(s.getFloor());
        System.out.println("====================================");
        System.out.println("Student ID   : " + s.getStudentId());
        System.out.println("Student Name : " + s.getName());
        System.out.println("Room Number  : " + s.getRoomNumber());
        System.out.println("Floor        : " + floorName);
        System.out.println("Bed Number   : " + s.getBedNumber());
        System.out.println("====================================");
        System.out.println();
    }
    // ================= FLOOR NAME CONVERTER =================
    public static String getFloorName(int floor) {

        switch (floor) {
            case 1: return "Ground Floor";
            case 2: return "First Floor";
            case 3: return "Second Floor";
            case 4: return "Third Floor";
            default: return "Unknown Floor";
        }
    }
    // ================= SEARCH STUDENT BY ID =================
    public static Student searchById(Student[] students, int count, int id) {

        for (int i = 0; i < count; i++) {
            if (students[i].getStudentId() == id) {
                return students[i];
            }
        }
        return null;
    }
    // ================= REMOVE STUDENT (DEALLOCATE SUPPORT) =================
    public static int removeStudent(Student[] students, int count, int id) {

        for (int i = 0; i < count; i++) {
            if (students[i].getStudentId() == id) {

                // Save to Deallocated file
                DeallocatedStudent.save(students[i]);

                // Shift array left
                for (int j = i; j < count - 1; j++) {
                    students[j] = students[j + 1];
                }
                students[count - 1] = null;
                count--;
                DataStore.saveAll(students, count);
                System.out.println("Student Deallocated Successfully!");
                return count;
            }
        }
        System.out.println("Student Not Found!");
        return count;
    }
}
