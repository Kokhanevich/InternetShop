package kokhanevych.internetshop.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import kokhanevych.internetshop.dao.RoleDao;
import kokhanevych.internetshop.lib.Dao;
import kokhanevych.internetshop.model.Role;
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
}
