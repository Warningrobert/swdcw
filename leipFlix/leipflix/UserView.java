package leipflix;

import java.util.Scanner;

public class UserView {
    private Scanner scanner;

    public UserView(Scanner scanner) {
        this.scanner = scanner;
    }

    public User displayMainScreen(User user, IUserRepository userRepo, IContentRepository contentRepo, PaymentInterface payment) {
        System.out.println("Welcome to LeipFlix, " + user.getUserName() + "!");

        int selection = 0;

        do {
            System.out.println("Select an option among the following:");
            System.out.println("1 - edit profile");
            System.out.println("2 - browse and play movies");
            System.out.println("3 - logout");
            if (user instanceof AdminUser)
                System.out.println("4 - add an admin account");

            if (user instanceof AdminUser)
                selection = getSelection(1, 4);
            else
                selection = getSelection(1, 3);

            switch (selection) {
                case 1:
                    user = editScreen(user, userRepo, payment);
                    break;
                case 2:
                    browseScreen(user, contentRepo);
                    break;
                case 4:
                    adminScreen(userRepo);
                    break;
                default:
                    break;
            }

        } while (selection != 3);

        System.out.println("Goodbye, " + user.getUserName() + "!");
        return user;
    }

    private User editScreen(User user, IUserRepository userRepo, PaymentInterface payment) {
        int selection = 0;

        do {
            System.out.println("Select an option among the following:");
            System.out.println("1 - change password");
            System.out.println("2 - change number of movies per page");
            System.out.println("3 - go back");
            if (user instanceof FreeUser)
                System.out.println("4 - subscribe to premium");
            if (user instanceof PremiumUser)
                System.out.println("4 - unsubscribe");

            selection = getSelection(1, 4);

            switch (selection) {
                case 1:
                    System.out.println("Input new password:");
                    user.setPassword(scanner.next());
                    System.out.println("Password changed successfully");
                    System.out.println();
                    break;
                case 2:
                    setMoviesPerPage(user);
                    break;
                case 4:
                    if (user instanceof FreeUser) {
                        User newUser = ((FreeUser) user).subscribe(payment);
                        newUser.setMoviesPerPage(user.getMoviesPerPage());
                        userRepo.updateUser(user, newUser);
                        user = newUser;
                    } else if (user instanceof PremiumUser) {
                        User newUser = ((PremiumUser) user).unsubscribe();
                        newUser.setMoviesPerPage(user.getMoviesPerPage());
                        userRepo.updateUser(user, newUser);
                        user = newUser;
                    }
                    break;
                default:
                    break;
            }

        } while (selection != 3);

        return user;
    }

    private void browseScreen(User user, IContentRepository contentRepo) {
        int selection = 0;
        int movie;
        int currentPage = 0;
        int moviesPerPage = user.getMoviesPerPage();
        int totalMovies = contentRepo.getContent().size();
        int totalPages = (totalMovies + moviesPerPage - 1) / moviesPerPage;

        do {
            System.out.println("Select an option among the following:");
            System.out.println("1 - browse movies");
            System.out.println("2 - play a movie");
            System.out.println("3 - go back");

            selection = getSelection(1, 3);

            switch (selection) {
                case 1:
                    int pageSelection = 0;
                    do {
                        int start = currentPage * moviesPerPage;
                        int end = Math.min(start + moviesPerPage, totalMovies);

                        System.out.println("Page " + (currentPage + 1) + " of " + totalPages);
                        for (int i = start; i < end; i++) {
                            System.out.println(i + " - " + contentRepo.getContent().get(i).toString());
                        }

                        System.out.println();
                        System.out.println("1 - next page");
                        System.out.println("2 - previous page");
                        System.out.println("3 - go back");

                        pageSelection = getSelection(1, 3);

                        if (pageSelection == 1 && currentPage < totalPages - 1) {
                            currentPage++;
                        } else if (pageSelection == 2 && currentPage > 0) {
                            currentPage--;
                        }
                    } while (pageSelection != 3);
                    break;
                case 2:
                    System.out.println("Select the movie based on associated id when browsing");
                    movie = getSelection(0, contentRepo.getContent().size() - 1);
                    user.play(contentRepo.getContent().get(movie));
                    break;
                default:
                    break;
            }

        } while (selection != 3);
    }

    private void adminScreen(IUserRepository userRepo) {
        System.out.println("Type the username of the new admin account:");
        String name = scanner.next();
        System.out.println("Type the password of the new admin account:");
        String password = scanner.next();
        userRepo.addUser(new AdminUser(name, password));
        System.out.println("New admin " + name + " added successfully");
    }

    private void setMoviesPerPage(User user) {
        int selection = 0;

        do {
            System.out.println("How many movies per page while browsing? Only possible options are 5, 10, or 15");

            if (scanner.hasNextInt())
                selection = scanner.nextInt();
            else
                scanner.next();

        } while (selection != 5 && selection != 10 && selection != 15);

        user.setMoviesPerPage(selection);

        System.out.println("Number of movies per page set up correctly!");
        System.out.println();
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
}
