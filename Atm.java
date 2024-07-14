import java.util.*;
public class Atm {
    public static void main(String[] args) {
        Bank bank = new Bank();
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter your User ID:");
        String userId = sc.nextLine();
        System.out.println("Enter your PIN:");
        String pin = sc.nextLine();

        User user = bank.authenticateUser(userId, pin);
        if (user != null) {
            System.out.println("Welcome to the ATM!");
            boolean quit = false;
            while (!quit) {
                System.out.println("\nATM Menu:");
                System.out.println("1. Transaction History");
                System.out.println("2. Withdraw");
                System.out.println("3. Deposit");
                System.out.println("4. Transfer");
                System.out.println("5. Quit");
                System.out.print("Choose an option: ");
                int choice = sc.nextInt();
                switch (choice) {
                    case 1:
                        bank.printTransactionHistory(user);
                        break;
                    case 2:
                        System.out.print("Enter amount to withdraw: ");
                        double withdrawAmount = sc.nextDouble();
                        bank.withdraw(user, withdrawAmount);
                        break;
                    case 3:
                        System.out.print("Enter amount to deposit: ");
                        double depositAmount = sc.nextDouble();
                        bank.deposit(user, depositAmount);
                        break;
                    case 4:
                        System.out.print("Enter recipient User ID: ");
                        sc.nextLine();
                        String recipientId = sc.nextLine();
                        System.out.print("Enter amount to transfer: ");
                        double transferAmount = sc.nextDouble();
                        bank.transfer(user, recipientId, transferAmount);
                        break;
                    case 5:
                        quit = true;
                        System.out.println("Thank you for using the ATM. Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        } else {
            System.out.println("Authentication failed. Please try again.");
        }

        sc.close();
    }
}


class Bank {
    private Map<String, User> users;

    public Bank() {
        users = new HashMap<>();
        // Adding some dummy users for testing
        users.put("prashanthi", new User("prashanthi", "1234"));
        users.put("akash", new User("akash", "5678"));
    }

    public User authenticateUser(String userId, String pin) {
        User user = users.get(userId);
        if (user != null && user.getPin().equals(pin)) {
            return user;
        }
        return null;
    }

    public void printTransactionHistory(User user) {
        user.printTransactionHistory();
    }

    public void withdraw(User user, double amount) {
        if (user.withdraw(amount)) {
            System.out.println("Withdrawal successful.");
        } else {
            System.out.println("Insufficient balance.");
        }
    }

    public void deposit(User user, double amount) {
        user.deposit(amount);
        System.out.println("Deposit successful.");
    }

    public void transfer(User user, String recipientId, double amount) {
        User recipient = users.get(recipientId);
        if (recipient == null) {
            System.out.println("Recipient not found.");
            return;
        }
        if (user.withdraw(amount)) {
            recipient.deposit(amount);
            System.out.println("Transfer successful.");
        } else {
            System.out.println("Insufficient balance.");
        }
    }
}
class User {
    private String userId;
    private String pin;
    private double balance;
    private List<String> transactionHistory;

    public User(String userId, String pin) {
        this.userId = userId;
        this.pin = pin;
        this.balance = 0.0;
        this.transactionHistory = new ArrayList<>();
    }

    public String getUserId() {
        return userId;
    }

    public String getPin() {
        return pin;
    }

    public double getBalance() {
        return balance;
    }

    public List<String> getTransactionHistory() {
        return transactionHistory;
    }

    public boolean withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
            transactionHistory.add("Withdraw: " + amount);
            return true;
        }
        return false;
    }

    public void deposit(double amount) {
        balance += amount;
        transactionHistory.add("Deposit: " + amount);
    }

    public void printTransactionHistory() {
        if (transactionHistory.isEmpty()) {
            System.out.println("No transactions found.");
        } else {
            for (String transaction : transactionHistory) {
                System.out.println(transaction);
            }
        }
    }
}