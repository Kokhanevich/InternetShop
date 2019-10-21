package kokhanevych.internetshop.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kokhanevych.internetshop.dao.BucketDao;
import kokhanevych.internetshop.dao.UserDao;
import kokhanevych.internetshop.lib.Dao;
import kokhanevych.internetshop.lib.Inject;
import kokhanevych.internetshop.model.Bucket;
import kokhanevych.internetshop.model.Item;
import kokhanevych.internetshop.model.User;
import org.apache.log4j.Logger;

@Dao
public class BucketDaoJdbcImpl extends AbstractDao<Bucket> implements BucketDao {
    private static Logger logger = Logger.getLogger(BucketDaoJdbcImpl.class);

    @Inject
    private static UserDao userDao;

    public BucketDaoJdbcImpl(Connection connection) {
        super(connection);
    }

    @Override
    public Bucket create(Bucket bucket) {
        String addBucketQuery = "INSERT INTO buckets (user_id) VALUES (?);";
        try (PreparedStatement statement =
                    connection.prepareStatement(addBucketQuery,
                            PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, bucket.getUser().getId());
            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    bucket.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Creating bucket failed, no ID obtained.");
                }
            } catch (SQLException e) {
                logger.warn("Can’t create bucket with id=" + bucket.getId());
            }
        } catch (SQLException e) {
            logger.warn("Can’t create bucket with id=" + bucket.getId());
        }
        return bucket;
    }

    @Override
    public Bucket get(Long bucketId) {
        List<Item> items = new ArrayList<>();
        String getBucketQuery = "SELECT * FROM buckets WHERE bucket_id=?;";
        try (PreparedStatement statement =
                    connection.prepareStatement(getBucketQuery,
                            PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, bucketId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Long userId = resultSet.getLong("user_id");
                Bucket bucket = new Bucket();
                bucket.setId(bucketId);
                User user = userDao.get(userId);
                bucket.setUser(user);
                return bucket;
            }
        } catch (SQLException e) {
            logger.error("Can’t get bucket with id=" + bucketId);
        }
        return null;
    }

    @Override
    public Bucket update(Bucket bucket) {
        String updateBucketQuery = "UPDATE buckets SET user_id=? WHERE bucket_id=?;";
        try (PreparedStatement statement = connection.prepareStatement(updateBucketQuery)) {
            statement.setLong(1, bucket.getUser().getId());
            statement.setLong(2, bucket.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Can’t update bucket with id=" + bucket.getId());
        }
        return bucket;
    }

    @Override
    public void delete(Long bucketId) {
        String deleteBucket = "DELETE FROM buckets WHERE bucket_id=?;";
        try (PreparedStatement statement = connection.prepareStatement(deleteBucket)) {
            statement.setLong(1, bucketId);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Can't delete bucket", e);
        }
    }

    @Override
    public Bucket addItem(Long bucketId, Long itemId) {
        String addItemQuery = "INSERT INTO buckets_items (bucket_id, item_id) VALUES (?, ?);";
        try (PreparedStatement statement = connection.prepareStatement(addItemQuery)) {
            statement.setLong(1, bucketId);
            statement.setLong(2, itemId);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Can't add item to bucket", e);
        }
        return get(bucketId);
    }

    @Override
    public List<Item> getAllItems(Long bucketId) {
        List<Item> items = new ArrayList<>();
        String getAllQuery =
                "SELECT * FROM items i INNER JOIN buckets_items bi "
                        + "ON i.item_id = bi.item_id WHERE bucket_id=?";
        try (PreparedStatement statement = connection.prepareStatement(getAllQuery)) {
            statement.setLong(1, bucketId);
            ResultSet resultSet = statement.executeQuery();
            items = getItemsFromResultSet(resultSet);
        } catch (SQLException e) {
            logger.error("Can't get all items from bucket with id= " + bucketId, e);
        }
        return items;
    }

    @Override
    public void deleteItem(Item item, Long bucketId) {
        String deleteItemQuery = "DELETE FROM buckets_items WHERE item_id=? AND bucket_id=?";
        try (PreparedStatement statement = connection.prepareStatement(deleteItemQuery)) {
            statement.setLong(1, item.getId());
            statement.setLong(2, bucketId);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Can't delete item from bucket", e);
        }
    }

    @Override
    public void clear(Long bucketId) {
        String clearBucketQuery = "DELETE FROM buckets_items WHERE bucket_id=?";
        try (PreparedStatement statement = connection.prepareStatement(clearBucketQuery)) {
            statement.setLong(1, bucketId);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Can't clear bucket with id " + bucketId, e);
        }
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
