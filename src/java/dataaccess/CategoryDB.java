/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataaccess;

/**
 *
 * @author danielchow
 */

import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import models.Category;
import models.Item;


public class CategoryDB {
    
    public List<Category> getAll() throws SQLException {
        
        EntityManager em = DBUtil.getEmFactory().createEntityManager();

        try {
        List<Category> categories = em.createNamedQuery("Category.findAll",Category.class).getResultList();
        return categories;
        } finally {
            em.close();
        }
    }
    
    public Category get(int category_id) throws SQLException {
        
        EntityManager em = DBUtil.getEmFactory().createEntityManager();

        try {
            Category category = em.find(Category.class, category_id);
            return category;
        } finally {
           em.close();
        }
    }
    
        public void insert(Category category) throws SQLException{
        
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
           trans.begin();
           em.persist(category);
           trans.commit();
        }catch(Exception ex){
            trans.rollback();
        }
        finally {
            em.close();
        }
    }
        
        
         public void update(Category category) throws SQLException{
        
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        
        try {
           trans.begin();
           em.merge(category);
           trans.commit();
        }catch(Exception ex){
            trans.rollback();
        }
        finally {
            em.close();
        }
    }
}

