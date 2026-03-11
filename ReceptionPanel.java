import java.util.Scanner;

public class ReceptionPanel {

    static final int MAX_ROOMS = 40;
    static final int BEDS_PER_ROOM = 4;
    static final int MAX_STUDENTS = MAX_ROOMS * BEDS_PER_ROOM;
    static final double DEFAULT_MONTHLY_FEE = 8000;

    public static int receptionMenu(Scanner sc, Student[] students, int count) {

        while (true) {

            System.out.println("\n===== RECEPTION PANEL =====");
            System.out.println("1. Allocate Room");
            System.out.println("2. Display All Students");
            System.out.println("3. Search Student");
            System.out.println("4. Fees Details");
            System.out.println("5. Room-wise Display");
            System.out.println("6. Floor-wise Display");
            System.out.println("7. Update Student");
            System.out.println("8. Deallocate");
            System.out.println("9. Summary");
            System.out.println("10. Logout");

            System.out.print("Enter Choice: ");
            if (!sc.hasNextInt()) {
                System.out.println("Invalid Input!");
                sc.next();
                continue;
            }
            int choice = sc.nextInt();
            sc.nextLine();

            if (choice == 10)
                break;

            switch (choice) {

                // ================= ALLOCATE =================
               case 1:

    System.out.println("\n====================================");
    System.out.println("        ADD NEW STUDENT FORM        ");
    System.out.println("====================================");

    if (count >= MAX_STUDENTS) {
        System.out.println(" Hostel Full!");
        break;
    }

    System.out.print("Enter Student ID        : ");
    int id = sc.nextInt();
    sc.nextLine();

    // Check duplicate ID
    boolean exists = false;
    for (int i = 0; i < count; i++) {
        if (students[i].getStudentId() == id) {
            exists = true;
            break;
        }
    }

    if (exists) {
        System.out.println(" ID Already Exists!");
        break;
    }

    System.out.print("Enter Student Name      : ");
    String name = sc.nextLine();

    System.out.print("Enter Floor (1-4)       : ");
    int floor = sc.nextInt();

    System.out.print("Enter Room Number (1-40): ");
    int room = sc.nextInt();
    sc.nextLine();

    // Bed Allocation
    int bed = 0;

    for (int b = 1; b <= BEDS_PER_ROOM; b++) {
        boolean occupied = false;

        for (int i = 0; i < count; i++) {
            if (students[i].getRoomNumber() == room &&
                students[i].getBedNumber() == b) {
                occupied = true;
                break;
            }
        }

        if (!occupied) {
            bed = b;
            break;
        }
    }

    if (bed == 0) {
        System.out.println(" Room Full!");
        break;
    }

    System.out.print("Enter Joining Month     : ");
    String joiningMonth = sc.nextLine();

    students[count++] = new Student(
            id,
            name,
            floor,
            room,
            bed,
            DEFAULT_MONTHLY_FEE,
            joiningMonth
    );

    DataStore.saveAll(students, count);

    System.out.println("\n====================================");
    System.out.println("  Room Allocated Successfully!");
    System.out.println("Assigned Bed Number : " + bed);
    System.out.println("====================================\n");

    break;
                // ================= DISPLAY ALL =================
                case 2:
                    for (int i = 0; i < count; i++) {
                        System.out.println(students[i]);
                    }
                    break;
                // ================= SEARCH =================
   case 3:

    System.out.println("\n====================================");
    System.out.println("        SEARCH STUDENT RECORD       ");
    System.out.println("====================================");

    sc.nextLine();  // buffer clear

    System.out.print("Enter Student ID or Name : ");
    String input = sc.nextLine();

    boolean found = false;

    for (int i = 0; i < count; i++) {

        // ID se search
        if (String.valueOf(students[i].getStudentId()).equals(input) ||
            students[i].getName().equalsIgnoreCase(input)) {

            System.out.println("\n---------- STUDENT DETAILS ----------");
            System.out.println("Student ID      : " + students[i].getStudentId());
            System.out.println("Name            : " + students[i].getName());
            System.out.println("Floor           : " + students[i].getFloor());
            System.out.println("Room Number     : " + students[i].getRoomNumber());
            System.out.println("Bed Number      : " + students[i].getBedNumber());
            System.out.println("Monthly Fee     : " + students[i].getMonthlyFee());
            System.out.println("Joining Month   : " + students[i].getJoiningMonth());
            System.out.println("--------------------------------------\n");

            found = true;
            break;
        }
    }

    if (!found) {
        System.out.println("\nStudent Not Found!");
    }

    System.out.println("====================================\n");

    break;
                // ================= FEES =================
               case 4:

    System.out.println("\n==========================================");
    System.out.println("           MONTHLY FEE UPDATE             ");
    System.out.println("==========================================");

    System.out.print("Enter Student ID : ");
    int fid = sc.nextInt();
    sc.nextLine();

    Student student = null;

    for (int i = 0; i < count; i++) {
        if (students[i].getStudentId() == fid) {
            student = students[i];
            break;
        }
    }

    if (student == null) {
        System.out.println("\n Student Not Found!");
        System.out.println("==========================================\n");
        break;
    }

    System.out.println("\nStudent Name : " + student.getName());
    System.out.print("Enter Month  : ");
    String month = sc.nextLine();

    double previousPending = student.getPendingAmount();

    // Add monthly fee
    student.addMonthlyFee();

    double monthlyFee = student.getMonthlyFee();
    double totalDue = student.getPendingAmount();

    System.out.println("\n------------ FEE DETAILS ----------------");
    System.out.println("Previous Pending  : " + previousPending);
    System.out.println("Current Month Fee : " + monthlyFee);
    System.out.println("------------------------------------------");
    System.out.println("Total Due         : " + totalDue);
    System.out.println("------------------------------------------");

    System.out.print("Enter Payment Amount : ");
    double payment = sc.nextDouble();
    sc.nextLine();

    student.payFee(payment);

    double newPending = student.getPendingAmount();

    System.out.println("\n------------ PAYMENT SUMMARY ------------");
    System.out.println("Payment Received  : " + payment);
    System.out.println("Remaining Pending : " + newPending);
    System.out.println("------------------------------------------");

    FeeDataStore.saveMonthlyRecord(
            fid,
            student.getName(),
            month,
            previousPending,
            monthlyFee,
            payment,
            newPending
    );

    DataStore.saveAll(students, count);

    System.out.println("\n Monthly Fee Updated Successfully!");
    System.out.println("==========================================\n");

    break;
                // ================= ROOM-WISE =================
                case 5:

    System.out.println("\n====================================");
    System.out.println("        VIEW STUDENTS BY ROOM       ");
    System.out.println("====================================");

    System.out.print("Enter Room Number (1-40) : ");
    int r = sc.nextInt();

    boolean roomFound = false;

    System.out.println("\n--------- ROOM DETAILS -------------");

    for (int i = 0; i < count; i++) {

        if (students[i].getRoomNumber() == r) {

            System.out.println("------------------------------------");
            HostelRoomAllocationSystem.displaySingle(students[i]);
            roomFound = true;
        }
    }

    if (!roomFound) {
        System.out.println(" No Students in This Room!");
    }

    System.out.println("====================================\n");

    break;
                // ================= FLOOR-WISE =================
           case 6:

    System.out.println("\n====================================");
    System.out.println("       VIEW STUDENTS BY FLOOR       ");
    System.out.println("====================================");

    System.out.print("Enter Floor (1-4) : ");
    int f = sc.nextInt();

    boolean floorFound = false;

    System.out.println("\n--------- FLOOR DETAILS ------------");

    for (int i = 0; i < count; i++) {

        if (students[i].getFloor() == f) {

            System.out.println("------------------------------------");
            HostelRoomAllocationSystem.displaySingle(students[i]);
            floorFound = true;
        }
    }

    if (!floorFound) {
        System.out.println(" No Students on This Floor!");
    }

    System.out.println("====================================\n");

    break;
                // ================= UPDATE =================
           case 7:

    System.out.println("\n==========================================");
    System.out.println("           UPDATE STUDENT RECORD          ");
    System.out.println("==========================================");

    System.out.print("Enter Student ID : ");
    int uid = sc.nextInt();
    sc.nextLine();

    boolean updated = false;

    for (int i = 0; i < count; i++) {

        if (students[i].getStudentId() == uid) {

            System.out.println("\nCurrent Name  : " + students[i].getName());
            System.out.println("Current Floor : " + students[i].getFloor());
            System.out.println("Current Room  : " + students[i].getRoomNumber());
            System.out.println("------------------------------------------");

            System.out.print("Enter New Name        : ");
            String newName = sc.nextLine();

            System.out.print("Enter New Floor (1-4) : ");
            int newFloor = sc.nextInt();

            System.out.print("Enter New Room (1-40) : ");
            int newRoom = sc.nextInt();
            sc.nextLine();

            // Floor validation
            if (newFloor < 1 || newFloor > 4) {
                System.out.println("Invalid Floor!");
                break;
            }

            // Room validation
            if (newRoom < 1 || newRoom > MAX_ROOMS) {
                System.out.println("Invalid Room!");
                break;
            }

            int newBed = 0;

            for (int b = 1; b <= BEDS_PER_ROOM; b++) {

                boolean occupied = false;

                for (int j = 0; j < count; j++) {
                    if (students[j].getRoomNumber() == newRoom &&
                        students[j].getBedNumber() == b &&
                        students[j].getStudentId() != uid) {
                        occupied = true;
                        break;
                    }
                }

                if (!occupied) {
                    newBed = b;
                    break;
                }
            }

            if (newBed == 0) {
                System.out.println("Selected Room Full!");
                break;
            }

            // Update values
            students[i].setName(newName);
            students[i].setFloor(newFloor);
            students[i].setRoomNumber(newRoom);
            students[i].setBedNumber(newBed);

            DataStore.saveAll(students, count);

            System.out.println("\nStudent Updated Successfully!");
            System.out.println("New Bed Assigned : " + newBed);
            System.out.println("==========================================\n");

            updated = true;
            break;
        }
    }

    if (!updated) {
        System.out.println("Student Not Found!");
        System.out.println("==========================================\n");
    }

    break;
    case 8:

    System.out.println("\n==========================================");
    System.out.println("           DEALLOCATE STUDENT             ");
    System.out.println("==========================================");

    System.out.print("Enter Student ID : ");
    int did = sc.nextInt();
    sc.nextLine();

    boolean removed = false;

    for (int i = 0; i < count; i++) {

        if (students[i].getStudentId() == did) {

            System.out.println("\nStudent Name : " + students[i].getName());
            System.out.println("Room         : " + students[i].getRoomNumber());
            System.out.println("Bed          : " + students[i].getBedNumber());
            System.out.println("------------------------------------------");

            FeeDataStore.removeStudentRecords(did);
            DeallocatedStudent.save(students[i]);

            for (int j = i; j < count - 1; j++)
                students[j] = students[j + 1];

            students[count - 1] = null;
            count--;

            DataStore.saveAll(students, count);

            System.out.println("Student Deallocated Successfully!");
            System.out.println("==========================================\n");

            removed = true;
            break;
        }
    }

    if (!removed) {
        System.out.println("Student Not Found!");
        System.out.println("==========================================\n");
    }

    break;
    case 9:

    System.out.println("\n====================================");
    System.out.println("            HOSTEL SUMMARY          ");
    System.out.println("====================================");

    System.out.println("Total Students Registered : " + count);
    System.out.println("Total Rooms Available     : " + MAX_ROOMS);
    System.out.println("Total Beds Capacity       : " + MAX_STUDENTS);
    System.out.println("------------------------------------");

    int availableBeds = MAX_STUDENTS - count;
    System.out.println("Available Beds            : " + availableBeds);

    System.out.println("====================================\n");

    break;
                default:
                    System.out.println("Invalid Choice!");
            }
        }
        return count;
    }
}
