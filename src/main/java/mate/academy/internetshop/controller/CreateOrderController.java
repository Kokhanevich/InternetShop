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

public class CreateOrderController extends HttpServlet {

    @Inject
    private static OrderService orderService;

    @Inject
    private static BucketService bucketService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        //String bucketId = req.getParameter("bucket_id");
        List<Item> allItems = bucketService.getAllItems(0L);
        orderService.completeOrder(allItems, 0L);
        bucketService.clear(0L);
        resp.sendRedirect(req.getContextPath() + "/servlet/getAllItems");
    }
}
