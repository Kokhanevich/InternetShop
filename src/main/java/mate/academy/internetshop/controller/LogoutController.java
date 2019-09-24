package mate.academy.internetshop.controller;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

public class LogoutController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(true);
        session.setAttribute("userId", "");
        session.invalidate();

        for (Cookie cookie: req.getCookies()) {
            if (cookie.getName().equals("Mate")) {
                cookie.setMaxAge(0);
                cookie.setValue("");
                resp.addCookie(cookie);
            }
        }
        resp.sendRedirect(req.getContextPath() + "/index");
    }
}
