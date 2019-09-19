package mate.academy.internetshop.model;

import java.util.ArrayList;
import java.util.List;

import mate.academy.internetshop.generators.BucketIdGenerator;

public class Bucket {
    private Long id;
    private List<Item> items;
    private User user;

    public Bucket() {
        this.id = BucketIdGenerator.getGeneratedId();
        this.items = new ArrayList<>();
    }

    public Bucket(User user) {
        this();
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
