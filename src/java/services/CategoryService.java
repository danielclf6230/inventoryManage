package services;

import dataaccess.CategoryDB;
import java.sql.SQLException;
import java.util.List;
import models.Category;

/**
 *
 * @author danielchow
 */
public class CategoryService {

    public List<Category> getAll() throws SQLException {

        CategoryDB categoryDB = new CategoryDB();
        List<Category> categories = categoryDB.getAll();
        return categories;
    }

    //get single user
    public Category get(int categoryId) throws SQLException {
        CategoryDB categoryDB = new CategoryDB();
        Category category = categoryDB.get(categoryId);
        return category;
    }

    public void insert(Integer categoryId, String categoryName) throws SQLException {
        Category category = new Category(categoryId, categoryName);

        CategoryDB categoryDB = new CategoryDB();
        categoryDB.insert(category);
    }

    public void update(Integer categoryId, String categoryName) throws SQLException {
        Category category = new Category(categoryId, categoryName);

        CategoryDB categoryDB = new CategoryDB();
        categoryDB.update(category);
    }
}
