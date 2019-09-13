package mate.academy.internetshop.model;

import java.util.ArrayList;
import java.util.List;

import mate.academy.internetshop.generators.OrderIdGenerator;

public class Order {
    private Long id;
    private User user;
    private List<Item> items;

    public Order() {
        this.id = OrderIdGenerator.getGeneratedId();
        this.items = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Order{"
                + "id=" + id
                + ", user=" + user
                + ", items=" + items + '}';
    }
}
