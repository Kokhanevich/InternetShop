package mate.academy.internetshop.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import mate.academy.internetshop.model.Bucket;
import mate.academy.internetshop.model.Item;
import mate.academy.internetshop.model.Order;
import mate.academy.internetshop.model.Role;
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

    static {
        User user1 = new User("Mykola");
        user1.setLogin("Nick95");
        user1.setPassword("111");
        user1.setSurname("Gava");
        user1.setToken(UUID.randomUUID().toString());
        user1.addRole(Role.of("USER"));
        users.add(user1);

        User user2 = new User("Bob");
        user2.setLogin("Bob95");
        user2.setPassword("222");
        user2.setSurname("Gavan");
        user2.addRole(Role.of("ADMIN"));
        user2.setToken(UUID.randomUUID().toString());
        users.add(user2);
    }
}
