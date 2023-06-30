/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import dataaccess.RoleDB;
import java.io.IOException;
import java.io.PrintWriter;
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
public class UserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        UserService us = new UserService();

        String action = request.getParameter("action");
        if (action != null && action.equals("edit")) {
            try {
                String email = request.getParameter("userEmail");
                User user = us.get(email);
                request.setAttribute("selectedUser", user);
            } catch (Exception ex) {
                Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (action != null && action.equals("delete")) {
            try {
                String email = request.getParameter("userEmail");
                us.delete(email);
            } catch (Exception ex) {
                Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        HttpSession session = request.getSession();
        List<User> users = null;

        try {
            users = us.getAll();
            session.setAttribute("userlist", users);

        } catch (SQLException ex) {

            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        getServletContext().getRequestDispatcher("/WEB-INF/users.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        UserService us = new UserService();

        // action must be one of: create, update, delete
        String action = request.getParameter("action");

        String email = request.getParameter("emailIn");
        String firstName = request.getParameter("fnIn");
        String lastname = request.getParameter("lnIn");
        String password = request.getParameter("pwIn");
        Boolean activity = true;
        RoleDB roleDB = new RoleDB();
        int role_id = 0;
 

        try {
            switch (action) {
                case "add":
                    role_id = Integer.parseInt(request.getParameter("role"));
                    Role role = roleDB.get(role_id);
                    us.insert(email, activity, firstName, lastname, password, role);
                    break;
                case "update":
                    role_id = Integer.parseInt(request.getParameter("role"));
                    role = roleDB.get(role_id);
                    us.update(email, activity, firstName, lastname, password, role);
                    break;
                case "cancel":
                    break;
            }
        } catch (Exception ex) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("message", "error");
        }

        HttpSession session = request.getSession();
        List<User> users = null;
 
        try {
            users = us.getAll();
            session.setAttribute("userlist", users);

        } catch (SQLException ex) {

            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        getServletContext().getRequestDispatcher("/WEB-INF/users.jsp")
                .forward(request, response);

    }

}
