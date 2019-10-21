package kokhanevych.internetshop.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import kokhanevych.internetshop.lib.Inject;
import kokhanevych.internetshop.model.Bucket;
import kokhanevych.internetshop.model.User;
import kokhanevych.internetshop.service.BucketService;
import kokhanevych.internetshop.service.UserService;

public class AddItemToBucketController extends HttpServlet {
    @Inject
    private static UserService userService;

    @Inject
    private static BucketService bucketService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long userId = (Long) req.getSession(true).getAttribute("userId");
        User user = userService.get(userId);
        Bucket bucket = bucketService.get(user.getBucket().getId());
        String itemId = req.getParameter("item_id");
        bucketService.addItem(bucket.getId(), Long.valueOf(itemId));
        resp.sendRedirect(req.getContextPath() + "/servlet/getAllItems");
    }
}
