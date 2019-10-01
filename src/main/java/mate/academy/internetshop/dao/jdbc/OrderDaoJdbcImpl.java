package mate.academy.internetshop.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import mate.academy.internetshop.dao.OrderDao;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.model.Item;
import mate.academy.internetshop.model.Order;
import mate.academy.internetshop.service.UserService;
import org.apache.log4j.Logger;

@Dao
public class OrderDaoJdbcImpl extends AbstractDao<Order> implements OrderDao {
    private static Logger logger = Logger.getLogger(OrderDaoJdbcImpl.class);

    @Inject
    private static UserService userService;

    public OrderDaoJdbcImpl(Connection connection) {
        super(connection);
    }

    @Override
    public Order create(Order order) {
        Long orderId = null;
        String query = "INSERT INTO orders (user_id) VALUES (?);";
        try (PreparedStatement statement =
                    connection.prepareStatement(query,
                            Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, order.getUser().getId());
            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    orderId = generatedKeys.getLong(1);
                } else {
                    throw new SQLException("Creating order failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            logger.error("Can’t create order with id= " + order.getId(), e);
        }
        String insertOrderItemQuery =
                "INSERT INTO orders_items (order_id, item_id) VALUES(?, ?);";
        try (PreparedStatement statement = connection.prepareStatement(insertOrderItemQuery)) {
            for (Item item: order.getItems()) {
                statement.setLong(1, orderId);
                statement.setLong(2, item.getId());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            logger.error("Can’t get item to order", e);
        }
        return new Order(orderId, order.getUser(), order.getItems());
    }

    @Override
    public Order get(Long id) {
        Order order = null;
        String getOrderQuery = "SELECT o.order_id, o.user_id, i.item_id, i.name,"
                + "i.price FROM orders o INNER JOIN orders_items oi ON o.order_id=oi.order_id "
                + "INNER JOIN items i ON oi.item_id=i.item_id WHERE o.order_id=?;";
        try (PreparedStatement statement = connection.prepareStatement(getOrderQuery)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            List<Item> items = new ArrayList<>();
            while (resultSet.next()) {
                long orderId = resultSet.getLong("order_id");
                long userId = resultSet.getLong("user_id");
                String name = resultSet.getString("name");
                double price = resultSet.getDouble("price");
                long itemId = resultSet.getLong("item_id");
                order = new Order();
                order.setId(orderId);
                order.setUser(userService.get(userId));
                Item item = new Item(itemId);
                item.setName(name);
                item.setPrice(price);
                items.add(item);
            }
            order.setItems(items);
        } catch (SQLException e) {
            logger.error("Can’t get order with id=" + id, e);
        }
        return order;
    }

    @Override
    public Order update(Order order) {
        String updateOrderQuery = "UPDATE orders SET user_id=? WHERE order_id=?";
        try (PreparedStatement statement = connection.prepareStatement(updateOrderQuery)) {
            statement.setLong(1, order.getUser().getId());
            statement.setLong(2, order.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Can’t update order", e);
        }
        return order;
    }

    @Override
    public void delete(Long id) {
        String deleteOrderQuery = "DELETE FROM orders WHERE order_id=?";
        try (PreparedStatement statement = connection.prepareStatement(deleteOrderQuery)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Can’t delete order", e);
        }
    }

    @Override
    public List<Order> getOrdersForUser(Long id) {
        List<Order> orders = new ArrayList<>();
        String getOrdersQuery = "SELECT * FROM orders WHERE user_id=?;";
        try (PreparedStatement statement = connection.prepareStatement(getOrdersQuery)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                long orderId = resultSet.getLong("order_id");
                orders.add(get(orderId));
            }
        } catch (SQLException e) {
            logger.error("Can’t get orders", e);
        }
        return orders;
    }
}
