package atm.model;

public class UserAccount {
    private String name;
    private String lastName;
    private String cardNumber;
    private String pinCode;
    private int balance;
    public enum Color {
        ANSI_RESET("\u001B[0m"),
        ANSI_RED("\u001B[31m"),
        ANSI_GREEN("\u001B[32m"),
        ;
        private final String color;

        Color(String color) {
            this.color = color;
        }

    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name.length() > 2) {
            this.name = name;
        } else {
            System.out.println(Color.ANSI_RED +" "+ "Имени должна быть больше 2 буквы!" + ""+ Color.ANSI_RESET);
        }
    }
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        if (lastName.length() > 2) {
            this.lastName = lastName;
        } else {
            System.out.println(Color.ANSI_RED + "Длина фамилия должна быть больше 2 буквы!" + Color.ANSI_RESET);
        }
    }
    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "UserAccount{" +
                "name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", cardNumber='" + cardNumber + '\'' +
                ", pinCode='" + pinCode + '\'' +
                ", balance=" + balance +
                '}';
    }
}
