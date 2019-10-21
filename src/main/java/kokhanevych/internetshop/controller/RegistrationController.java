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
import kokhanevych.internetshop.model.Role;
import kokhanevych.internetshop.service.RoleService;
import kokhanevych.internetshop.service.UserService;

public class RegistrationController extends HttpServlet {
    @Inject
    private static UserService userService;

    @Inject
    private static RoleService roleService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User newUser = new User();
        newUser.setLogin(req.getParameter("login"));
        newUser.setPassword(req.getParameter("psw"));
        newUser.setName(req.getParameter("user_name"));
        newUser.setSurname(req.getParameter("user_surname"));
        Role userRole = roleService.get(Role.RoleName.USER);
        newUser.addRole(userRole);
        User user = userService.create(newUser);

        HttpSession session = req.getSession(true);
        session.setAttribute("userId", user.getId());

        Cookie cookie = new Cookie("Mate", user.getToken());
        resp.addCookie(cookie);
        resp.sendRedirect(req.getContextPath() + "/index");
    }
}
