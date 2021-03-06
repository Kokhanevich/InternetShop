package kokhanevych.internetshop.dao;

import java.util.List;
import kokhanevych.internetshop.model.Order;

public interface OrderDao {

    Order create(Order order);

    Order get(Long id);

    Order update(Order order);

    void delete(Long id);

    List<Order> getOrdersForUser(Long id);
}
