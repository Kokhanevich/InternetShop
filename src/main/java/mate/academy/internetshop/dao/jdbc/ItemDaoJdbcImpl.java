package mate.academy.internetshop.dao.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import mate.academy.internetshop.dao.ItemDao;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.Item;
import org.apache.log4j.Logger;

@Dao
public class ItemDaoJdbcImpl extends AbstractDao<Item> implements ItemDao {
    private static Logger logger = Logger.getLogger(ItemDaoJdbcImpl.class);
    private static final String DB_NAME = "internetshop";

    public ItemDaoJdbcImpl(Connection connection) {
        super(connection);
    }

    @Override
    public Item create(Item item) {
        Statement statement = null;
        String query = String.format("INSERT INTO %s.items (%s, %s) VALUES ('%s', %2f);",
                DB_NAME, "name", "price", item.getName(), item.getPrise());
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            return item;
        } catch (SQLException e) {
            logger.warn("Can’t create item=" + item.getName());
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    logger.warn("Can’t close statement", e);
                }
            }
        }
        return null;
    }

    @Override
    public Item get(Long id) {
        Statement statement = null;
        String query = String.format("SELECT * FROM %s.items where item_id=%d;", DB_NAME, id);

        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                long itemId = resultSet.getLong("item_id");
                String name = resultSet.getString("name");
                double prise = resultSet.getDouble("prise");
                Item item = new Item(itemId);
                item.setName(name);
                item.setPrise(prise);
                return item;
            }
        } catch (SQLException e) {
            logger.warn("Can’t get item by id=" + id);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    logger.warn("Can’t close statement", e);
                }
            }
        }
        return null;
    }

    @Override
    public Item update (Item item) {
        Statement statement = null;
        String query =
                String.format("UPDATE %s.items SET item_id=%d, name='$s', price=%2f WHERE item_id=%d;",
                DB_NAME, item.getId(), item.getName(), item.getPrise(), item.getId());
        try {
            statement = connection.createStatement();
            statement.executeUpdate(query);
            return item;
        } catch (SQLException e) {
            logger.warn("Can’t update item with id=" + item.getId());
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    logger.warn("Can’t close statement", e);
                }
            }
        }
        return null;
    }

    @Override
    public void delete (Long id) {
        Statement statement = null;
        String query = String.format("DELETE FROM %s.items WHERE item_id=%d;", DB_NAME, id);
        try {
            statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            logger.warn("Can’t delete item with id=" + id);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    logger.warn("Can’t close statement", e);
                }
            }
        }
    }
}

