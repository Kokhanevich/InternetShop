package mate.academy.internetshop;

import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.lib.Injector;
import mate.academy.internetshop.model.Bucket;
import mate.academy.internetshop.model.Item;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.BucketService;
import mate.academy.internetshop.service.ItemService;
import mate.academy.internetshop.service.OrderService;
import mate.academy.internetshop.service.UserService;

public class Main {
    static {
        try {
            Injector.inject();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Inject
    private static UserService userService;

    @Inject
    private static BucketService bucketService;

    @Inject

    private static ItemService itemService;

    @Inject
    private static OrderService orderService;

    public static void main(String[] args) {
        Item car = new Item();
        car.setName("Toyota");
        car.setPrise(20000D);

        Item phone = new Item();
        phone.setName("Nokia");
        phone.setPrise(5000D);

        itemService.create(car);
        itemService.create(phone);

        User user = new User();
        userService.create(user);

        Bucket bucket = new Bucket();
        bucket.setUser(user);
        bucketService.create(bucket);
        bucketService.addItem(bucket.getId(), car.getId());

        orderService.completeOrder(bucket.getItems(), user.getId());

        System.out.println(userService.getOrders(user.getId()));
    }
}
