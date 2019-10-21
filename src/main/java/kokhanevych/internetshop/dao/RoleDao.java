package kokhanevych.internetshop.dao;

import kokhanevych.internetshop.model.Role;

public interface RoleDao {

    Role get(Role.RoleName roleName);
}
