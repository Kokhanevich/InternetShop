package mate.academy.internetshop.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.model.Item;
import mate.academy.internetshop.service.BucketService;
import mate.academy.internetshop.service.OrderService;
import mate.academy.internetshop.service.UserService;

public class CompleteOrderController extends HttpServlet {
    private static final Long USER_ID = 0L;
    private static final Long BUCKET_ID = 0L;
    @Inject
    private static OrderService orderService;

    @Inject
    private static BucketService bucketService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Item> allItems = bucketService.getAllItems(BUCKET_ID);
        orderService.completeOrder(allItems, USER_ID);
        bucketService.clear(BUCKET_ID);
        resp.sendRedirect(req.getContextPath() + "/servlet/getAllItems");
    }
}
