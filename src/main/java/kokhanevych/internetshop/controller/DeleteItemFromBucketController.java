package kokhanevych.internetshop.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kokhanevych.internetshop.lib.Inject;
import kokhanevych.internetshop.model.Item;
import kokhanevych.internetshop.model.User;
import kokhanevych.internetshop.service.BucketService;
import kokhanevych.internetshop.service.ItemService;
import kokhanevych.internetshop.service.UserService;

public class DeleteItemFromBucketController extends HttpServlet {
    @Inject
    private static UserService userService;

    @Inject
    private static BucketService bucketService;

    @Inject
    private static ItemService itemService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long userId = (Long) req.getSession(true).getAttribute("userId");
        User user = userService.get(userId);
        String itemId = req.getParameter("item_id");
        Item item = itemService.get(Long.parseLong(itemId));
        bucketService.deleteItem(item, user.getBucket().getId());
        resp.sendRedirect(req.getContextPath() + "/servlet/getBucket");
    }
}
