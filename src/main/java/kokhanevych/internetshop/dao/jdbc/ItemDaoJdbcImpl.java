package kokhanevych.internetshop.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kokhanevych.internetshop.dao.ItemDao;
import kokhanevych.internetshop.lib.Dao;
import kokhanevych.internetshop.model.Item;
import org.apache.log4j.Logger;

@Dao
public class ItemDaoJdbcImpl extends AbstractDao<Item> implements ItemDao {
    private static Logger logger = Logger.getLogger(ItemDaoJdbcImpl.class);

    public ItemDaoJdbcImpl(Connection connection) {
        super(connection);
    }

    @Override
    public Item create(Item item) {
        String query = "INSERT INTO items (name, price) VALUES (?, ?);";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, item.getName());
            statement.setDouble(2, item.getPrice());
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.warn("Can’t create item=" + item.getName());
        }
        return item;
    }

    @Override
    public Item get(Long id) {
        String query = "SELECT * FROM items where item_id=?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                long itemId = resultSet.getLong("item_id");
                String name = resultSet.getString("name");
                double price = resultSet.getDouble("price");
                Item item = new Item(itemId);
                item.setName(name);
                item.setPrice(price);
                return item;
            }
        } catch (SQLException e) {
            logger.warn("Can’t get item by id=" + id);
        }
        return null;
    }

    @Override
    public Item update(Item item) {
        String query = "UPDATE items SET item_id=?, name=?, price=? WHERE item_id=?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, item.getId());
            statement.setString(2, item.getName());
            statement.setDouble(3, item.getPrice());
            statement.setLong(4, item.getId());
            statement.executeUpdate();
            return item;
        } catch (SQLException e) {
            logger.warn("Can’t update item with id=" + item.getId());
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        String query = "DELETE FROM items WHERE item_id=?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.warn("Can’t delete item with id=" + id);
        }
    }

    @Override
    public List<Item> getAll() {
        List<Item> items = new ArrayList<>();
        String query = "SELECT * FROM items;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            items = getItemsFromResultSet(resultSet);
        } catch (SQLException e) {
            logger.warn("Can’t create item list", e);
        }
        return items;
    }

    private List<Item> getItemsFromResultSet(ResultSet resultSet) {
        List<Item> items = new ArrayList<>();
        try {
            while (resultSet.next()) {
                long itemId = resultSet.getLong("item_id");
                String name = resultSet.getString("name");
                double price = resultSet.getDouble("price");
                Item item = new Item(itemId);
                item.setName(name);
                item.setPrice(price);
                items.add(item);
            }
        } catch (SQLException e) {
            logger.error("Can't get items", e);
        }
        return items;
    }
}
