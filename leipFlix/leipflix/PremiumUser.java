package leipflix;

public class PremiumUser extends User {

    public PremiumUser(String userName, String password) {
        super(userName, password);
    }

    @Override
    public void play(Streamable content) {
        content.playHD();
    }

    public FreeUser unsubscribe() {
        return new FreeUser(getUserName(), getPassword());
    }
}
