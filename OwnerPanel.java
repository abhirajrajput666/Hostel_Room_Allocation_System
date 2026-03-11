import java.util.Scanner;

public class OwnerPanel {
    public static int ownerMenu(Scanner sc, Student[] students, int count) {

        while (true) {

            System.out.println("\n===== OWNER PANEL =====");
            System.out.println("1. Display All Students");
            System.out.println("2. Search Student");
            System.out.println("3. Summary");
            System.out.println("4. Logout");
            System.out.print("Enter Choice: ");

            if (!sc.hasNextInt()) {
                System.out.println("Invalid Input!");
                sc.next();
                continue;
            }
            int choice = sc.nextInt();
            sc.nextLine();   // buffer clear

            if (choice == 4)
                break;

            switch (choice) {

                case 1:
                    HostelRoomAllocationSystem.displayStudents(students, count);
                    break;

                case 2:
                    System.out.print("Enter ID: ");
                    int sid = sc.nextInt();
                    sc.nextLine();

                    boolean found = false;
                    for (int i = 0; i < count; i++) {
                        if (students[i].getStudentId() == sid) {
                            HostelRoomAllocationSystem.displaySingle(students[i]);
                            found = true;
                            break;
                        }
                    }
                    if (!found)
                        System.out.println("Student Not Found!");
                    break;

                case 3:
                    System.out.println("Total Students: " + count);
                    System.out.println("Total Rooms: " + HostelRoomAllocationSystem.MAX_ROOMS);
                    System.out.println("Total Beds: " + HostelRoomAllocationSystem.MAX_STUDENTS);
                    break;
                default:
                    System.out.println("Invalid Choice!");
            }
        }
        return count;   //IMPORTANT
    }
}
