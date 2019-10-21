package kokhanevych.internetshop.service;

import java.util.List;
import java.util.Optional;

import kokhanevych.internetshop.model.Order;
import kokhanevych.internetshop.model.User;
import kokhanevych.internetshop.exceptions.AuthenticationException;

public interface UserService {

    User create(User user);

    User get(Long id);

    User update(User user);

    void delete(Long id);

    List<Order> getOrders(Long userId);

    List<User> getAll();

    User login(String login, String password) throws AuthenticationException;

    Optional<User> getByToken(String token);
}
