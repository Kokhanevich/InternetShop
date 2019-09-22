package mate.academy.internetshop.model;

import java.util.ArrayList;
import java.util.List;

import mate.academy.internetshop.generators.UserIdGenerator;

public class User {
    private Long id;
    private String name;
    private List<Order> orders;
    private Bucket bucket;
    private String surname;
    private String login;
    private String password;
    private String token;

    public User() {
        this.id = UserIdGenerator.getGeneratedId();
        this.orders = new ArrayList<>();
        this.bucket = new Bucket(this);
    }

    public User(String name) {
        this();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public Bucket getBucket() {
        return bucket;
    }

    public void setBucket(Bucket bucket) {
        this.bucket = bucket;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "User{"
                + "id=" + id
                + ", name='" + name
                + '\'' + '}';
    }
}
