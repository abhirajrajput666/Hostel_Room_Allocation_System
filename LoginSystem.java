import java.util.Scanner;

public class LoginSystem {

    private static String ownerUser = "owner";
    private static String ownerPass = "1234";

    private static String receptionUser = "reception";
    private static String receptionPass = "1234";

    public static String login() {

        Scanner sc = new Scanner(System.in);

        while (true) {

            System.out.println("\n===== HOSTEL LOGIN =====");
            System.out.println("1. Owner");
            System.out.println("2. Reception");
            System.out.println("3. Exit");
            System.out.print("Choose Role: ");

            int roleChoice = sc.nextInt();
            sc.nextLine();

            if (roleChoice == 3)
                return "EXIT";

            String role = "";

            if (roleChoice == 1)
                role = "Owner";
            else if (roleChoice == 2)
                role = "Reception";
            else
                continue;

            while (true) {
                System.out.println("\n--- " + role + " Panel ---");
                System.out.println("1. Login");
                System.out.println("2. Register");
                System.out.println("3. Back");
                System.out.print("Choose Option: ");

                int option = sc.nextInt();
                sc.nextLine();

                if (option == 3)
                    break;

                if (option == 1) {
                    System.out.print("Username: ");
                    String username = sc.nextLine();
                    System.out.print("Password: ");
                    String password = sc.nextLine();

                    if (role.equals("Owner") &&
                            username.equals(ownerUser) &&
                            password.equals(ownerPass)) {
                        System.out.println("Login Successful as Owner");
                        return "Owner";
                    }
                    if (role.equals("Reception") &&
                            username.equals(receptionUser) &&
                            password.equals(receptionPass)) {
                        System.out.println("Login Successful as Reception");
                        return "Reception";
                    }
                    System.out.println("Invalid Credentials!");

                } else if (option == 2) {
                    System.out.print("Set Username: ");
                    String newUser = sc.nextLine();
                    System.out.print("Set Password: ");
                    String newPass = sc.nextLine();

                    if (role.equals("Owner")) {
                        ownerUser = newUser;
                        ownerPass = newPass;
                        System.out.println("Owner Registered Successfully!");
                    } else {
                        receptionUser = newUser;
                        receptionPass = newPass;
                        System.out.println("Reception Registered Successfully!");
                    }
                } else {
                    System.out.println("Invalid Option!");
                }
            }
        }
    }
}
