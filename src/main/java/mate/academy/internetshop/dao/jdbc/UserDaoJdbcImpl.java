package mate.academy.internetshop.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.internetshop.dao.UserDao;
import mate.academy.internetshop.exceptions.AuthenticationException;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.model.Bucket;
import mate.academy.internetshop.model.Role;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.BucketService;
import org.apache.log4j.Logger;

@Dao
public class UserDaoJdbcImpl extends AbstractDao<User> implements UserDao {
    private static Logger logger = Logger.getLogger(UserDaoJdbcImpl.class);

    @Inject
    private static BucketService bucketService;

    public UserDaoJdbcImpl(Connection connection) {
        super(connection);
    }

    @Override
    public User create(User user) {
        String createUserQuery =
                "INSERT INTO users (name, surname, login, password, token) VALUES (?, ?, ?, ?, ?);";
        try (PreparedStatement statement =
                    connection.prepareStatement(createUserQuery,
                            PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getSurname());
            statement.setString(3, user.getLogin());
            statement.setString(4, user.getPassword());
            statement.setString(5, user.getToken());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            Long userId = resultSet.getLong(1);
            user.setId(userId);
            Bucket bucket = new Bucket(user);
            bucketService.create(bucket);
        } catch (SQLException e) {
            logger.warn("Can’t create user= " + user.getLogin());
        }
        return user;
    }

    @Override
    public User get(Long id) {
        String getUserQuery =
                "SELECT * FROM users u INNER JOIN buckets b ON u.user_id = b.user_id "
                        + "WHERE u.user_id=?;";
        try (PreparedStatement statement =
                    connection.prepareStatement(getUserQuery)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                long userId = resultSet.getLong("user_id");
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");
                String login = resultSet.getString("login");
                String password = resultSet.getString("password");
                String token = resultSet.getString("token");
                Long bucketId = resultSet.getLong("bucket_id");
                User user = new User(userId);
                user.setName(name);
                user.setSurname(surname);
                user.setLogin(login);
                user.setPassword(password);
                user.setToken(token);
                Bucket bucket = new Bucket();
                bucket.setId(bucketId);
                user.setBucket(bucket);
                return user;
            }
        } catch (SQLException e) {
            logger.warn("Can’t get user with id=" + id);
        }
        return null;
    }

    @Override
    public User update(User user) {
        String updateUserQuery =
                "UPDATE users SET user_id=?, name=?, surname=?, login=?, password=? "
                        + "WHERE user_id=?;";
        try (PreparedStatement statement =
                   connection.prepareStatement(updateUserQuery)) {
            statement.setLong(1, user.getId());
            statement.setString(2, user.getName());
            statement.setString(3, user.getSurname());
            statement.setString(4, user.getLogin());
            statement.setString(5, user.getPassword());
            statement.setLong(6, user.getId());
            statement.executeUpdate();
            return user;
        } catch (SQLException e) {
            logger.warn("Can’t update user with id=" + user.getId());
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        String deleteUserQuery = "DELETE FROM users WHERE user_id=?;";
        try (PreparedStatement statement = connection.prepareStatement(deleteUserQuery)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.warn("Can’t delete user with id=" + id);
        }
    }

    @Override
    public User login(String login, String password) throws AuthenticationException {
        String selectUserQuery = "SELECT * FROM users where login=? AND password=?;";
        try (PreparedStatement statement = connection.prepareStatement(selectUserQuery)) {
            statement.setString(1, login);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Long id = resultSet.getLong("user_id");
                String name = resultSet.getString("name");
                String passwordDb = resultSet.getString("password");
                String token = resultSet.getString("token");
                if (!passwordDb.equals(password)) {
                    throw new AuthenticationException("Incorrect login or password!");
                }
                User user = new User(id);
                user.setName(name);
                user.setLogin(login);
                user.setPassword(password);
                user.setToken(token);
                return user;
            }
        } catch (SQLException e) {
            logger.warn("Can’t get user with login=" + login);
        }
        throw new AuthenticationException("Can’t get user with login = " + login);
    }

    @Override
    public Optional<User> getByToken(String token) {
        String getTokenQuery = "SELECT * FROM users WHERE token=?;";
        try (PreparedStatement statement = connection.prepareStatement(getTokenQuery)) {
            statement.setString(1, token);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Long id = resultSet.getLong("user_id");
                String login = resultSet.getString("login");
                String password = resultSet.getString("password");
                String name = resultSet.getString("name");
                User user = new User(id);
                user.setName(name);
                user.setLogin(login);
                user.setPassword(password);
                user.setToken(token);
                user.addRole(Role.of("USER"));
                return Optional.of(user);
            }
        } catch (SQLException e) {
            logger.warn("Can’t get user with token=" + token);
        }
        return Optional.empty();
    }

    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        String getAllUsersQuery = "SELECT * FROM users;";
        try (PreparedStatement statement = connection.prepareStatement(getAllUsersQuery)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                long userId = resultSet.getLong("user_id");
                String login = resultSet.getString("login");
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");
                User user = new User(userId);
                user.setLogin(login);
                user.setName(name);
                user.setSurname(surname);
                users.add(user);
            }
        } catch (SQLException e) {
            logger.warn("Can’t create item list", e);
        }
        return users;
    }
}
