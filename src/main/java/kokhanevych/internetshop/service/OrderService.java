package kokhanevych.internetshop.service;

import java.util.List;

import kokhanevych.internetshop.model.Order;

public interface OrderService {

    Order create(Order order);

    Order get(Long id);

    Order update(Order order);

    void delete(Long id);

    Order completeOrder(List items, Long userId);
}
