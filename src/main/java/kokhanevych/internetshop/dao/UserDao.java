package kokhanevych.internetshop.dao;

import java.util.List;
import java.util.Optional;

import kokhanevych.internetshop.exceptions.AuthenticationException;
import kokhanevych.internetshop.model.User;

public interface UserDao {

    User create(User user);

    User get(Long id);

    User update(User user);

    void delete(Long id);

    User login(String login, String password) throws AuthenticationException;

    Optional<User> getByToken(String token);

    List<User> getAll();
}
