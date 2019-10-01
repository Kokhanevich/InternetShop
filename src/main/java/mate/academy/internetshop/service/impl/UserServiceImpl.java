package mate.academy.internetshop.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import mate.academy.internetshop.dao.OrderDao;
import mate.academy.internetshop.dao.RoleDao;
import mate.academy.internetshop.dao.UserDao;
import mate.academy.internetshop.exceptions.AuthenticationException;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.lib.Service;
import mate.academy.internetshop.model.Order;
import mate.academy.internetshop.model.Role;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Inject
    private static UserDao userDao;

    @Inject
    private static OrderDao orderDao;

    @Inject
    private static RoleDao roleDao;

    @Override
    public User create(User user) {
        Set<Role> rolesFromUser = user.getRoles();
        Set<Role> rolesFromDb = new HashSet<>();
        for (Role r: rolesFromUser) {
            Role dbRole = roleDao.get(r.getRoleName());
            rolesFromDb.add(dbRole);
        }
        user.setToken(getToken());
        User newUser = userDao.create(user);
        roleDao.setRoles(newUser, rolesFromDb);
        return newUser;
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
