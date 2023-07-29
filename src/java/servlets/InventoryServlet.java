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
import models.Item;
import services.CategoryService;
import services.ItemService;

/**
 *
 * @author danielchow
 */
public class InventoryServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        String linkitemId = request.getParameter("itemid");
        ItemService is = new ItemService();
        CategoryService cs = new CategoryService();

        if (action != null && action.equals("muser")) {
            response.sendRedirect("admin");
        } else if (action != null && action.equals("macc")) {
            response.sendRedirect("user");
        } else {
            HttpSession session = request.getSession();
            int roleid = (int) session.getAttribute("roleid");
            String useremail = (String) session.getAttribute("useremail");

            if ((action != null && action.equals("edit"))) {
                try {
                    Item item = is.get(Integer.parseInt(linkitemId));
                    session.setAttribute("selectedItem", item);

                } catch (Exception ex) {
                    Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (action != null && action.equals("delete")) {
                try {
                    is.delete(Integer.parseInt(linkitemId));
                } catch (Exception ex) {
                    Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            List<Item> items = null;
            List<Category> categories = null;

            if (roleid == 1) {
                try {
                    items = is.getAll();
                    session.setAttribute("itemlist", items);
                    categories = cs.getAll();
                    session.setAttribute("categorylist", categories);

                } catch (SQLException ex) {

                    Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                if (roleid == 2) {
                    try {
                        items = is.getAll(useremail);
                        session.setAttribute("itemlist", items);
                        categories = cs.getAll();
                        session.setAttribute("categorylist", categories);

                    } catch (SQLException ex) {

                        Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

            getServletContext().getRequestDispatcher("/WEB-INF/inventory.jsp")
                    .forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ItemService is = new ItemService();
        CategoryService cs = new CategoryService();

        // action must be one of: create, update, delete
        String action = request.getParameter("action");
        String itemIdIn = request.getParameter("itemIdIn");
        String itemNameIn = request.getParameter("itemNameIn");
        String priceIn = request.getParameter("priceIn");
        String emailIn = request.getParameter("emailIn");
        CategoryDB categoryDB = new CategoryDB();
        HttpSession session = request.getSession();
        int roleid = (int) session.getAttribute("roleid");
        String useremail = (String) session.getAttribute("useremail");

        try {
            switch (action) {
                case "add":
                    if (itemIdIn.equals("") || itemNameIn.equals("") || priceIn.equals("") || emailIn.equals("")) {
                        request.setAttribute("message", "All fields are required");
                        request.setAttribute("itemNameIn", itemNameIn);
                        request.setAttribute("priceIn", priceIn);
                    } else if (Integer.parseInt(priceIn) < 0) {
                        request.setAttribute("message", "Price cannot be negative");
                    } else {
                        int categoryId = Integer.parseInt(request.getParameter("category"));
                        Category category = categoryDB.get(categoryId);
                        int puitemId = Integer.parseInt(itemIdIn);
                        double putprice = Double.parseDouble(priceIn);
                        is.insert(puitemId, category, itemNameIn, putprice, emailIn);
                    }
                    break;

                case "update":
                    if (itemIdIn.equals("") || itemNameIn.equals("") || priceIn.equals("") || emailIn.equals("")) {
                        request.setAttribute("message", "All fields are required");
                        request.setAttribute("itemIdIn", itemIdIn);
                        request.setAttribute("itemNameIn", itemNameIn);
                        request.setAttribute("priceIn", priceIn);
                    } else if (Double.parseDouble(priceIn) < 0) {
                        request.setAttribute("message", "Price cannot be negative");
                    } else {
                        int puitemId = Integer.parseInt(itemIdIn);
                        int categoryId = Integer.parseInt(request.getParameter("category"));
                        Category category = categoryDB.get(categoryId);
                        double putprice = Double.parseDouble(priceIn);
                        is.update(puitemId, category, itemNameIn, putprice, emailIn);
                        request.setAttribute("message", "Item Updated");
                        session.setAttribute("selectedItem", null);
                    }
                    break;

                case "cancel":
                    session.setAttribute("selectedItem", null);
                    break;
            }
        } catch (NumberFormatException e) {

            request.setAttribute("message", "Price must be a valid number");
        } catch (Exception ex) {
            Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        List<Item> items = null;
        List<Category> categories = null;

        if (roleid == 1) {
            try {
                items = is.getAll();
                session.setAttribute("itemlist", items);
                categories = cs.getAll();
                session.setAttribute("categorylist", categories);

            } catch (NumberFormatException e) {
                request.setAttribute("message", "Price must be a valid number");
            } catch (SQLException ex) {

                Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            if (roleid == 2) {
                try {
                    items = is.getAll(useremail);
                    session.setAttribute("itemlist", items);
                    categories = cs.getAll();
                    session.setAttribute("categorylist", categories);

                } catch (SQLException ex) {

                    Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        getServletContext().getRequestDispatcher("/WEB-INF/inventory.jsp")
                .forward(request, response);

    }

}
