package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.User;
import services.AccountService;

public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        session.invalidate(); // just by going to the login page the user is logged out :-) 

        getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        AccountService as = new AccountService();
        User user = as.login(email, password);
        if (user == null) {
            request.setAttribute("message", "Invalid user");
            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
            return;
        }
        Boolean active = user.getActive();
        if (active != true) {
            request.setAttribute("message", "Inactivate user");
            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
            return;
        }
        session.setAttribute("useract", active);

        session.setAttribute("useremail", email);
        int rid = user.getRole().getRoleId();
        session.setAttribute("roleid", rid);
        session.setAttribute("loginuser", user);

        if (rid == 1) {
            response.sendRedirect("admin");
        } else {
            response.sendRedirect("inventory");
        }
    }

}
