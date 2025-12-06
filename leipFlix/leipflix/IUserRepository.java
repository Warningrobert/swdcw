package leipflix;

import java.util.List;

public interface IUserRepository {
    void addUser(User user);
    User findUser(String username);
    List<User> getUsers();
    void updateUser(User oldUser, User newUser);
}
