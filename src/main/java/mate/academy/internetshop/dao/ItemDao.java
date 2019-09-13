package mate.academy.internetshop.dao;

import mate.academy.internetshop.model.Item;

public interface ItemDao {

    Item create(Item item);

    Item get(Long id);

    Item update(Item item);

    void delete(Long id);

    void deleteByItem(Item item);
}
