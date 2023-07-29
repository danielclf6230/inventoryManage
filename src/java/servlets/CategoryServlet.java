package servlets;

import dataaccess.CategoryDB;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Category;
import services.CategoryService;

/**
 *
 * @author danielchow
 */
public class CategoryServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        CategoryService cs = new CategoryService();
        HttpSession session = request.getSession();

        if ((action != null && action.equals("edit"))) {
            try {
                String categoryId = request.getParameter("categoryid");

                Category category = cs.get(Integer.parseInt(categoryId));
                session.setAttribute("selectedCat", category);

            } catch (Exception ex) {
                Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        List<Category> categories = null;

        try {
            categories = cs.getAll();
            session.setAttribute("catlist", categories);

        } catch (SQLException ex) {

            Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        getServletContext().getRequestDispatcher("/WEB-INF/category.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        CategoryService cs = new CategoryService();
        String action = request.getParameter("action");
        String categoryIdIn = request.getParameter("categoryIdIn");
        String categoryNameIn = request.getParameter("categoryNameIn");
        HttpSession session = request.getSession();

        try {
            switch (action) {
                case "add":
                    if (categoryNameIn.equals("") || categoryIdIn.equals("")) {
                        request.setAttribute("message", "All fields are required");
                        request.setAttribute("categoryIdIn", categoryIdIn);
                        request.setAttribute("categoryNameIn", categoryNameIn);
                    } else {
                        cs.insert(Integer.parseInt(categoryIdIn), categoryNameIn);
                    }
                    break;

                case "update":
                    if (categoryNameIn.equals("") || categoryIdIn.equals("")) {
                        request.setAttribute("message", "All fields are required");
                        request.setAttribute("categoryIdIn", categoryIdIn);
                        request.setAttribute("categoryNameIn", categoryNameIn);
                    } else {
                        cs.update(Integer.parseInt(categoryIdIn), categoryNameIn);
                        request.setAttribute("message", "Inventory Updated");
                        session.setAttribute("selectedCat", null);
                    }
                    break;
                case "cancel":
                    session.setAttribute("selectedCat", null);
                    break;
            }
        } catch (Exception ex) {
            String ex1 = ex.getMessage();
            if (ex1.contains("Duplicate entry")) {
                request.setAttribute("categoryIdIn", categoryIdIn);
                request.setAttribute("categoryNameIn", categoryNameIn);
                request.setAttribute("message", "Category existed");
            }
            Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        List<Category> categories = null;

        try {
            categories = cs.getAll();
            session.setAttribute("catlist", categories);

        } catch (SQLException ex) {

            Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        getServletContext().getRequestDispatcher("/WEB-INF/category.jsp")
                .forward(request, response);

    }

}
