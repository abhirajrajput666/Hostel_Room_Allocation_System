import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class DeallocatedStudent {

    public static void save(Student s) {

        try {

            PrintWriter pw = new PrintWriter(new FileWriter("deallocated_students.txt", true));

            pw.println("ID: " + s.getStudentId());
            pw.println("Name: " + s.getName());
            pw.println("Room: " + s.getRoomNumber());
            pw.println("Floor: " + s.getFloor());
            pw.println("Bed: " + s.getBedNumber());
            pw.println("-----------------------------------");

            pw.close();

        } catch (IOException e) {

            System.out.println("Error saving deallocated student.");
        }
    }
}
