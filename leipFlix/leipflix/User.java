package leipflix;

public abstract class User {
    private String userName;
    private String password;
    private int moviesPerPage;

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
        this.moviesPerPage = 5;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getMoviesPerPage() {
        return moviesPerPage;
    }

    public void setMoviesPerPage(int moviesPerPage) {
        this.moviesPerPage = moviesPerPage;
    }

    public abstract void play(Streamable content);
}
