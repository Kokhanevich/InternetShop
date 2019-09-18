package mate.academy.internetshop.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.model.Order;
import mate.academy.internetshop.service.UserService;

public class GetAllOrdersController extends HttpServlet {

    private static final Long USER_ID = 0L;

    @Inject
    private static UserService userService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Order> orders = userService.getOrders(USER_ID);
        req.setAttribute("orders", orders);
        req.getRequestDispatcher("/WEB-INF/views/orders.jsp").forward(req, resp);

    }
}
