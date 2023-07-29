package servlets;

import dataaccess.RoleDB;
import dataaccess.UserDB;
import java.io.IOException;
import java.sql.SQLException;
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
public class UserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("useremail");
        UserDB userDB = new UserDB();
        try {
            User user = userDB.get(email);
            session.setAttribute("loginuser", user);

        } catch (SQLException ex) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        getServletContext().getRequestDispatcher("/WEB-INF/user.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        UserService us = new UserService();

        String action = request.getParameter("action");
        String email = request.getParameter("email");
        String firstName = request.getParameter("fnIn");
        String lastName = request.getParameter("lnIn");
        String password = request.getParameter("pwIn");
        RoleDB roleDB = new RoleDB();
        HttpSession session = request.getSession();
        Boolean active = (Boolean) session.getAttribute("useract");

        try {
            switch (action) {
                case "update":
                    if (email.equals("") || firstName.equals("") || lastName.equals("") || password.equals("")) {
                        request.setAttribute("message", "All fields are required");
                        getServletContext().getRequestDispatcher("/WEB-INF/user.jsp")
                                .forward(request, response);
                    } else {
                        int role_id = (int) session.getAttribute("roleid");
                        Role role = roleDB.get(role_id);
                        us.update(email, active, firstName, lastName, password, role);
                        request.setAttribute("message", "Account Updated");
                    }
                    break;
                case "cancel":
                    break;
                case "deactivate":

                    if (active == true) {
                        int role_id = (int) session.getAttribute("roleid");
                        User user = (User) session.getAttribute("loginuser");
                        email = user.getEmail();
                        firstName = user.getFirstName();
                        lastName = user.getLastName();
                        password = user.getPassword();
                        Role role = roleDB.get(role_id);
                        active = false;
                        us.update(email, active, firstName, lastName, password, role);
                        response.sendRedirect("login");
                    }
            }
        } catch (Exception ex) {
            Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (active == true) {
            getServletContext().getRequestDispatcher("/WEB-INF/inventory.jsp")
                    .forward(request, response);
        }

    }

}
