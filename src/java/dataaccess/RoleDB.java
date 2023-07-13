/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataaccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import models.Role;
import models.User;

/**
 *
 * @author danielchow
 */
public class RoleDB {
    
    public List<Role> getAll() throws SQLException {
        
        EntityManager em = DBUtil.getEmFactory().createEntityManager();

        try {
        List<Role> roles = em.createNamedQuery("Role.findAll",Role.class).getResultList();
        return roles;
        } finally {
            em.close();
        }
    }
    
    public Role get(int role_id) throws SQLException {
        
        EntityManager em = DBUtil.getEmFactory().createEntityManager();

        try {
            Role role = em.find(Role.class, role_id);
            return role;
        } finally {
           em.close();
        }
    }
}
