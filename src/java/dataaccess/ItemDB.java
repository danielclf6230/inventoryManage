package dataaccess;

import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import models.Category;
import models.Item;

/**
 *
 * @author danielchow
 */
public class ItemDB {

    public List<Item> getAll() throws SQLException {

        EntityManager em = DBUtil.getEmFactory().createEntityManager();

        try {
            List<Item> items = em.createNamedQuery("Item.findAll", Item.class).getResultList();
            return items;
        } finally {
            em.close();
        }
    }

    public List<Item> getAll(String email) throws SQLException {

        EntityManager em = DBUtil.getEmFactory().createEntityManager();

        try {
            TypedQuery<Item> query = em.createNamedQuery("Item.findByOwner", Item.class);
            query.setParameter("email", email);

            List<Item> items = query.getResultList();
            return items;
        } finally {
            em.close();
        }
    }

    public Item get(Integer itemId) throws SQLException {

        EntityManager em = DBUtil.getEmFactory().createEntityManager();

        try {
            Item item = em.find(Item.class, itemId);
            return item;
        } finally {
            em.close();
        }
    }

    public void insert(Item item) throws SQLException {

        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            Category category = item.getCategory();
            category.getItemList().add(item);
            trans.begin();
            em.persist(item);
            em.merge(category);
            trans.commit();
        } catch (Exception ex) {
            trans.rollback();
        } finally {
            em.close();
        }
    }

    public void update(Item item) throws SQLException {

        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();

        try {
            trans.begin();
            em.merge(item);
            trans.commit();
        } catch (Exception ex) {
            trans.rollback();
        } finally {
            em.close();
        }
    }

    public void delete(Item item) throws SQLException {

        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();

        try {
            Category category = item.getCategory();
            category.getItemList().remove(item);

            trans.begin();
            em.remove(em.merge(item));
            em.merge(category);
            trans.commit();
        } catch (Exception ex) {
            trans.rollback();
        } finally {
            em.close();
        }
    }

}
