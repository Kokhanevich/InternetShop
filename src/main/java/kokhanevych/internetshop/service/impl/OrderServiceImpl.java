package kokhanevych.internetshop.service.impl;

import java.util.List;

import kokhanevych.internetshop.dao.OrderDao;
import kokhanevych.internetshop.dao.UserDao;
import kokhanevych.internetshop.lib.Inject;
import kokhanevych.internetshop.lib.Service;
import kokhanevych.internetshop.model.Order;
import kokhanevych.internetshop.model.User;
import kokhanevych.internetshop.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

    @Inject
    private static OrderDao orderDao;
    @Inject
    private static UserDao userDao;

    @Override
    public Order create(Order order) {
        return orderDao.create(order);
    }

    @Override
    public Order get(Long id) {
        return orderDao.get(id);
    }

    @Override
    public Order update(Order order) {
        return orderDao.update(order);
    }

    @Override
    public void delete(Long id) {
        orderDao.delete(id);

    }

    @Override
    public Order completeOrder(List items, Long userId) {
        Order order = new Order();
        User user = userDao.get(userId);
        order.setUser(user);
        order.setItems(items);
        orderDao.create(order);
        return order;
    }
}
