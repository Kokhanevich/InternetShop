package kokhanevych.internetshop.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import kokhanevych.internetshop.dao.BucketDao;
import kokhanevych.internetshop.dao.RoleDao;
import kokhanevych.internetshop.dao.UserDao;
import kokhanevych.internetshop.lib.Dao;
import kokhanevych.internetshop.lib.Inject;
import kokhanevych.internetshop.model.Bucket;
import kokhanevych.internetshop.model.Role;
import kokhanevych.internetshop.model.User;
import kokhanevych.internetshop.exceptions.AuthenticationException;
import kokhanevych.internetshop.util.HashUtil;
import org.apache.log4j.Logger;

@Dao
public class UserDaoJdbcImpl extends AbstractDao<User> implements UserDao {
    private static Logger logger = Logger.getLogger(UserDaoJdbcImpl.class);

    @Inject
    private static BucketDao bucketDao;

    @Inject
    private static RoleDao roleDao;

    public UserDaoJdbcImpl(Connection connection) {
        super(connection);
    }

    @Override
    public User create(User user) {
        String createUserQuery =
                "INSERT INTO users (name, surname, login, password, token, salt) "
                        + "VALUES (?, ?, ?, ?, ?, ?);";
        try (PreparedStatement statement =
                    connection.prepareStatement(createUserQuery,
                            PreparedStatement.RETURN_GENERATED_KEYS)) {
            byte[] salt = HashUtil.getSalt();
            String hashPassword = HashUtil.hashPassword(user.getPassword(), salt);
            statement.setString(1, user.getName());
            statement.setString(2, user.getSurname());
            statement.setString(3, user.getLogin());
            statement.setString(4, hashPassword);
            statement.setString(5, user.getToken());
            statement.setBytes(6, salt);
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            Long userId = resultSet.getLong(1);
            user.setId(userId);
            Bucket bucket = new Bucket(user);
            bucketDao.create(bucket);
            Set<Role> rolesFromUser = user.getRoles();
            Set<Role> rolesFromDb = new HashSet<>();
            for (Role r: rolesFromUser) {
                Role dbRole = roleDao.get(r.getRoleName());
                rolesFromDb.add(dbRole);
            }
            setRoles(user, rolesFromDb);
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
                User user = new User(resultSet.getLong("user_id"));
                user.setName(resultSet.getString("name"));
                user.setSurname(resultSet.getString("surname"));
                user.setLogin(resultSet.getString("login"));
                user.setPassword(resultSet.getString("password"));
                user.setToken(resultSet.getString("token"));
                Bucket bucket = new Bucket();
                bucket.setId(resultSet.getLong("bucket_id"));
                user.setBucket(bucket);
                return user;
            }
        } catch (SQLException e) {
            logger.error("Can’t get user with id=" + id);
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
            logger.error("Can’t update user with id=" + user.getId());
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
        String selectUserQuery = "SELECT * FROM users where login=?;";
        try (PreparedStatement statement = connection.prepareStatement(selectUserQuery)) {
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                byte [] salt = resultSet.getBytes("salt");
                if (!login.equals(resultSet.getString("login"))
                        || !HashUtil.hashPassword(password, salt)
                        .equals(resultSet.getString("password"))) {
                    throw new AuthenticationException("Incorrect login or password!");
                }
                User user = new User(resultSet.getLong("user_id"));
                user.setName(resultSet.getString("name"));
                user.setLogin(login);
                user.setPassword(password);
                user.setToken(resultSet.getString("token"));
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
                User user = new User(resultSet.getLong("user_id"));
                user.setName(resultSet.getString("name"));
                user.setLogin(resultSet.getString("login"));
                user.setPassword(resultSet.getString("password"));
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
            users = getUsersFromResultSet(resultSet);
        } catch (SQLException e) {
            logger.warn("Can’t create item list", e);
        }
        return users;
    }

    private List<User> getUsersFromResultSet(ResultSet resultSet) {
        List<User> users = new ArrayList<>();
        try {
            while (resultSet.next()) {
                User user = new User(resultSet.getLong("user_id"));
                user.setLogin(resultSet.getString("login"));
                user.setName(resultSet.getString("name"));
                user.setSurname(resultSet.getString("surname"));
                users.add(user);
            }
        } catch (SQLException e) {
            logger.error("Can't get users", e);
        }
        return users;
    }

    private void setRoles(User newUser, Set<Role> rolesFromDb) {
        for (Role r: rolesFromDb) {
            String setRole = "INSERT INTO users_roles (user_id, role_id) VALUES (?, ?) ";
            try (PreparedStatement statement = connection.prepareStatement(setRole)) {
                statement.setLong(1, newUser.getId());
                statement.setLong(2, r.getId());
                statement.executeUpdate();
            } catch (SQLException e) {
                logger.error("Can’t set role with name= " + r.getRoleName().toString(), e);
            }
        }
    }
}
