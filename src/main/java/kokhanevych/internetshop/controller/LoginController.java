package kokhanevych.internetshop.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kokhanevych.internetshop.lib.Inject;
import kokhanevych.internetshop.model.User;
import kokhanevych.internetshop.service.UserService;
import kokhanevych.internetshop.exceptions.AuthenticationException;

public class LoginController extends HttpServlet {
    @Inject
    private static UserService userService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String login = req.getParameter("login");
        String psw = req.getParameter("psw");
        try {
            User user = userService.login(login, psw);

            HttpSession session = req.getSession(true);
            session.setAttribute("userId", user.getId());

            Cookie cookie = new Cookie("Mate", user.getToken());
            resp.addCookie(cookie);
            resp.sendRedirect(req.getContextPath() + "/servlet/getAllItems");
        } catch (AuthenticationException e) {
            req.setAttribute("errorMsg", "Incorrect login or password");
            req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
        }
    }
}
