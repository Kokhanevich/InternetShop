package mate.academy.internetshop.dao;

import java.util.ArrayList;
import java.util.List;

import mate.academy.internetshop.model.Bucket;
import mate.academy.internetshop.model.Item;
import mate.academy.internetshop.model.Order;
import mate.academy.internetshop.model.User;

public class Storage {
    public static final List<Item> items = new ArrayList<>();
    public static final List<Bucket> buckets = new ArrayList<>();
    public static final List<User> users = new ArrayList<>();
    public static final List<Order> orders = new ArrayList<>();

    static {

        items.add(new Item("Xiaomi Note 5", 6000D));
        items.add(new Item("Nokia 5800", 2000D));
        items.add(new Item("Lenovo G570", 3500D));
        items.add(new Item("Fly A820", 3500D));
        items.add(new Item("Meizu D300", 4000D));
        items.add(new Item("iPhone X", 25000D));
        items.add(new Item("Samsung Note A7", 15000D));
    }
}
