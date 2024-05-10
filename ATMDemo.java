import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Account {
    private String accountId;
    private int pin;
    private double balance;

    public Account(String accountId, int pin, double balance) {
        this.accountId = accountId;
        this.pin = pin;
        this.balance = balance;
    }

    public String getAccountId() {
        return accountId;
    }

    public int getPin() {
        return pin;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        this.balance += amount;
    }

    public void withdraw(double amount) {
        if (amount <= balance) {
            this.balance -= amount;
        } else {
            throw new IllegalArgumentException("Insufficient balance");
        }
    }
}

class Transaction {
    private String type;
    private double amount;
    private String details;

    public Transaction(String type, double amount, String details) {
        this.type = type;
        this.amount = amount;
        this.details = details;
    }

    @Override
    public String toString() {
        return type + " - Amount: $" + amount + " - " + details;
    }
}

class TransactionHistory {
    private List<Transaction> transactions;

    public TransactionHistory() {
        this.transactions = new ArrayList<>();
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public void printHistory() {
        if (transactions.isEmpty()) {
            System.out.println("No transactions available.");
        } else {
            for (Transaction transaction : transactions) {
                System.out.println(transaction);
            }
        }
    }
}

class ATM {
    private Account account;
    private TransactionHistory history;

    public ATM(Account account) {
        this.account = account;
        this.history = new TransactionHistory();
    }

    public void login() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter User ID: ");
        String userId = scanner.next();
        System.out.print("Enter PIN: ");
        int pin = scanner.nextInt();

        if (userId.equals(account.getAccountId()) && pin == account.getPin()) {
            showMenu();
        } else {
            System.out.println("Invalid credentials!");
        }
    }

    private void showMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("\n1. Transaction History");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Transfer");
            System.out.println("5. Quit");
            System.out.print("Choose an option: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    history.printHistory();
                    break;
                case 2:
                    performWithdraw();
                    break;
                case 3:
                    performDeposit();
                    break;
                case 4:
                    performTransfer();
                    break;
                case 5:
                    System.out.println("Thank you for using our ATM!");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        } while (choice != 5);
    }

    private void performWithdraw() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter amount to withdraw: ");
        double amount = scanner.nextDouble();
        try {
            account.withdraw(amount);
            history.addTransaction(new Transaction("Withdraw", amount, "Withdrawn from ATM"));
            System.out.println("Withdrawal successful.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void performDeposit() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter amount to deposit: ");
        double amount = scanner.nextDouble();
        account.deposit(amount);
        history.addTransaction(new Transaction("Deposit", amount, "Deposited to ATM"));
        System.out.println("Deposit successful.");
    }

    private void performTransfer() {
        System.out.println("Transfer feature not implemented.");
    }
}

public class ATMDemo {
    public static void main(String[] args) {
        Account account = new Account("123456", 1234, 5000);
        ATM atm = new ATM(account);
        atm.login();
    }
}