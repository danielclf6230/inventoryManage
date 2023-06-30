/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import dataaccess.RoleDB;
import dataaccess.UserDB;
import java.sql.SQLException;
import java.util.List;
import models.Role;
import models.User;

/**
 *
 * @author danielchow
 */
public class RoleService {
    
        public List<Role> getAll() throws SQLException{
        
        RoleDB roleDB = new RoleDB();
        List<Role> roles = roleDB.getAll();
        return roles;
    }
    
    //get single user
    public Role get(int role_id) throws SQLException{
        RoleDB roleDB = new RoleDB();
        Role role = roleDB.get(role_id);
        return role;
    }
    
}
