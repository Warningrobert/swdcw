package leipflix;

import java.util.ArrayList;
import java.util.List;

public class UserRepository implements IUserRepository {
    private List<User> users;

    public UserRepository() {
        users = new ArrayList<User>();
    }

    @Override
    public void addUser(User user) {
        if (findUser(user.getUserName()) != null) {
            throw new IllegalArgumentException("Username already exists");
        }
        users.add(user);
    }

    @Override
    public User findUser(String username) {
        for (User u : users) {
            if (u.getUserName().equals(username)) {
                return u;
            }
        }
        return null;
    }

    @Override
    public List<User> getUsers() {
        return users;
    }

    @Override
    public void updateUser(User oldUser, User newUser) {
        int index = users.indexOf(oldUser);
        if (index != -1) {
            users.set(index, newUser);
        }
    }
}
