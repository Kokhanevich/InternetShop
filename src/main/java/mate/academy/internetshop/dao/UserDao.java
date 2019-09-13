package mate.academy.internetshop.dao;

import mate.academy.internetshop.model.User;

public interface UserDao {

    User create(User user);

    User get(Long id);

    User update(User user);

    void delete(Long id);

    void deleteByItem(User user);
}
