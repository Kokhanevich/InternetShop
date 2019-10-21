package kokhanevych.internetshop.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kokhanevych.internetshop.lib.Inject;
import kokhanevych.internetshop.model.User;
import kokhanevych.internetshop.service.UserService;
import kokhanevych.internetshop.model.Role;

public class InjectDataController extends HttpServlet {
    @Inject
    private static UserService userService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User user = new User("Bob");
        user.setSurname("Robson");
        user.setLogin("Bob2");
        user.setPassword("user");
        user.addRole(Role.of("USER"));
        userService.create(user);

        User admin = new User("Super");
        admin.setSurname("Mario");
        admin.setLogin("Super1");
        admin.setPassword("admin");
        admin.addRole(Role.of("ADMIN"));
        userService.create(admin);

        resp.sendRedirect(req.getContextPath() + "/login");
    }
}
