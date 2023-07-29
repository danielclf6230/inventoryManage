/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import dataaccess.CategoryDB;
import dataaccess.ItemDB;
import dataaccess.UserDB;
import models.Item;
import java.sql.SQLException;
import java.util.List;
import models.Category;
import models.User;

/**
 *
 * @author danielchow
 */
public class ItemService {
    
    public List<Item> getAll() throws SQLException{
        
        ItemDB itemDB = new ItemDB();
        List<Item> items = itemDB.getAll();
        return items;
    }
    
    public List<Item> getAll(String owner) throws SQLException{
        ItemDB itemDB = new ItemDB();
        List<Item> items = itemDB.getAll(owner);
        return items;
    }
    
    //get single user
    public Item get(Integer itemId) throws SQLException{
        ItemDB itemDB = new ItemDB();
        Item item = itemDB.get(itemId);
        return item;
    }
    
    
    public void insert(Integer itemId, Category category, String itemName, double price, String email) throws SQLException{
        Item item = new Item(itemId,itemName,price);
        
        CategoryDB categoryDB = new CategoryDB();
        Category c = categoryDB.get(category.getCategoryId());
        item.setCategory(c);
        UserDB userDB = new UserDB();
        User u = userDB.get(email);
        item.setOwner(u);
        
        ItemDB itemDB = new ItemDB();
        itemDB.insert(item);
    }
    
    public void update(Integer itemId, Category category, String itemName, double price, String email) throws SQLException{
       Item item = new Item(itemId,itemName,price);
        
        CategoryDB categoryDB = new CategoryDB();
        Category c = categoryDB.get(category.getCategoryId());
        item.setCategory(c);
        UserDB userDB = new UserDB();
        User u = userDB.get(email);
        item.setOwner(u);
        
        ItemDB itemDB = new ItemDB();
        itemDB.update(item);
    }
     
      public void delete(Integer itemId) throws SQLException{
        ItemDB itemDB = new ItemDB();
        Item item = itemDB.get(itemId);
        itemDB.delete(item);
    }
      
}
