package dataaccess;

import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import models.Role;

/**
 *
 * @author danielchow
 */
public class RoleDB {

    public List<Role> getAll() throws SQLException {

        EntityManager em = DBUtil.getEmFactory().createEntityManager();

        try {
            List<Role> roles = em.createNamedQuery("Role.findAll", Role.class).getResultList();
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
