package servlets;

import dataaccess.RoleDB;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.Role;
import services.UserService;

/**
 *
 * @author danielchow
 */
public class SignupServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        getServletContext().getRequestDispatcher("/WEB-INF/signup.jsp")
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

        try {
            switch (action) {
                case "signup":
                    if (email.equals("") || firstName.equals("") || lastName.equals("") || password.equals("")) {
                        request.setAttribute("message", "All fields are required");
                        request.setAttribute("email", email);
                        request.setAttribute("firstName", firstName);
                        request.setAttribute("lastName", lastName);
                        request.setAttribute("password", password);
                    } else {
                        Boolean activity = true;
                        int role_id = 2;
                        Role role = roleDB.get(role_id);
                        us.insert(email, activity, firstName, lastName, password, role);
                        request.setAttribute("message", "Account Created");
                    }
                    break;
                case "cancel":
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
                getServletContext().getRequestDispatcher("/WEB-INF/signup.jsp")
                        .forward(request, response);
            }
            Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        getServletContext().getRequestDispatcher("/WEB-INF/login.jsp")
                .forward(request, response);

    }

}
