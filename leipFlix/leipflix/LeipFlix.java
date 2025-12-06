package leipflix;

public class LeipFlix {

    public static void main(String[] args) {
        PaymentInterface payment = new PaymentSystem();

        IUserRepository userRepo = new UserRepository();
        IContentRepository contentRepo = new ContentRepository();

        setUpSystem(userRepo, contentRepo);

        LoginView loginView = new LoginView(userRepo, contentRepo, payment);

        loginView.start();
    }

    private static void setUpSystem(IUserRepository userRepo, IContentRepository contentRepo) {
        for (int i = 0; i < 2; i++) {
            userRepo.addUser(new AdminUser("admin" + i, "admin" + i));
        }

        for (int i = 0; i < 10; i++) {
            if (i < 5) {
                userRepo.addUser(new FreeUser("user" + i, "user" + i));
            } else {
                userRepo.addUser(new PremiumUser("user" + i, "user" + i));
            }
        }

        for (int i = 0; i < 25; i++) {
            String title = "movie" + i;
            contentRepo.addContent(new Movie(title));
        }
    }
}
