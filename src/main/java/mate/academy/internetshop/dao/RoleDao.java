package mate.academy.internetshop.dao;

import mate.academy.internetshop.model.Role;

public interface RoleDao {

    Role get(Role.RoleName roleName);
}
