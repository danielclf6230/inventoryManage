package servlets;

import dataaccess.RoleDB;
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
import models.Role;
import models.User;
import services.UserService;

/**
 *
 * @author danielchow
 */
public class AdminServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action != null && action.equals("minven")) {
            response.sendRedirect("inventory");
        } else if (action != null && action.equals("mcate")) {
            response.sendRedirect("category");
        } else {

            UserService us = new UserService();
            HttpSession session = request.getSession();

            if ((action != null && action.equals("edit"))) {
                try {
                    String email = request.getParameter("userEmail");
                    User user = us.get(email);
                    session.setAttribute("selectedUser", user);

                } catch (Exception ex) {
                    Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (action != null && action.equals("delete")) {
                try {
                    String email = request.getParameter("userEmail");
                    us.delete(email);
                    System.out.println("Deleted");
                } catch (Exception ex) {
                    Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            List<User> users = null;

            try {
                users = us.getAll();
                session.setAttribute("userlist", users);

            } catch (SQLException ex) {

                Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
            }

            getServletContext().getRequestDispatcher("/WEB-INF/admin.jsp")
                    .forward(request, response);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        UserService us = new UserService();

        // action must be one of: create, update, delete
        String action = request.getParameter("action");
        String email = request.getParameter("email");
        String firstName = request.getParameter("fnIn");
        String lastName = request.getParameter("lnIn");
        String password = request.getParameter("pwIn");
        String status = request.getParameter("status");
        RoleDB roleDB = new RoleDB();
        int role_id = 0;
        HttpSession session = request.getSession();

        try {
            switch (action) {
                case "add":
                    if (email.equals("") || firstName.equals("") || lastName.equals("") || password.equals("")) {
                        request.setAttribute("message", "All fields are required");
                        request.setAttribute("email", email);
                        request.setAttribute("firstName", firstName);
                        request.setAttribute("lastName", lastName);
                        request.setAttribute("password", password);
                    } else {
                        Boolean activity = true;
                        role_id = Integer.parseInt(request.getParameter("role"));
                        Role role = roleDB.get(role_id);
                        us.insert(email, activity, firstName, lastName, password, role);

                    }
                    break;
                case "update":
                    if (email.equals("") || firstName.equals("") || lastName.equals("") || password.equals("")) {
                        request.setAttribute("message", "All fields are required");
                        request.setAttribute("email", email);
                        request.setAttribute("firstName", firstName);
                        request.setAttribute("lastName", lastName);
                        request.setAttribute("password", password);
                    } else {
                        Boolean activity = Boolean.parseBoolean(status);
                        role_id = Integer.parseInt(request.getParameter("role"));
                        Role role = roleDB.get(role_id);
                        us.update(email, activity, firstName, lastName, password, role);
                        session.setAttribute("selectedUser", null);
                        request.setAttribute("message", "User updated");
                    }
                    break;
                case "cancel":
                    session.setAttribute("selectedUser", null);
                    break;
            }
        } catch (Exception ex) {
            String ex1 = ex.getMessage();
            if (ex1.contains("No transaction is currently active")) {
                request.setAttribute("email", email);
                request.setAttribute("firstName", firstName);
                request.setAttribute("lastName", lastName);
                request.setAttribute("password", password);
                request.setAttribute("message", "Email existed");
            }
            Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        List<User> users = null;

        try {
            users = us.getAll();
            session.setAttribute("userlist", users);

        } catch (SQLException ex) {

            Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        getServletContext().getRequestDispatcher("/WEB-INF/admin.jsp")
                .forward(request, response);

    }

}
