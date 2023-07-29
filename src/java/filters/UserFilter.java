package filters;

import dataaccess.UserDB;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.User;

/**
 *
 * @author danielchow
 */
public class UserFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession();
        String email = (String) session.getAttribute("useremail");
        UserDB userDB = new UserDB();
        try {
            User user = userDB.get(email);
            int userId = user.getRole().getRoleId();
            if (userId != 1) {
                HttpServletResponse res = (HttpServletResponse) response;
                res.sendRedirect("inventory");
                return;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserFilter.class.getName()).log(Level.SEVERE, null, ex);
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }

}
