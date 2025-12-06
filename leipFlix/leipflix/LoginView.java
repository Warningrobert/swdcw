package leipflix;

import java.util.Scanner;

public class LoginView {
    private IUserRepository userRepo;
    private IContentRepository contentRepo;
    private PaymentInterface payment;
    private Scanner scanner;
    private UserView userView;

    public LoginView(IUserRepository userRepo, IContentRepository contentRepo, PaymentInterface payment) {
        this.userRepo = userRepo;
        this.contentRepo = contentRepo;
        this.payment = payment;
        this.scanner = new Scanner(System.in);
        this.userView = new UserView(scanner);
    }

    public void start() {
        System.out.println("Welcome to LeipFlix!");
        System.out.println("Choose an option by typing the number next to it:");
        System.out.println("1 - Login");
        System.out.println("2 - Register");

        int selection = getSelection(1, 2);

        if (selection == 1)
            loginScreen();
        else
            registerScreen();
    }

    private int getSelection(int lowerbound, int upperbound) {
        int selection = 0;

        while (selection == 0) {
            if (scanner.hasNextInt())
                selection = scanner.nextInt();
            else {
                scanner.next();
                selection = 0;
            }

            if (selection < lowerbound || selection > upperbound) {
                System.out.println("Invalid input! Try again.");
                selection = 0;
            }
        }

        return selection;
    }

    private void loginScreen() {
        User user = null;
        String userName = "";
        String password = "";

        do {
            System.out.println("Please type your username:");
            userName = scanner.next();

            System.out.println("Please type your password:");
            password = scanner.next();

            user = userRepo.findUser(userName);

            if (user == null || !user.getPassword().equals(password))
                System.out.println("Wrong username or password");

        } while (user == null || !user.getPassword().equals(password));

        userView.displayMainScreen(user, userRepo, contentRepo, payment);
    }

    private void registerScreen() {
        System.out.println("Please type your username:");
        String userName = scanner.next();

        System.out.println("Please type your password:");
        String password = scanner.next();

        User user = new FreeUser(userName, password);
        userRepo.addUser(user);

        userView.displayMainScreen(user, userRepo, contentRepo, payment);
    }
}
