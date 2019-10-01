package mate.academy.internetshop.dao;

import java.util.Set;
import mate.academy.internetshop.model.Role;
import mate.academy.internetshop.model.User;

public interface RoleDao {
    Role get(Role.RoleName roleName);

    void setRoles(User newUser, Set<Role> rolesFromDb);
}
