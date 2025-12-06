package leipflix;

public class AdminUser extends User {

    public AdminUser(String userName, String password) {
        super(userName, password);
    }

    @Override
    public void play(Streamable content) {
        content.playHD();
    }
}
