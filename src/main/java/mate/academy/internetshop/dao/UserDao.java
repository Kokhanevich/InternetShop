package mate.academy.internetshop.dao;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import mate.academy.internetshop.exceptions.AuthenticationException;
import mate.academy.internetshop.model.Role;
import mate.academy.internetshop.model.User;

public interface UserDao {

    User create(User user);

    User get(Long id);

    User update(User user);

    void delete(Long id);

    User login(String login, String password) throws AuthenticationException;

    Optional<User> getByToken(String token);

    List<User> getAll();

    void setRoles(User newUser, Set<Role> rolesFromDb);
}
