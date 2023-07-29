package services;

import dataaccess.RoleDB;
import models.User;
import dataaccess.UserDB;
import java.sql.SQLException;
import java.util.List;
import models.Role;

/**
 *
 * @author danielchow
 */
public class UserService {

    public List<User> getAll() throws SQLException {

        UserDB userDB = new UserDB();
        List<User> users = userDB.getAll();
        return users;
    }

    //get single user
    public User get(String email) throws SQLException {
        UserDB userDB = new UserDB();
        User user = userDB.get(email);
        return user;
    }

    public void insert(String email, Boolean activity, String first_name, String last_name, String password, Role role) throws SQLException {
        User user = new User(email, activity, first_name, last_name, password);

        RoleDB roleDB = new RoleDB();
        Role r = roleDB.get(role.getRoleId());
        user.setRole(r);

        UserDB userDB = new UserDB();
        userDB.insert(user);
    }

    public void update(String email, Boolean activity, String first_name, String last_name, String password, Role role) throws SQLException {
        User user = new User(email, activity, first_name, last_name, password);

        RoleDB roleDB = new RoleDB();
        Role r = roleDB.get(role.getRoleId());
        user.setRole(r);

        UserDB userDB = new UserDB();
        userDB.update(user);
    }

    public void delete(String email) throws SQLException {
        UserDB userDB = new UserDB();
        User user = userDB.get(email);
        userDB.delete(user);
    }

}
