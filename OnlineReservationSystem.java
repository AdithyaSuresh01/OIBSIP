import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class User {
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }
}

class Train {
    private String trainNumber;
    private String trainName;

    public Train(String trainNumber, String trainName) {
        this.trainNumber = trainNumber;
        this.trainName = trainName;
    }

    public String getTrainNumber() {
        return trainNumber;
    }

    public String getTrainName() {
        return trainName;
    }
}

class Reservation {
    private String reservationId;
    private User user;
    private Train train;
    private String classType;
    private String dateOfJourney;
    private String fromPlace;
    private String toDestination;

    public Reservation(String reservationId, User user, Train train, String classType, String dateOfJourney, String fromPlace, String toDestination) {
        this.reservationId = reservationId;
        this.user = user;
        this.train = train;
        this.classType = classType;
        this.dateOfJourney = dateOfJourney;
        this.fromPlace = fromPlace;
        this.toDestination = toDestination;
    }

    public String getDetails() {
        return "Reservation ID: " + reservationId + "\nTrain: " + train.getTrainName() +
               " [" + train.getTrainNumber() + "]\nClass: " + classType + "\nDate: " + dateOfJourney +
               "\nFrom: " + fromPlace + " To: " + toDestination;
    }

    public String getReservationId() {
        return reservationId;
    }
}

class OnlineReservationSystem {
    private List<User> users = new ArrayList<>();
    private List<Train> trains = new ArrayList<>();
    private List<Reservation> reservations = new ArrayList<>();
    private User loggedInUser;

    public OnlineReservationSystem() {
        // Seed some data
        users.add(new User("admin", "admin"));
        trains.add(new Train("101", "Express A"));
        trains.add(new Train("102", "Express B"));
    }

    public void login() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Username: ");
        String username = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        for (User user : users) {
            if (user.getUsername().equals(username) && user.checkPassword(password)) {
                loggedInUser = user;
                System.out.println("Login successful");
                return;
            }
        }
        System.out.println("Login failed");
    }

    public void makeReservation() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Available Trains:");
        for (Train train : trains) {
            System.out.println("Number: " + train.getTrainNumber() + ", Name: " + train.getTrainName());
        }

        System.out.print("Enter Train Number: ");
        String trainNumber = scanner.nextLine();
        Train selectedTrain = trains.stream()
                                    .filter(t -> t.getTrainNumber().equals(trainNumber))
                                    .findFirst()
                                    .orElse(null);

        if (selectedTrain == null) {
            System.out.println("Train not found!");
            return;
        }

        System.out.print("Class Type (First/Second): ");
        String classType = scanner.nextLine();
        System.out.print("Date of Journey (dd/MM/yyyy): ");
        String date = scanner.nextLine();
        System.out.print("From Place: ");
        String from = scanner.nextLine();
        System.out.print("To Destination: ");
        String to = scanner.nextLine();

        String reservationId = "RES" + (reservations.size() + 1);
        Reservation newReservation = new Reservation(reservationId, loggedInUser, selectedTrain, classType, date, from, to);
        reservations.add(newReservation);
        System.out.println("Reservation Successful! Reservation ID: " + reservationId);
    }

    public void cancelReservation() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Reservation ID: ");
        String reservationId = scanner.nextLine();

        Reservation reservation = reservations.stream()
                                              .filter(r -> r.getReservationId().equals(reservationId))
                                              .findFirst()
                                              .orElse(null);

        if (reservation == null) {
            System.out.println("Reservation not found!");
            return;
        }

        System.out.println("Reservation Details:");
        System.out.println(reservation.getDetails());
        System.out.print("Confirm cancellation (yes/no): ");
        String confirm = scanner.nextLine();

        if ("yes".equalsIgnoreCase(confirm)) {
            reservations.remove(reservation);
            System.out.println("Reservation cancelled successfully.");
        } else {
            System.out.println("Cancellation aborted.");
        }
    }

    public static void main(String[] args) {
        OnlineReservationSystem system = new OnlineReservationSystem();
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n1. Login");
            System.out.println("2. Make Reservation");
            System.out.println("3. Cancel Reservation");
            System.out.println("4. Quit");
            System.out.print("Select an option: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    system.login();
                    break;
                case 2:
                    if (system.loggedInUser != null) {
                        system.makeReservation();
                    } else {
                        System.out.println("Please login first.");
                    }
                    break;
                case 3:
                    if (system.loggedInUser != null) {
                        system.cancelReservation();
                    } else {
                        System.out.println("Please login first.");
                    }
                    break;
                case 4:
                    System.out.println("Exiting system.");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        } while (choice != 4);
    }
}