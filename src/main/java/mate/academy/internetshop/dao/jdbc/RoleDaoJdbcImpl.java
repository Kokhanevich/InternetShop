package mate.academy.internetshop.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;
import mate.academy.internetshop.dao.RoleDao;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.Role;
import mate.academy.internetshop.model.User;
import org.apache.log4j.Logger;

@Dao
public class RoleDaoJdbcImpl extends AbstractDao<Role> implements RoleDao {
    private static Logger logger = Logger.getLogger(RoleDaoJdbcImpl.class);

    public RoleDaoJdbcImpl(Connection connection) {
        super(connection);
    }

    @Override
    public Role get(Role.RoleName roleName) {
        String getRoleQuery = "SELECT * FROM roles WHERE name=?";
        try (PreparedStatement statement =
                    connection.prepareStatement(getRoleQuery)) {
            statement.setString(1, String.valueOf(roleName));
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {

                Long roleId = resultSet.getLong("role_id");
                String roleNameDb = resultSet.getString("name");
                Role role = Role.of(roleNameDb);
                role.setId(roleId);
                return role;
            }
        } catch (SQLException e) {
            logger.error("Can’t get role with name= " + roleName, e);
        }
        return null;
    }

    @Override
    public void setRoles(User newUser, Set<Role> rolesFromDb) {
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
