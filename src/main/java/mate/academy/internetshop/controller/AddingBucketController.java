package mate.academy.internetshop.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.model.Bucket;
import mate.academy.internetshop.service.BucketService;

public class AddingBucketController extends HttpServlet {
    private Bucket bucket = new Bucket();

    @Inject
    private static BucketService bucketService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        bucketService.create(bucket);
        String item_id = req.getParameter("item_id");
        bucketService.addItem(bucket.getId(), Long.valueOf(item_id));
        resp.sendRedirect(req.getContextPath() + "/servlet/getAllItems");
    }
}
