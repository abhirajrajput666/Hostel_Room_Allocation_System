import java.io.Serializable;

public class Student implements Serializable {

    private int studentId;
    private String name;
    private int floor;
    private int roomNumber;
    private int bedNumber;

    private double monthlyFee;
    private double pendingAmount;
    private String joiningMonth;

    // Constructor
    public Student(int studentId, String name,
                   int floor, int roomNumber, int bedNumber,
                   double monthlyFee, String joiningMonth) {

        this.studentId = studentId;
        this.name = name;
        this.floor = floor;
        this.roomNumber = roomNumber;
        this.bedNumber = bedNumber;
        this.monthlyFee = monthlyFee;
        this.joiningMonth = joiningMonth;
        this.pendingAmount = 0;
    }

    // ================= GETTERS =================

    public int getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public int getFloor() {
        return floor;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public int getBedNumber() {
        return bedNumber;
    }

    public double getMonthlyFee() {
        return monthlyFee;
    }

    public double getPendingAmount() {
        return pendingAmount;
    }

    public String getJoiningMonth() {
        return joiningMonth;
    }

    //Floor Name Method (NEW ADDITION)
    public String getFloorName() {

        switch (floor) {
            case 1: return "Ground Floor";
            case 2: return "First Floor";
            case 3: return "Second Floor";
            case 4: return "Third Floor";
            default: return "Unknown Floor";
        }
    }

    // ================= SETTERS =================

    public void setName(String name) {
        this.name = name;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public void setBedNumber(int bedNumber) {
        this.bedNumber = bedNumber;
    }

    public void setPendingAmount(double pendingAmount) {
        this.pendingAmount = pendingAmount;
    }

    // ================= FEE LOGIC =================

    public void addMonthlyFee() {
        pendingAmount += monthlyFee;
    }

    public void payFee(double amount) {
        if (amount <= pendingAmount) {
            pendingAmount -= amount;
        } else {
            System.out.println("Payment exceeds pending amount!");
        }
    }

    // ================= DISPLAY =================

    @Override
    public String toString() {
        return "----------------------------------\n" +
                "ID             : " + studentId + "\n" +
                "Name           : " + name + "\n" +
                "Floor          : " + getFloorName() + "\n" +
                "Room Number    : " + roomNumber + "\n" +
                "Bed Number     : " + bedNumber + "\n" +
                "Joining Month  : " + joiningMonth + "\n" +
                "Monthly Fee    : " + monthlyFee + "\n" +
                "Pending Amount : " + pendingAmount + "\n" +
                "----------------------------------";
    }
}
