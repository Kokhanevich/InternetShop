package kokhanevych.internetshop.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import kokhanevych.internetshop.dao.OrderDao;
import kokhanevych.internetshop.dao.UserDao;
import kokhanevych.internetshop.lib.Inject;
import kokhanevych.internetshop.lib.Service;
import kokhanevych.internetshop.model.Order;
import kokhanevych.internetshop.model.User;
import kokhanevych.internetshop.service.UserService;
import kokhanevych.internetshop.exceptions.AuthenticationException;

@Service
public class UserServiceImpl implements UserService {

    @Inject
    private static UserDao userDao;

    @Inject
    private static OrderDao orderDao;

    @Override
    public User create(User user) {
        user.setToken(getToken());
        return userDao.create(user);
    }

    private String getToken() {
        return UUID.randomUUID().toString();
    }

    @Override
    public User get(Long id) {
        return userDao.get(id);
    }

    @Override
    public User update(User user) {
        return userDao.update(user);
    }

    @Override
    public void delete(Long id) {
        userDao.delete(id);
    }

    @Override
    public List<Order> getOrders(Long userId) {
        User user = userDao.get(userId);
        return orderDao.getOrdersForUser(user.getId());
    }

    @Override
    public List<User> getAll() {
        return userDao.getAll();
    }

    @Override
    public User login(String login, String password) throws AuthenticationException {
        return userDao.login(login, password);
    }

    @Override
    public Optional<User> getByToken(String token) {
        return userDao.getByToken(token);
    }
}
