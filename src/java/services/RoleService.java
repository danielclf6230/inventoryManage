package services;

import dataaccess.RoleDB;
import java.sql.SQLException;
import java.util.List;
import models.Role;

/**
 *
 * @author danielchow
 */
public class RoleService {

    public List<Role> getAll() throws SQLException {

        RoleDB roleDB = new RoleDB();
        List<Role> roles = roleDB.getAll();
        return roles;
    }

    //get single user
    public Role get(int role_id) throws SQLException {
        RoleDB roleDB = new RoleDB();
        Role role = roleDB.get(role_id);
        return role;
    }

}
