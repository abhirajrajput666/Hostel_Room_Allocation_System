import java.io.*;

public class FeeDataStore {

    private static final String FILE_NAME = "fees.txt";

    // ================= SAVE MONTHLY RECORD =================
    public static void saveMonthlyRecord(int studentId,
                                         String studentName,
                                         String month,
                                         double previousPending,
                                         double monthlyFee,
                                         double payment,
                                         double newPending) {

        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME, true))) {

            pw.println("========== Monthly Fee Record ==========");
            pw.println("Student ID        : " + studentId);
            pw.println("Student Name      : " + studentName);
            pw.println("Month             : " + month);
            pw.println("Previous Pending  : " + previousPending);
            pw.println("Monthly Fee       : " + monthlyFee);
            pw.println("Payment Made      : " + payment);
            pw.println("New Pending       : " + newPending);
            pw.println("=========================================");
            pw.println();

        } catch (IOException e) {
            System.out.println("Error Saving Monthly Fee Record!");
            e.printStackTrace();
        }
    }

    // ================= VIEW STUDENT HISTORY =================
    public static void viewStudentHistory(int studentId) {

        File file = new File(FILE_NAME);

        if (!file.exists()) {
            System.out.println("No Fee Records Found!");
            return;
        }

        boolean found = false;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            String line;
            boolean printBlock = false;

            while ((line = br.readLine()) != null) {

                if (line.startsWith("Student ID")) {

                    int id = Integer.parseInt(line.split(":")[1].trim());

                    if (id == studentId) {
                        printBlock = true;
                        found = true;
                    } else {
                        printBlock = false;
                    }
                }

                if (printBlock) {
                    System.out.println(line);
                }

                if (line.startsWith("=========================================")) {
                    printBlock = false;
                }
            }

        } catch (IOException e) {
            System.out.println("Error Reading Fees File!");
            e.printStackTrace();
        }

        if (!found) {
            System.out.println("No Records Found for Student ID: " + studentId);
        }
    }

    // ================= REMOVE ALL RECORDS OF A STUDENT =================
    public static void removeStudentRecords(int studentId) {

        File inputFile = new File(FILE_NAME);
        File tempFile = new File("temp_fees.txt");

        if (!inputFile.exists()) {
            System.out.println("No file found to update.");
            return;
        }

        try (
            BufferedReader br = new BufferedReader(new FileReader(inputFile));
            PrintWriter pw = new PrintWriter(new FileWriter(tempFile))
        ) {

            String line;
            boolean skipBlock = false;

            while ((line = br.readLine()) != null) {

                if (line.startsWith("Student ID")) {

                    int id = Integer.parseInt(line.split(":")[1].trim());

                    if (id == studentId) {
                        skipBlock = true;
                    } else {
                        skipBlock = false;
                    }
                }

                if (!skipBlock) {
                    pw.println(line);
                }

                if (line.startsWith("=========================================")) {
                    skipBlock = false;
                }
            }

        } catch (IOException e) {
            System.out.println("Error Updating Fees File!");
            e.printStackTrace();
            return;
        }

        // Replace original file safely
        if (inputFile.delete()) {
            if (tempFile.renameTo(inputFile)) {
                System.out.println("Records removed successfully.");
            } else {
                System.out.println("Error renaming temp file.");
            }
        } else {
            System.out.println("Error deleting original file.");
        }
    }
}
