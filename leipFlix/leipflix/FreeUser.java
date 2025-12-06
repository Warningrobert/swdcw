package leipflix;

public class FreeUser extends User {

    public FreeUser(String userName, String password) {
        super(userName, password);
    }

    @Override
    public void play(Streamable content) {
        content.playWithAds();
    }

    public PremiumUser subscribe(PaymentInterface payment) {
        payment.performPayment();
        return new PremiumUser(getUserName(), getPassword());
    }
}
