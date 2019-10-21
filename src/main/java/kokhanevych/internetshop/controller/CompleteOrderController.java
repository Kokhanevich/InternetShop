package kokhanevych.internetshop.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kokhanevych.internetshop.lib.Inject;
import kokhanevych.internetshop.model.Item;
import kokhanevych.internetshop.model.User;
import kokhanevych.internetshop.service.BucketService;
import kokhanevych.internetshop.service.OrderService;
import kokhanevych.internetshop.service.UserService;

public class CompleteOrderController extends HttpServlet {
    @Inject
    private static UserService userService;

    @Inject
    private static OrderService orderService;

    @Inject
    private static BucketService bucketService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long userId = (Long) req.getSession(true).getAttribute("userId");
        User user = userService.get(userId);
        List<Item> allItems = bucketService.getAllItems(user.getBucket().getId());
        orderService.completeOrder(allItems, userId);
        bucketService.clear(user.getBucket().getId());
        resp.sendRedirect(req.getContextPath() + "/servlet/getAllItems");
    }
}
