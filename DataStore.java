import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DataStore {

    private static final String FILE_NAME = "hostel_data.txt";

    // Load students from file
    public static int load(Student[] students) {

        int count = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {

            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("Student ID")) {

                    int id = Integer.parseInt(line.split(":")[1].trim());
                    String name = br.readLine().split(":")[1].trim();

                    // Floor parsing with fallback in case old file has text
                    String floorStr = br.readLine().split(":")[1].trim();
                    int floor = parseFloor(floorStr);

                    int room = Integer.parseInt(br.readLine().split(":")[1].trim());
                    int bed = Integer.parseInt(br.readLine().split(":")[1].trim());
                    String joiningMonth = br.readLine().split(":")[1].trim();
                    double monthlyFee = Double.parseDouble(br.readLine().split(":")[1].trim());
                    double pending = Double.parseDouble(br.readLine().split(":")[1].trim());

                    br.readLine(); // skip separator

                    Student s = new Student(id, name, floor, room, bed, monthlyFee, joiningMonth);
                    s.setPendingAmount(pending);
                    students[count++] = s;
                }
            }

        } catch (IOException e) {
            // File might not exist yet; ignore
        }

        return count;
    }

    // Save all students to file
    public static void saveAll(Student[] students, int count) {

        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME))) {

            LocalDate currentDate = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

            for (int i = 0; i < count; i++) {

                pw.printf("Student ID     : %d%n", students[i].getStudentId());
                pw.printf("Student Name   : %s%n", students[i].getName());
                pw.printf("Floor          : %d%n", students[i].getFloor()); // always store number
                pw.printf("Room Number    : %d%n", students[i].getRoomNumber());
                pw.printf("Bed Number     : %d%n", students[i].getBedNumber());
                pw.printf("Joining Month  : %s%n", students[i].getJoiningMonth());
                pw.printf("Monthly Fee    : %.2f%n", students[i].getMonthlyFee());
                pw.printf("Pending Amount : %.2f%n", students[i].getPendingAmount());
                pw.printf("Saved Date     : %s%n", currentDate.format(formatter));
                pw.println("----------------------------------------");
            }

        } catch (IOException e) {
            System.out.println("Error Saving Data!");
        }
    }

    // Helper: convert old text floors to numbers
    private static int parseFloor(String floorStr) {
        switch (floorStr.toLowerCase()) {
            case "first floor": return 1;
            case "second floor": return 2;
            case "third floor": return 3;
            case "fourth floor": return 4;
            default:
                try {
                    return Integer.parseInt(floorStr); // fallback for numbers
                } catch (NumberFormatException e) {
                    return 1; // default to 1 if everything fails
                }
        }
    }
}